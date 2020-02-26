package com.night.nightcourse.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.night.nightcourse.AnimalDetailActivity
import com.night.nightcourse.R
import com.night.nightcourse.model.Animal
import com.night.nightcourse.viewmodel.AnimalViewModel
import kotlinx.android.synthetic.main.fragment_likes.*
import kotlinx.android.synthetic.main.recycler_animal.view.*

@Suppress("DEPRECATION")
class LikeFragment : Fragment(){

    private lateinit var animalViewModel : AnimalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_likes,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        animalViewModel = ViewModelProviders.of(this).get(AnimalViewModel::class.java)
        recycler_like.layoutManager = LinearLayoutManager(this.context)
        recycler_like.setHasFixedSize(true)
        animalViewModel.likeAnimals.observe(viewLifecycleOwner, Observer {
            recycler_like.adapter = likeAnimalAdapter()
        })
    }

    inner class likeAnimalAdapter:RecyclerView.Adapter<likeAnimalViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): likeAnimalViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_animal,parent,false)
            return likeAnimalViewHolder(view)
        }

        override fun getItemCount(): Int {
           return animalViewModel.likeAnimals.value!!.size
        }

        override fun onBindViewHolder(holder: likeAnimalViewHolder, position: Int) {
            holder.bindTo(animalViewModel.likeAnimals.value!![position])
            holder.imageHeart.setOnClickListener {
                animalViewModel.delete(animalViewModel.likeAnimals.value!![position])
                notifyItemRemoved(position)
            }
        }
    }
    inner class likeAnimalViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val animalId = view.textView_animal_id
        val imageHeart = view.imageView_heart
        val imageAnimal = view.imageView_animal_Image
        val buttonDetail = view.button_detail
        fun bindTo(animal: Animal){
            animalId.text = animal.animal_id.toString()
            buttonDetail.setText("Detail")
            buttonDetail.setOnClickListener {
                startActivity(
                    Intent(this@LikeFragment.context, AnimalDetailActivity::class.java)
                        .putExtra("imageUrl", animal.album_file)
                        .putExtra("id", animal.animal_id.toString())
                        .putExtra("shelter_tell", animal.shelter_tel)
                        .putExtra("sex", animal.animal_sex)
                        .putExtra("age", animal.animal_age)
                )
            }
            Glide.with(view)
                .load(R.drawable.trash_1)
                .into(imageHeart)
            Glide.with(view)
                .load(animal.album_file)
                .into(imageAnimal)
        }
    }
}