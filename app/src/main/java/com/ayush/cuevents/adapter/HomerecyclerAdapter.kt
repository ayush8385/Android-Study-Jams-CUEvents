package com.ayush.cuevents.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ayush.cuevents.R
import com.ayush.cuevents.activity.ClubItems
import com.ayush.cuevents.model.Clubs
import com.squareup.picasso.Picasso


class HomerecyclerAdapter(val context: Context) :RecyclerView.Adapter<HomerecyclerAdapter.HomeViewHolder>(){

    val allClubs= java.util.ArrayList<Clubs>()
    class HomeViewHolder(view: View):RecyclerView.ViewHolder(view){
        val clubImage:ImageView=view.findViewById(R.id.club_img)
        val clubName:TextView=view.findViewById(R.id.club_name)
        val clubDesc:TextView=view.findViewById(R.id.club_desc)
        val parLayout:LinearLayout=view.findViewById(R.id.parentlayout)

    }

    fun updateList(list: ArrayList<Clubs>) {
        allClubs.clear()
        allClubs.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.home_single_row,parent,false)

        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val club=allClubs[position]
        holder.clubName.text=club.name
        holder.clubDesc.text=club.description
        Picasso.get().load(club.image_url).into(holder.clubImage)

        holder.parLayout.setOnClickListener {
            context.startActivity(Intent(context, ClubItems::class.java).putExtra("id",club.id).putExtra("name",club.name))
        }

    }
    override fun getItemCount(): Int {
        return allClubs.size
    }
}