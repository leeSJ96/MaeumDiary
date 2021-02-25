package com.poly.test.diaryapp.Home

import android.content.ContentValues
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.RecyclerDecoration
import com.poly.test.diaryapp.adapters.FriendShareDiaryAdapter
import com.poly.test.diaryapp.adapters.MyTodayDiaryAdapter
import com.poly.test.diaryapp.databinding.FragmentHomeBinding
import com.poly.test.diaryapp.friendCustumDialog
import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.models.CalendarReceiveModel
import com.poly.test.diaryapp.models.FriendAddModel
import com.poly.test.diaryapp.models.FriendDataModel
import com.poly.test.diaryapp.utils.Constants
import com.poly.test.diaryapp.utils.SharedPreferenceFactory
import com.poly.test.diaryapp.utils.onMyTextChanged
import kotlinx.android.synthetic.main.activity_set_bg_color.*
import kotlinx.android.synthetic.main.fragment_calendar_list.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private var fragmentHomeBinding: FragmentHomeBinding? = null
    var friendmodel: ArrayList<FriendAddModel> = arrayListOf()
    var calendarModel: ArrayList<CalendarModel> = arrayListOf()
    private var firestore: FirebaseFirestore? = null
    var uid: String? = null

    var sizeSnapshot: ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        fragmentHomeBinding = binding

        firestore = FirebaseFirestore.getInstance()
        uid = SharedPreferenceFactory.getStrValue("userToken", "")

        fragmentHomeBinding?.searchEdit?.isEndIconVisible = false

        fragmentHomeBinding?.searchEditText?.onMyTextChanged {

            when {

                it.toString().count() >= 12 -> {
                    Snackbar.make(this.home_layout, "검색어는 12자까지만 입력 가능합니다.", Snackbar.LENGTH_SHORT)
                        .show()
                }

                it.toString().count() > 0 -> {
                    fragmentHomeBinding?.searchEdit?.isEndIconVisible = true
                }
                else -> {
                    fragmentHomeBinding?.searchEdit?.isEndIconVisible = false

                }


            }
        }
        fragmentHomeBinding?.searchEdit?.setEndIconOnClickListener {
            val searchText = fragmentHomeBinding?.searchEditText?.text.toString()

            searchList(searchText)

        }
        MyTodayGetData()
        friendGetData()
        return fragmentHomeBinding!!.root
    }

    val name = SharedPreferenceFactory.getStrValue("userName", "").toString()   // 유저 닉네임

    private fun getProfile() {
        user_name.text = name
    }

//    private fun searchList(searchText: String) {
//
//        Log.d("로그", "서치 텍스트 $searchText")
//        // 친구 닉네임 검색, 친구 이메일 검색 둘 다 허용해줘야 함
//
//
//        val authStore = FirebaseFirestore.getInstance().collection("user_auth")
//
//
//        authStore.whereEqualTo("name", searchText).get().addOnSuccessListener { querySnapShot ->
//
//            for (i in querySnapShot) {
//
//                Log.d("로그", "데이터/  이름: ${i.data["name"]} 닉네임 : ${i.data["email"]}")
//
//
//            }
//
//        }.addOnFailureListener {
//
//        }
//
//
//    }


    override fun onResume() {
        super.onResume()
//        배경색
        fragmentHomeBinding?.homeLayout?.setBackgroundResource(Constants.COLOR_BG)
//        fragmentHomeBinding?.homeSubLayout?.setBackgroundResource(Constants.COLOR_BG)
//        fragmentHomeBinding?.homeScrollview?.setBackgroundResource(Constants.COLOR_BG)
        friendSize()
        todaysize()
        getProfile()
        textColor()
        iconColor()


    }

    override fun onStop() {
        super.onStop()
        sizeSnapshot?.remove()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        var url =
            "http://storage.enuri.info/pic_upload/knowbox2/202006/09420655320200602575abd0d-a578-49d0-ab34-d9f3d58ee86b.jpeg"

        iconColor()

        fragmentHomeBinding?.friendListBtn?.setOnClickListener {
            friendList()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun iconColor() {
//        fragmentHomeBinding?.searchBack?.setBackgroundResource(Constants.COLOR_ICON_BACKSEARCH)
        fragmentHomeBinding?.mySoulImage?.setBackgroundResource(Constants.COLOR_ICON_MYALARM)
        fragmentHomeBinding?.friendSoulImage?.setBackgroundResource(Constants.COLOR_ICON_FRALARM)
        fragmentHomeBinding?.searchEdit?.setBackgroundResource((Constants.COLOR_SEARCH_BACK))
        fragmentHomeBinding?.friendListBtn?.setBackgroundResource((Constants.COLOR_FRIENDLIST))
        fragmentHomeBinding?.middleLine?.setBackgroundResource((Constants.COLOR_TEXT_W))
        fragmentHomeBinding?.searchEditText?.setBackgroundResource((Constants.COLOR_HOME_ITEM))
    }

    fun textColor() {
//        fragmentHomeBinding?.searchEdit?.setHintTextColor(resources.getColor(Constants.COLOR_TEXT_W))
//        fragmentHomeBinding?.searchEdit?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        fragmentHomeBinding?.userName?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
//        fragmentHomeBinding?.todayText?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
//        fragmentHomeBinding?.friendText?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
//        fragmentHomeBinding?.froendUidText?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
//        fragmentHomeBinding?.scheduleText?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
//        fragmentHomeBinding?.contextText?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        fragmentHomeBinding?.userName?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        fragmentHomeBinding?.mySoulText?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        fragmentHomeBinding?.friendSoulText?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        fragmentHomeBinding?.friendListTv?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        fragmentHomeBinding?.todayTv?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        fragmentHomeBinding?.myTv?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        fragmentHomeBinding?.friendTv?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        fragmentHomeBinding?.searchEditText?.setHintTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        fragmentHomeBinding?.searchEditText?.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
    }

    //파이어베이스 리사이클러뷰 투데이 대이터
    private fun MyTodayGetData() {

        val calendarArray = ArrayList<CalendarModel>()
        val store = FirebaseFirestore.getInstance().collection("user_calendar")
        val calendarDate: Date = Date()
        val todayFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val todayText = todayFormat.format(calendarDate).toString()



        store.whereEqualTo("date", todayText).whereEqualTo("uid", uid)
            .addSnapshotListener { querySnapShot, error ->
                if (querySnapShot == null) return@addSnapshotListener
                calendarArray.clear()
                for (snapshot in querySnapShot.documents) {
                    calendarArray.add(snapshot.toObject(CalendarModel::class.java)!!)
                }
                Log.d("로그", "hometoday size ${calendarArray.size}")
                if (calendarArray.size > 0) {
                    //리사이클러뷰
                    fragmentHomeBinding?.todayRecyclerview?.adapter =
                        MyTodayDiaryAdapter(calendarArray)
                    fragmentHomeBinding?.todayRecyclerview?.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)


                    fragmentHomeBinding?.todayRecyclerview?.addItemDecoration(RecyclerDecoration(40))
                } else {

                }
            }
        fragmentHomeBinding?.todayRecyclerview?.adapter?.notifyDataSetChanged()

    }

    //파이어베이스 리사이클러뷰 투데이 대이터
    private fun friendGetData() {

        val receiveArray = ArrayList<CalendarReceiveModel>()
        val store = FirebaseFirestore.getInstance().collection("user_receive")
        val myEmail = SharedPreferenceFactory.getStrValue("userEmail", "")


        store.whereEqualTo("receiveUserEmail", myEmail)
            .addSnapshotListener { querySnapShot, error ->
                if (querySnapShot == null) return@addSnapshotListener
                receiveArray.clear()
                for (snapshot in querySnapShot.documents) {
                    receiveArray.add(snapshot.toObject(CalendarReceiveModel::class.java)!!)
                }
                Log.d("로그", "hometoday size ${receiveArray.size}")
                if (receiveArray.size > 0) {
                    //리사이클러뷰
                    fragmentHomeBinding?.friendRecyclerview?.adapter =
                        FriendShareDiaryAdapter(receiveArray)
                    fragmentHomeBinding?.friendRecyclerview?.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)


                    fragmentHomeBinding?.friendRecyclerview?.addItemDecoration(RecyclerDecoration(40))
                } else {

                }
            }
        fragmentHomeBinding?.friendRecyclerview?.adapter?.notifyDataSetChanged()

    }


    //친구목록다이로그
    private fun friendList() {
        val similarImageDialogView: View = layoutInflater.inflate(R.layout.friend_dialog, null)
        val dlg = friendCustumDialog(similarImageDialogView)


        dlg.isCancelable = true
        dlg.show(childFragmentManager, "similarImageDialog")

    }


    private fun todaysize() {

        val calendarDate: Date = Date()
        val todayFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val todayText = todayFormat.format(calendarDate).toString()

        var calendarModel: ArrayList<CalendarModel>
        calendarModel = ArrayList()

        sizeSnapshot = firestore?.collection("user_calendar")?.whereEqualTo("date", todayText)
            ?.whereEqualTo("uid", uid)
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                calendarModel.clear()
                if (querySnapshot == null)
                    return@addSnapshotListener
                for (snapshot in querySnapshot.documents) {
                    Log.d(ContentValues.TAG, "1")
                    calendarModel.add(snapshot.toObject(CalendarModel::class.java)!!)
                    Log.d(ContentValues.TAG, "2")
                    my_soul_text.text = "오늘의 마음이 ${calendarModel.size}개 있습니다"
                    Log.d(calendarModel.size.toString(), "size테스트")

                }

            }

    }

    private fun friendSize() {

        var myUserEmail = SharedPreferenceFactory.getStrValue("userEmail", null)

        var calendarReceiveModel: ArrayList<CalendarReceiveModel>
        calendarReceiveModel = ArrayList()

        sizeSnapshot =
            firestore?.collection("user_receive")?.whereEqualTo("receiveUserEmail", myUserEmail)
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    calendarReceiveModel.clear()
                    if (querySnapshot == null)
                        return@addSnapshotListener
                    for (snapshot in querySnapshot.documents) {
                        Log.d(ContentValues.TAG, "1")
                        calendarReceiveModel.add(snapshot.toObject(CalendarReceiveModel::class.java)!!)
                        Log.d(ContentValues.TAG, "2")
                        friend_soul_text.text = "친구의 마음이 ${calendarReceiveModel.size}개 있습니다"
                        Log.d(Constants.TAG, "size테스트1:${calendarReceiveModel.size}")
                    }

                }

    }


    private fun searchList(searchText: String) {


        val authStore = FirebaseFirestore.getInstance().collection("user_auth")

        val authArrayList = ArrayList<FriendDataModel>()

        authStore.get().addOnSuccessListener { querySnapshot ->

            for (i in querySnapshot) {

                if (searchText in i.data["name"].toString() || searchText in i.data["email"].toString()) {
                    Log.d("로그", "데이터 조회 in ${i.data["name"]}")

                    authArrayList.add(
                        FriendDataModel(
                            i.data["name"].toString(),
                            i.data["email"].toString()
                        )
                    )
                }

            }

            val dial = FriendAddDialog(authArrayList)

            dial.show(activity?.supportFragmentManager!!, "test")

        }


    }


}