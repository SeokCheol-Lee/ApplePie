package com.example.project_applepie

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.forEach
import com.example.project_applepie.databinding.ActivityModifyProfileBinding
import com.example.project_applepie.model.dao.inquireUserInfo
import com.example.project_applepie.model.dao.js_modProfile
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.BasicResponse
import com.example.project_applepie.sharedpref.SharedPref
import com.example.project_applepie.utils.Url
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ModifyProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mpBinding = ActivityModifyProfileBinding.inflate(layoutInflater)
        setContentView(mpBinding.root)

        // 사용자 uid & pid 가져오기
        val uid = SharedPref.getUserId(this@ModifyProfileActivity)
//        val uid = "10006"

        // 사용자 정보 조회하기
        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)

        server.inquireUserInfo(uid).enqueue(object : Callback<inquireUserInfo> {
            override fun onResponse(call: Call<inquireUserInfo>, response: Response<inquireUserInfo>
            ) {
                Log.d("로그","${response.body().toString()}")
                var eamil = response.body()?.data?.get("email");
                Log.d("로그","$eamil")
                Log.d("uid 확인", "$uid")
            }

            override fun onFailure(call: Call<inquireUserInfo>, t: Throwable) {
                Log.d("로그_서버연동 실패","${t.message}")
            }
        })

        // 사용자 지역
        var uArea : String = "KOREA"

        // 사용자 출신 대학교
        var uCollege : String = "DKU"

        // 사용자가 대학교에서 받은 학점 (숫자로만 입력 받도록 수정 필요) <------------------------------------------------------------
        var uGrade : Float = 0f
        var uTotalGrade : Float = 4.5f

        // 학년 선택 -> 기본값 : 2학년 (나중에 비우고 선택 안하면 넘어가지 않도록 수정 필요) <-------------------------------------------
        var uGrader : String = "2학년"
        mpBinding.toggleButton.check(R.id.btn_grade2)
        mpBinding.btnGrade1.setOnClickListener {
            uGrader = "1학년"
        }
        mpBinding.btnGrade2.setOnClickListener {
            uGrader = "2학년"
        }
        mpBinding.btnGrade3.setOnClickListener {
            uGrader = "3학년"
        }
        mpBinding.btnGrade4.setOnClickListener {
            uGrader = "4학년"
        }

        // 사용자 깃허브 주소
        var uGit : String = ""

        // 사용자가 사용하는 프로그램 언어 선택
        var uLanguage : List<Int> = listOf()
        mpBinding.chLang.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener{ _, _ ->
                val ids = mpBinding.chLang.checkedChipIds
                var titles = listOf<Int>()

                ids.forEach { id ->
                    if(mpBinding.chLang.findViewById<Chip>(id).text == "C"){
                        titles += 0
                    } else if(mpBinding.chLang.findViewById<Chip>(id).text == "C++"){
                        titles += 1
                    } else if(mpBinding.chLang.findViewById<Chip>(id).text == "C#"){
                        titles += 2
                    } else if(mpBinding.chLang.findViewById<Chip>(id).text == "Python"){
                        titles += 3
                    } else if(mpBinding.chLang.findViewById<Chip>(id).text == "Kotlin"){
                        titles += 4
                    } else if(mpBinding.chLang.findViewById<Chip>(id).text == "Swift"){
                        titles += 5
                    } else if(mpBinding.chLang.findViewById<Chip>(id).text == "Dart"){
                        titles += 6
                    } else { titles += 7 }
                }
                Log.d("test", "Click: $titles")
                uLanguage = titles
            }
        }

        // 사용자가 사용하는 프로그램 선택
        val getFramework = resources.getStringArray(R.array.create_profile_framework)
        val arrayAdapter2 = ArrayAdapter(this,R.layout.dropdown_item, getFramework)
        mpBinding.acFramework.setAdapter(arrayAdapter2)

        var uFramework : String = "None"
        mpBinding.acFramework.setOnItemClickListener { adapterView, view, position, id ->
            uFramework = getFramework[position].toString()
//            Toast.makeText(this, "$uFramework", Toast.LENGTH_SHORT).show()
        }

        mpBinding.modSuccessBtn.setOnClickListener {
            // Retrofit 연동
            val url = Url.BASE_URL
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            var server = retrofit.create(ApiService::class.java)

            uArea = mpBinding.modArea.text.toString()
            uCollege = mpBinding.modColleague.text.toString()
            uGit = mpBinding.modGithub.text.toString()
            uGrade = mpBinding.modMyScore.text.toString().toFloat()
            uTotalGrade = mpBinding.modMaxScore.text.toString().toFloat()

            val modifyProfileModel = js_modProfile(uArea, uCollege, uGrade, uTotalGrade, uGrader, uGit, uLanguage, uFramework)

            server.modifyProfile(modifyProfileModel, uid).enqueue(object:Callback<BasicResponse> {
                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.d("프로필 수정 실패", "$t")
                    Toast.makeText(this@ModifyProfileActivity, "서버 오류! 프로필 수정 실패", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                    Toast.makeText(this@ModifyProfileActivity, "프로필 생성을 완료했습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("프로필 수정 성공", "$modifyProfileModel")

                    val intent = Intent(this@ModifyProfileActivity, HomeActivity::class.java)

                    val changeView = 100
                    intent.putExtra("chgV", changeView)
                    startActivity(intent)
                    finish()
                } }
            )
//            val intent = Intent(this, PersonalInformation::class.java)
//            startActivity(intent)
//            finish()
        }
    }
}