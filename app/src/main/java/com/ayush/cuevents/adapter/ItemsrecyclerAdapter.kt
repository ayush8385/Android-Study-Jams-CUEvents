package com.ayush.cuevents.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ayush.cuevents.R
import com.ayush.cuevents.activity.ClubItems
import com.ayush.cuevents.model.Events
import com.ayush.hungreed.database.EventEntity
import com.squareup.picasso.Picasso

class ItemsrecyclerAdapter(val context: Context, var itemList:ArrayList<Events>):RecyclerView.Adapter<ItemsrecyclerAdapter.ItemsviewHolder>() {

    class ItemsviewHolder(view: View):RecyclerView.ViewHolder(view){
        val eventImage: ImageView =view.findViewById(R.id.event_img)
        val eventName:TextView=view.findViewById(R.id.event_name)
        val eventDesc:TextView=view.findViewById(R.id.event_desc)
        val eventFav:ImageView=view.findViewById(R.id.event_fav)
        val parLayout: LinearLayout =view.findViewById(R.id.parentlayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsviewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_single_row,parent,false)
        return ItemsviewHolder(view)

    }

    override fun onBindViewHolder(holder: ItemsviewHolder, position: Int) {

        val event=itemList[position]
        holder.eventName.text=event.name
        holder.eventDesc.text=event.description
        Picasso.get().load(event.image_url).into(holder.eventImage)

        holder.parLayout.setOnClickListener {
//            context.startActivity(Intent(context, FoodItems::class.java).putExtra("id",food.id).putExtra("name",food.name))
        }

        val eventEntity = EventEntity(
            event.id,event.name,event.description,event.detail,event.image_url
        )

        val checkFav=ClubItems.DBAsyncTask(context,eventEntity,1).execute()

        val fav=checkFav.get()
        if(fav){
            holder.eventFav.setImageResource(R.drawable.ic_lmyfav_foreground)
        }
        else{
            holder.eventFav.setImageResource(R.drawable.ic_favorite_foreground)
        }
        holder.eventFav.setOnClickListener {
            if(!ClubItems.DBAsyncTask(context,eventEntity,1).execute().get()){
                val result=ClubItems.DBAsyncTask(context,eventEntity,2).execute().get()
                if(result){
                    Toast.makeText(context, "${holder.eventName.text} added to favorite", Toast.LENGTH_SHORT).show()
                    holder.eventFav.setImageResource(R.drawable.ic_lmyfav_foreground)
                }
                else{
                    Toast.makeText(context,"Some Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                val result=ClubItems.DBAsyncTask(context,eventEntity,3).execute().get()
                if(result){
                    Toast.makeText(context, "${holder.eventName.text} removed from favorite", Toast.LENGTH_SHORT).show()
                    holder.eventFav.setImageResource(R.drawable.ic_favorite_foreground)
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