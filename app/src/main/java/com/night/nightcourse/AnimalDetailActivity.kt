package com.night.nightcourse

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.activity_animal_detail.*
import kotlinx.android.synthetic.main.content_animal_detail.*

class AnimalDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_detail)
        setSupportActionBar(toolbar)
        val intent : Intent = intent
        val id = intent.getStringExtra("id")
        val imageUrl = intent.getStringExtra("imageUrl")
        val shelter_tel = intent.getStringExtra("shelter_tell")
        val sex = intent.getStringExtra("sex")
        val age = intent.getStringExtra("age")
        Glide.with(this)
            .load(imageUrl)
            .into(imageView_detail)
        textView_detail.text = "ID : $id \nSEX : $sex \nAGE : $age \nShelter_tel : $shelter_tel"
    }

}
