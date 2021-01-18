package com.poly.test.diaryapp.Setting

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.auth.FirebaseAuth
import com.poly.test.diaryapp.Intro.IntroActivity
import com.poly.test.diaryapp.Intro.LoginActivity
import com.poly.test.diaryapp.MyApplication
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.Constants.COLOR_BG
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*




class SettingFragment : Fragment() {


    var setView : View? = null
    var auth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        setView = view



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //배경 화면
        view?.background_Color?.setOnClickListener {
            val intent = Intent(activity, SetBgColorActivity::class.java)
            startActivity(intent)
        }
        //리뷰 버튼
        view?.Review_btn?.setOnClickListener {
            reviewBtn()
        }
        //회원 탈퇴
        view?.revoke_btn?.setOnClickListener {
            revokeAccessAD()
        }


        //로그아웃 스위치
        logout_Switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {

                btn_logout()
                Snackbar.make(setting_layout, "로그아웃 되었습니다.", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(setting_layout, "로그아웃이 취소 되었습니다.", Snackbar.LENGTH_LONG).show();
            }


        }
    }

    override fun onResume() {
        super.onResume()

        setView?.setting_layout?.setBackgroundResource(COLOR_BG)

    }


    //리뷰 버튼
    fun reviewBtn() {

        val manager = ReviewManagerFactory.create(context!!)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = request.result
                manager.launchReviewFlow(activity!!, reviewInfo)
            } else {
                // There was some problem, continue regardless of the result.
            }
        }
    }
    //회원탈퇴
    fun revokeAccessAD() {
        AlertDialog.Builder(context)
            .setTitle("회원탈퇴").setMessage(" 정말 회원탈퇴하시겠습니까? \n [ 기록된 정보는 사라집니다 ]  ")
            .setPositiveButton("회원탈퇴", DialogInterface.OnClickListener { dialog, whichButton ->
                // 쉐어드 초기화
                MyApplication.prefs.setclear()
                deleteId()

            })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, whichButton -> })
            .show()
    }




    //로그아웃 다이얼로그 구문
    fun btn_logout() {
        AlertDialog.Builder(context)
            .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
            .setPositiveButton("로그아웃", DialogInterface.OnClickListener { dialog, whichButton ->

                auth?.signOut()
                //화면넘김
                val i = Intent(context, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(i)


            })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, whichButton -> })
            .show()
    }

    fun deleteId(){
        FirebaseAuth.getInstance().currentUser!!.delete().addOnCompleteListener { task ->
            if(task.isSuccessful){
                Snackbar.make(setting_layout, "아이디 삭제가 완료되었습니다", Snackbar.LENGTH_LONG).show()



//                auth.signOut()
//                val settings = context?.getSharedPreferences("초기화", Context.MODE_PRIVATE)
//                settings?.edit()?.clear()?.apply()


                //인트로화면으로 이동
                val i = Intent(context, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(i)
            }else{
                Snackbar.make(setting_layout, task.exception.toString(), Snackbar.LENGTH_LONG).show()

            }
        }
    }


}