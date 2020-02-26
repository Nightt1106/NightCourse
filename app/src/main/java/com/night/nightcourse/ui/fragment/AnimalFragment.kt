package com.night.nightcourse.ui.fragment
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.night.nightcourse.AnimalDetailActivity
import com.night.nightcourse.R
import com.night.nightcourse.model.Animal
import com.night.nightcourse.viewmodel.AnimalViewModel

import kotlinx.android.synthetic.main.fragment_animals.*
import kotlinx.android.synthetic.main.recycler_animal.view.*

@Suppress("DEPRECATION")
class AnimalFragment : Fragment() {

    private val TAG = AnimalFragment::class.java.simpleName
    private lateinit var animalViewModel: AnimalViewModel
    private val animalAdapter = AnimalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.i(TAG, "Called ViewModelProviders.of")
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser)
            animalAdapter.notifyDataSetChanged()
        Log.d(TAG,isVisibleToUser.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //viewModel = ViewModelProviders.of(this).get(AnimalViewModel::class.java)
        return inflater.inflate(R.layout.fragment_animals,container,false)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchView.inputType = InputType.TYPE_CLASS_NUMBER
        searchView.queryHint = "Search from id"

        spinner.adapter = ArrayAdapter(this.requireContext(),R.layout.support_simple_spinner_dropdown_item, arrayListOf("--","Dog","Cat"))

        recyler.layoutManager = LinearLayoutManager(this.context)
        recyler.setHasFixedSize(true)
        recyler.addItemDecoration(DividerItemDecoration(this.requireContext(),DividerItemDecoration.VERTICAL))

        animalViewModel = ViewModelProviders.of(this).get(AnimalViewModel::class.java)

        animalViewModel.tempAnimals.observe(this, Observer {
            if (it != null) {
                loading.visibility = View.GONE
                recyler.adapter = animalAdapter
                spinner.onItemSelectedListener = OnItemSelectedListener()
                searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(text: String?): Boolean {

                        animalViewModel.tempAnimals.value?.let {
                            it.forEach {
                                if(it.animal_id.toString() == text){
                                    Log.d(TAG,"FindITTTTTTTTTT")
                                    AlertDialog.Builder(this@AnimalFragment.requireContext())
                                        .setTitle("Your search is exit")
                                        .setMessage("$text is exit \n Do you want to see animal detail's?")
                                        .setPositiveButton("GO", DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int -> goDeail(it)})
                                        .setNegativeButton("Like",DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                                            animalViewModel.insert(it)
                                            animalAdapter.notifyDataSetChanged()
                                        })
                                        .setOnCancelListener { animalAdapter.notifyDataSetChanged() }
                                        .show()
                                    return true
                                }
                            }
                        }
                        AlertDialog.Builder(this@AnimalFragment.requireContext())
                            .setTitle("Your search is not exit")
                            .setMessage("$text is not exit")
                            .setNegativeButton("OK",null)
                            .show()
                        return false
                    }
                    override fun onQueryTextChange(text: String?): Boolean {
                        return false
                    }
                })
            }
        })

        animalViewModel.likeAnimals.observe(this, Observer { list ->
            floatingActionButton_test.setOnClickListener {
                list.forEach {
                    Log.d("AnimalIDDDDD","${it.animal_id} ")
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"PAUSE")
    }

    /*open inner class OnQueryTextListener : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            Log.d(TAG,"q1424")
            return false
        }
    }*/

    inner class OnItemSelectedListener : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            when(p2){
                0 -> {
                    animalViewModel.tempAnimals = animalViewModel.animals
                    animalAdapter.notifyDataSetChanged()
                    Log.d(TAG,animalViewModel.animals.value.toString())
                }
                1 -> {
                    animalViewModel.animalsOfDog.observe(this@AnimalFragment, Observer {
                        animalViewModel.tempAnimals = animalViewModel.animalsOfDog
                        animalAdapter.notifyDataSetChanged()
                    })
                    Log.d(TAG,animalViewModel.animalsOfDog.value.toString())
                }
                2 -> {
                    animalViewModel.animalsOfCat.observe(this@AnimalFragment, Observer {
                        animalViewModel.tempAnimals = animalViewModel.animalsOfCat
                        animalAdapter.notifyDataSetChanged()
                    })
                    Log.d(TAG,animalViewModel.animalsOfCat.value.toString())
                }
            }
        }

    }

    inner class AnimalAdapter : RecyclerView.Adapter<AnimalViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_animal,parent,false)
            return AnimalViewHolder(view)
        }

        override fun getItemCount(): Int {
            return animalViewModel.tempAnimals.value!!.size
        }

        override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
            holder.bindTo(animalViewModel.tempAnimals.value!![position])
            /*animalViewModel.mutableMap.clear()
            animalViewModel.mutableMap.put(animalViewModel.tempAnimals.value!![position].animal_id,position)*/
        }
    }

    fun goDeail(animal: Animal){
        startActivity(Intent(this@AnimalFragment.context,AnimalDetailActivity::class.java)
            .putExtra("imageUrl",animal.album_file)
            .putExtra("id",animal.animal_id.toString())
            .putExtra("shelter_tell",animal.shelter_tel)
            .putExtra("sex",animal.animal_sex)
            .putExtra("age",animal.animal_age)
        )
    }

    inner class AnimalViewHolder(private val view: View) : RecyclerView.ViewHolder(view){

        val animalId = view.textView_animal_id
        val imageHeart = view.imageView_heart
        val imageAnimal = view.imageView_animal_Image
        val buttonDetail = view.button_detail
        var temp : Boolean = false

        fun bindTo(animal : Animal){
            animalId.text = "ID: " + animal.animal_id.toString()
            buttonDetail.setText("Detail")
            buttonDetail.setOnClickListener {
                goDeail(animal)
            }
            Glide.with(view)
                .load(R.drawable.plus_blue)
                .into(imageHeart)

            for(it in animalViewModel.likeAnimals.value!!){
                if (it.animal_id == animal.animal_id) {
                    Glide.with(view)
                        .load(R.drawable.heart_red)
                        .into(imageHeart)
                    temp = true
                    break
                }
            }

            Glide.with(view)
                .load(animal.album_file)
                .error(R.drawable.noexit)
                .into(imageAnimal)

            imageHeart.setOnClickListener {
                Log.d(TAG,temp.toString())
                temp = false
                if(!temp) {
                    animalViewModel.insert(animal)
                    Glide.with(view)
                        .load(R.drawable.heart_red)
                        .into(imageHeart)
                    temp = true
                } /*else {
                    animalViewModel.delete(animal)
                    Glide.with(view)
                        .load(R.drawable.heart)
                        .into(imageHeart)
                    temp = false
                }*/
            }
        }

    }
}

