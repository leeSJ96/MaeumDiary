package com.poly.test.diaryapp.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.App
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.adapters.UserListAdapter
import com.poly.test.diaryapp.databinding.DialogFriendAddBinding
import com.poly.test.diaryapp.models.FriendAddModel
import com.poly.test.diaryapp.models.FriendDataModel
import com.poly.test.diaryapp.utils.SharedPreferenceFactory

class FriendAddDialog(private val userArray : ArrayList<FriendDataModel>) : DialogFragment(), IHomePosition {




    lateinit var binding : DialogFriendAddBinding
    private var friendAddModel = FriendAddModel()
    private var friendDataModel = FriendDataModel()
    private var friendClickCheck = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.d("로그","friendDialog onCreateView - call")

        binding = DialogFriendAddBinding.inflate(inflater, container,false)

        defaultSetting()

        return binding.root

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.dialog_fullscreen)
        isCancelable = true
    }



    private fun defaultSetting() {


        Log.d("로그","defaultSetting call - 배열 크기 ${userArray.size}")


        if(userArray.size == 0) {
            binding.userRv.visibility = View.INVISIBLE
            binding.userNoData.visibility = View.VISIBLE

            binding.addFriendBtn.isEnabled = false

        } else {

            binding.userRv.adapter = UserListAdapter(userArray, this)
            binding.userRv.layoutManager = LinearLayoutManager(App.instance)

            binding.userRv.visibility = View.VISIBLE
            binding.userNoData.visibility = View.INVISIBLE

        }


        binding.addFriendBtn.setOnClickListener {

            if(friendClickCheck) {
                friendAdd()
            }
        }

        binding.closeBtn.setOnClickListener {
            dismiss()
        }



    }


    private fun friendAdd() {

        val friendStore = FirebaseFirestore.getInstance().collection("user_friends")

        val uid = SharedPreferenceFactory.getStrValue("userToken", "")
        val timeStamp = System.currentTimeMillis().toString()
        val myPath = "${uid}_${timeStamp}"

        val myEmail = SharedPreferenceFactory.getStrValue("userEmail","")
        val myName = SharedPreferenceFactory.getStrValue("userName","")




        friendAddModel = FriendAddModel(
            friendName = friendDataModel.friendName,
            friendEmail = friendDataModel.friendEmail,
            myName = myName,
            myEmail = myEmail,
            myPath = myPath
        )



        when (myName) {

            friendDataModel.friendName -> {
                Toast.makeText(App.instance,"나 자신은 영원한 인생의 친구입니다.",Toast.LENGTH_SHORT).show()

            }
            else -> {

                Log.d("로그","친구 이름 ${friendDataModel.friendName}")

                friendStore.whereEqualTo("friendName",friendDataModel.friendName).whereEqualTo("myName",myName).get().addOnSuccessListener { querySnapshot ->

                    when {


                        querySnapshot == null || querySnapshot.size() == 0 -> {

                            friendStore.document(myPath).set(friendAddModel).addOnSuccessListener {

                                Toast.makeText(App.instance,"친구 추가 되었습니다.",Toast.LENGTH_SHORT).show()
                                dismiss()
                            }.addOnFailureListener {
                                Log.d("로그","error $it")
                                Toast.makeText(App.instance,"서버 에러입니다.",Toast.LENGTH_SHORT).show()
                            }

                        }
                        querySnapshot.size() > 0 -> {


                            Log.d("로그","배열값 ${querySnapshot.size()}")
                            Toast.makeText(App.instance,"이미 친구로 등록된 유저입니다.",Toast.LENGTH_SHORT).show()

                        }
                    }

                }




            }
        }

    }





    override fun onClickListener(position: Int, friendModel: FriendDataModel) {

        friendClickCheck = true

        friendDataModel = friendModel

    }
}