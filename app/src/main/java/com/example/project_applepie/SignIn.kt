package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

//      로그인 버튼 클릭 시 팀원 모집 화면으로 이동
        findViewById<Button>(R.id.Login).setOnClickListener {
            val intent = Intent(this, Recruit::class.java)
            finish()
            startActivity(intent)
        }
    }
}