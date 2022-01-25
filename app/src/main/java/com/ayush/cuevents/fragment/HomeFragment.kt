package com.ayush.cuevents.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ayush.cuevents.R
import com.ayush.cuevents.adapter.HomerecyclerAdapter
import com.ayush.cuevents.model.Clubs
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Method
import javax.security.auth.Subject


class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: HomerecyclerAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressLayout: RelativeLayout
    var clublist= arrayListOf<Clubs>()
    lateinit var searchView: SearchView
    val filteredlist:ArrayList<Subject> = ArrayList()
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerhome)
        progressLayout = view.findViewById(R.id.progresslayout)
        progressBar = view.findViewById(R.id.progressbar)
        searchView=view.findViewById(R.id.search_bar)

        progressLayout.visibility = View.VISIBLE

        layoutManager = LinearLayoutManager(activity)

        val url = "https://digitalhain.com/ayush/getClubs.php/"
        val queue = Volley.newRequestQueue(context)

        searchElement()

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {

                    val array = JSONArray(response)
                    progressLayout.visibility=View.GONE

                    for(i in 0 until array.length()){
                        val clubJSONObject=array.getJSONObject(i)
                        val clubObject=Clubs(
                            clubJSONObject.getString("club_id"),
                            clubJSONObject.getString("club_name"),
                            clubJSONObject.getString("club_desc"),
                            clubJSONObject.getString("club_img")
                        )
                        clublist.add(clubObject)
                        recyclerAdapter= HomerecyclerAdapter(activity as Context)
                        recyclerView.adapter=recyclerAdapter
                        recyclerView.layoutManager=layoutManager

                        recyclerAdapter.updateList(clublist)
                    }

                } catch (e: JSONException) {
                    Toast.makeText(context,"Error found", Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
        }

        queue.add(stringRequest)

        return view

    }

    private fun searchElement() {
        searchView.queryHint="Search For Clubs..."
        searchView.setIconifiedByDefault(false)
        val searchIcon: ImageView = searchView.findViewById(R.id.search_mag_icon)
        searchIcon.visibility= View.GONE
        searchIcon.setImageDrawable(null)
        val closeIcon:ImageView = searchView.findViewById(R.id.search_close_btn)
        closeIcon.setColorFilter(Color.BLACK)
        val theTextArea = searchView.findViewById<View>(R.id.search_src_text) as SearchView.SearchAutoComplete
        theTextArea.setTextColor(Color.BLACK)
        theTextArea.setHintTextColor(Color.DKGRAY)//or any color that you want

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                //filtering(query!!)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterr(newText!!)
                return true
            }
        })
    }

    fun filterr(text:String){
        val filteredlist:ArrayList<Clubs> = ArrayList()

        for(item in clublist){
            if(item.name.toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()){
            //Toast.makeText(applicationContext,"No Chats found", Toast.LENGTH_SHORT).show()
        }
        recyclerAdapter.updateList(filteredlist)
    }


}