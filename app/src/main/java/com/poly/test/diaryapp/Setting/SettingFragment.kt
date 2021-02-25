package com.poly.test.diaryapp.Setting

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.App.Companion.instance
import com.poly.test.diaryapp.Intro.IntroActivity
import com.poly.test.diaryapp.Intro.LoginActivity
import com.poly.test.diaryapp.MainActivity
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.RecyclerDecoration
import com.poly.test.diaryapp.adapters.MyTodayDiaryAdapter
import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.utils.Constants
import com.poly.test.diaryapp.utils.Constants.COLOR_BG
import com.poly.test.diaryapp.utils.SharedPreferenceFactory
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class SettingFragment : Fragment() {


    var setView : View? = null
    var auth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        MySizeGetData()
        oneMonthSize()
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
            } else {

            }


        }

//        view.exit_button.setOnClickListener {
//            val intent = Intent(activity, SetBgColorActivity::class.java)
//            startActivity(intent)
//        }


    }

    override fun onResume() {
        super.onResume()

        setView?.setting_layout?.setBackgroundResource(COLOR_BG)
        textWhiteColor()
    }

    //파이어베이스 리사이클러뷰 투데이 대이터
    private fun MySizeGetData() {

        val calendarArray = ArrayList<CalendarModel>()
        val store = FirebaseFirestore.getInstance().collection("user_calendar")
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        store.whereEqualTo("uid",uid)
            .addSnapshotListener { querySnapShot, error ->
                if (querySnapShot == null) return@addSnapshotListener
                calendarArray.clear()
                for (snapshot in querySnapShot.documents) {
                    calendarArray.add(snapshot.toObject(CalendarModel::class.java)!!)
                }
                Log.d("로그", "setting size ${calendarArray.size}")
                if (calendarArray.size > 0) {

                    val dataSize =  "다이어리에 ${calendarArray.size}번의 기록을 하셨습니다."

                    view?.main_text?.text = dataSize
                } else {

                }
            }


    }


    private fun oneMonthSize() {

        val calendarDate: Date = Date()
        val todayFormat = SimpleDateFormat("MM월")
        val todayText = todayFormat.format(calendarDate).toString()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        var calendar = Calendar.getInstance()
        var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        var calendarModel: ArrayList<CalendarModel>
        calendarModel = ArrayList()

        val date = Calendar.getInstance()
        val mon = date.get(Calendar.MONTH)


        FirebaseFirestore.getInstance().collection("user_calendar")?.whereEqualTo("mon",mon.toString()).whereEqualTo("uid",uid)
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                calendarModel.clear()
                if (querySnapshot == null)
                    return@addSnapshotListener
                for (snapshot in querySnapshot.documents) {
                    calendarModel.add(snapshot.toObject(CalendarModel::class.java)!!)
                    view?.sub_text?.text = "이번달 다이어리를 ${calendarModel.size}개 기록하였습니다."
                    Log.d(Constants.TAG, "123123.size: ${calendarModel.size}")

                }

            }

    }
    //리뷰 버튼
    fun reviewBtn() {

        val manager = ReviewManagerFactory.create(requireContext())
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = request.result
                manager.launchReviewFlow(requireActivity(), reviewInfo)
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

                SharedPreferenceFactory.clearAllValue()

                    deleteId()

                //인트로화면으로 이동
                val i = Intent(context, IntroActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(i)
            })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, whichButton -> })
            .show()
    }
//    // 총 글쓴횟수
//    fun gettitlecount() {
//        var contentDTO: ArrayList<ContentDTO>
//        contentDTO = ArrayList()
//
//          firestore?.collection("images")?.whereEqualTo("uid", uid)
//            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//                contentDTO.clear()
//                if (querySnapshot == null)
//                    return@addSnapshotListener
//                for (snapshot in querySnapshot.documents) {
//                    Log.d(ContentValues.TAG, "1")
//                    contentDTO.add(snapshot.toObject(ContentDTO::class.java)!!)
//                    Log.d(ContentValues.TAG, "2")
//                    account_tv_post_count.text = contentDTO.size.toString()
//                    Log.d(contentDTO.size.toString(), "size테스트")
//                }
//
//            }
//
//    }


    //로그아웃 다이얼로그 구문
    fun btn_logout() {
        AlertDialog.Builder(context)
            .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
            .setPositiveButton("로그아웃", DialogInterface.OnClickListener { dialog, whichButton ->
                Snackbar.make(setting_layout, "로그아웃 되었습니다.", Snackbar.LENGTH_LONG).show();
                auth?.signOut()
                //화면넘김
                val i = Intent(context, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(i)


            })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, whichButton -> })
            .show()
        Snackbar.make(setting_layout, "로그아웃이 취소 되었습니다.", Snackbar.LENGTH_LONG).show();
    }

    fun deleteId(){
        FirebaseAuth.getInstance().currentUser!!.delete().addOnCompleteListener { task ->
            if(task.isSuccessful){
                Snackbar.make(setting_layout, "아이디 삭제가 완료되었습니다", Snackbar.LENGTH_LONG).show()

                // 쉐어드 초기화

//                val settings = context?.getSharedPreferences("초기화", Context.MODE_PRIVATE)
//                settings?.edit()?.clear()?.apply()


            }else{
                Snackbar.make(setting_layout, task.exception.toString(), Snackbar.LENGTH_LONG).show()

            }
        }
    }

    //텍스트 싱글톤 컬러적용
    fun textWhiteColor(){

        //세팅

        main_text.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        sub_text.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        login_text.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        logout_Switch.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        settings_text.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))

        title_text.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        developer_btn.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        background_Color.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        appversion_btn.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        support_text.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))

        support_btn.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        Review_btn.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        notice_btn.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        revoke_btn.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        //단색




    }


}