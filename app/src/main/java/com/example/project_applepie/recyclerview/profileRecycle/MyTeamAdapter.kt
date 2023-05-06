package com.example.project_applepie.recyclerview.profileRecycle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.App
import com.example.project_applepie.R
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.myTeam

class MyTeamAdapter : RecyclerView.Adapter<MyTeamHolder>(){

    private var searchList = ArrayList<myTeam>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTeamHolder {
        val teamHolder = MyTeamHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_my_team,parent,false)
        )
        return teamHolder
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: MyTeamHolder, position: Int) {
        holder.teamImg.setImageResource(searchList.get(position).thumbnail)
        holder.teamTitle.text = searchList.get(position).title

        holder.teamDelete.setOnClickListener {
            Log.d("로그","삭제")
        }

        if(position!=RecyclerView.NO_POSITION){
            holder.itemView.setOnClickListener {
                listener?.onItemClick(holder.itemView, searchList[position], position)
            }
        }
    }

    fun submitList(searchList : ArrayList<myTeam>){
        this.searchList = searchList
    }
    interface OnItemClickListener{
        fun onItemClick(v: View, data: myTeam, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }
}