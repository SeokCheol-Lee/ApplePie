package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_applepie.databinding.ActivityViewTeamBinding
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.myTeam
import com.example.project_applepie.model.recuit
import com.example.project_applepie.recyclerview.profileRecycle.myVolunteerAdapter
import com.example.project_applepie.recyclerview.profileRecycle.viewTeamAdapter

class ViewTeamActivity : AppCompatActivity() {
    private lateinit var viewTeamAdapter: viewTeamAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vtBinding = ActivityViewTeamBinding.inflate(layoutInflater)
        setContentView(vtBinding.root)

        val basicImg = R.drawable.charmander
        val itemList = arrayListOf(
            AuerProfile(basicImg,"이상해씨","프론트","간략하게 작성")
        )

        viewTeamAdapter = viewTeamAdapter()
        viewTeamAdapter.submitList(itemList)
        vtBinding.rvRecruit.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        vtBinding.rvRecruit.adapter = viewTeamAdapter
    }
}