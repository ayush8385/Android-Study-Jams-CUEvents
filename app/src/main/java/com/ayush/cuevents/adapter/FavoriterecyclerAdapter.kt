package com.ayush.cuevents.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ayush.cuevents.R
import com.ayush.cuevents.activity.ClubItems
import com.ayush.hungreed.database.EventEntity
import com.squareup.picasso.Picasso

class FavoriterecyclerAdapter(val context: Context):RecyclerView.Adapter<FavoriterecyclerAdapter.FavoriteviewHolder>() {
    val itemList= arrayListOf<EventEntity>()
    class FavoriteviewHolder(view: View):RecyclerView.ViewHolder(view){
        val parentlayout:CardView=view.findViewById(R.id.home_row)
        val fav_image:ImageView=view.findViewById(R.id.fav_img)
        val fav_name:TextView=view.findViewById(R.id.fav_name)
        val fav_desc:TextView=view.findViewById(R.id.fav_desc)
        val fav_fav:ImageView=view.findViewById(R.id.fav_fav)
    }

    fun updateList(list:ArrayList<EventEntity>){
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteviewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.favorite_single_row,parent,false)
        return FavoriteviewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteviewHolder, position: Int) {
        val event=itemList[position]

        val eventEntity = EventEntity(
            event.eventId,event.eventName,event.eventDesc,event.eventDetail,event.eventImage
        )

        val checkFav= ClubItems.DBAsyncTask(context,eventEntity,1).execute()

        val fav=checkFav.get()
        if(fav){
            holder.fav_name.text=event.eventName
            holder.fav_desc.text=event.eventDesc
            Picasso.get().load(event.eventImage).into(holder.fav_image)
            holder.fav_fav.setImageResource(R.drawable.ic_lmyfav_foreground)
        }

        holder.fav_fav.setOnClickListener {
            itemList.remove(event)
            notifyDataSetChanged()
            if(!ClubItems.DBAsyncTask(context,eventEntity,1).execute().get()){
                val result= ClubItems.DBAsyncTask(context,eventEntity,2).execute().get()
                if(result){
                    Toast.makeText(context, "${holder.fav_name.text} added to favorite", Toast.LENGTH_SHORT).show()
                    holder.fav_fav.setImageResource(R.drawable.ic_lmyfav_foreground)
                }
                else{
                    Toast.makeText(context,"Some Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                val result= ClubItems.DBAsyncTask(context,eventEntity,3).execute().get()
                if(result){
                    Toast.makeText(context, "${holder.fav_name.text} removed from favorite", Toast.LENGTH_SHORT).show()
                    holder.fav_fav.setImageResource(R.drawable.ic_favorite_foreground)
                }
                else{
                    Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}