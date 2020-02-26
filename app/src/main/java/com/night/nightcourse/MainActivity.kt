package com.night.nightcourse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val listString = listOf("StrayHouse","TESTING","TESTING")
    private lateinit var arrayAdapter: ArrayAdapter<Any>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,listString)
        listView.adapter = arrayAdapter as ListAdapter?
        button_let_star.setOnClickListener {
            button_let_star.visibility = View.GONE
            listView.visibility = View.VISIBLE
        }

        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, postion, l ->

            Log.d(TAG , postion.toString())
            when(postion){
                0 -> startActivity(Intent(this,StrayHouseActivity::class.java))
                1 -> Snackbar.make(view,"TESTING",Snackbar.LENGTH_LONG).show()
                2 -> Snackbar.make(view,"TESTING",Snackbar.LENGTH_LONG).show()
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
