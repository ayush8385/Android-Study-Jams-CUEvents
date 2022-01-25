package com.ayush.cuevents.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.ayush.cuevents.R
import com.ayush.cuevents.adapter.FavoriterecyclerAdapter
import com.ayush.hungreed.database.EventDatabase
import com.ayush.hungreed.database.EventEntity
import com.digitalhain.daipsisearch.Activities.Room.EventViewModel


class FavoriteFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerAdapter: FavoriterecyclerAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    var dbEventList= arrayListOf<EventEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        recyclerView=view.findViewById(R.id.recyclerfav)
        progressLayout=view.findViewById(R.id.progresslayout)
        progressBar=view.findViewById(R.id.progressbar)
        layoutManager= LinearLayoutManager(activity)

        progressLayout.visibility=View.GONE

       // dbEventList=Retrieverevents(activity as Context).execute().get()

        if(activity!=null){
            recyclerAdapter=FavoriterecyclerAdapter(activity as Context)
            recyclerView.adapter=recyclerAdapter
            recyclerView.layoutManager=layoutManager
        }


        EventViewModel(requireActivity().application).allEvents.observe(requireActivity(), Observer { list->
            list?.let {
                dbEventList.clear()
                dbEventList.addAll(list)
                recyclerAdapter.updateList(dbEventList)
            }
        })

        return view
    }

//    class Retrieverevents(val context: Context): AsyncTask<Void, Void, List<EventEntity>>(){
//        override fun doInBackground(vararg params: Void?): List<EventEntity> {
//            val db = Room.databaseBuilder(context,EventDatabase::class.java,"event_fav-db").build()
//            return db.eventDao().getAllEvents()
//        }
//
//    }

}