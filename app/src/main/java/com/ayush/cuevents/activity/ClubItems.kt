package com.ayush.cuevents.activity

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ayush.cuevents.R
import com.ayush.cuevents.adapter.HomerecyclerAdapter
import com.ayush.cuevents.adapter.ItemsrecyclerAdapter
import com.ayush.cuevents.model.Clubs
import com.ayush.cuevents.model.Events
import com.ayush.hungreed.database.EventDatabase
import com.ayush.hungreed.database.EventEntity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject




class ClubItems : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: ItemsrecyclerAdapter
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var drawerLayout: DrawerLayout
    lateinit var frameLayout: FrameLayout
    lateinit var proceed:Button
    lateinit var progressLayout:RelativeLayout
    lateinit var progressBar:ProgressBar
    var club_name:String?=null
    var club_id:String?=null
    var flag=0
    var eventItems= arrayListOf<Events>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_items)

        frameLayout=findViewById(R.id.framelayout)
        recyclerView=findViewById(R.id.recycleritems)
        progressLayout=findViewById(R.id.progresslayout)
        progressBar=findViewById(R.id.progressbar)

        progressLayout.visibility= View.VISIBLE
        layoutManager= LinearLayoutManager(this)

        club_name=intent.getStringExtra("name")
        club_id=intent.getStringExtra("id")
        setuptoolbar()

        fetchAllItem(club_id)

    }

    fun fetchAllItem(clubId: String?) {

        val url = "https://digitalhain.com/ayush/getEvents.php/"
        val queue = Volley.newRequestQueue(this)


        val jsonObjectRequest =
            object : StringRequest(Method.POST ,url, com.android.volley.Response.Listener {

                try {
                    val array = JSONArray(it)
                    progressLayout.visibility=View.GONE

                    for(i in 0 until array.length()){
                        val eventJSONObject=array.getJSONObject(i)
                        val clubObject= Events(
                            eventJSONObject.getString("event_id"),
                            eventJSONObject.getString("event_name"),
                            eventJSONObject.getString("event_desc"),
                            eventJSONObject.getString("event_detail"),
                            eventJSONObject.getString("event_img")
                        )
                        eventItems.add(clubObject)
                        recyclerAdapter= ItemsrecyclerAdapter(this,eventItems)
                        recyclerView.adapter=recyclerAdapter
                        recyclerView.layoutManager=layoutManager
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }, com.android.volley.Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT)
                    .show()
            }) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params.put("Content-Type","application/json")
                    params.put("club", clubId.toString())
                    return params
                }
            }
        queue.add(jsonObjectRequest)

    }


    fun setuptoolbar(){
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title=club_name
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class DBAsyncTask(val context: Context, val eventEntity: EventEntity, val mode:Int):AsyncTask<Void,Void,Boolean>(){
        override fun doInBackground(vararg params: Void?): Boolean {
            val db= Room.databaseBuilder(context,EventDatabase::class.java,"events-db").build()

            when(mode){
                1->{
                    val event:EventEntity=db.eventDao().getEventsbyId(eventEntity.eventId)
                    db.close()
                    return event!=null
                }
                2->{
                    db.eventDao().insertEvent(eventEntity)
                    db.close()
                    return true
                }
                3->{
                    db.eventDao().deleteEvent(eventEntity)
                    db.close()
                    return true
                }
            }
            return false
        }
    }

}