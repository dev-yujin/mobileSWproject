package com.example.healdoc_mobile_5


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_receipt.*
import kotlinx.android.synthetic.main.list_book_item.*
//import sun.jvm.hotspot.utilities.IntArray
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class ReceiptFragment : Fragment() {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance() //DB
    var adapter : ListViewAdapter = ListViewAdapter()
    //    var re_adapter : ListViewAdapter = ListViewAdapter()
    var temp_adapter : ListViewAdapter = ListViewAdapter()
    var user = "홍길동"
    var bookID = ""

    var calledAlready = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (!calledAlready)
//        {
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true) // 다른 인스턴스보다 먼저 실행되어야 한다.
//            calledAlready = true;
//        }

        InOnCreate()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_receipt, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        InOnViewCreate()

    }

    fun InOnCreate() {
        // DB
        val myRef : DatabaseReference = database.getReference(user).child("예약")
        var now : LocalDate = LocalDate.now() //오늘 날짜
        var today = now.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일")) //오늘날짜

        var info : DatabaseReference
        var flag : Int = 1 //데이터 존재할때만 요소에 추가하기 위함
        Log.w("myRef","${today}")

        if(myRef.child(today).key == today){
            info = myRef.child(today)
//            Log.w("INFOCOUNt","${info}")
            flag = 1 //존재함
//            Log.w("myRef","yesyes")
        }
        else {
            info = myRef
            flag = 0 //존재하지 않음
        }
        info.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(flag == 1) {
                    adapter.clearItem()
                    for (snapshot in p0.children) {
                        var post : bookInfo? = snapshot.getValue(bookInfo::class.java)
                        Log.w("COUNT","${snapshot.key}")
                        //오늘 날짜의 예약이 있으면 쓰기 _ 어댑터에 요소 추가
                        adapter.addItem(today, "${post?.sub}", "${post?.hour}", "${post?.tea}", "${snapshot.key}")

                    }
                }
                adapter.notifyDataSetChanged()//어댑터에 리스트가 바뀜을 알린다
                onSaveInstanceState(Bundle())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Log.e("onRESUME!!!!!","")
    }


    fun InOnViewCreate(){

        // DB
        val myRef : DatabaseReference = database.getReference(user).child("진료내역")
        var now : LocalDate = LocalDate.now() //오늘 날짜
        var today = now.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일")) //오늘날짜

        //타이머 핸들러 - fragment refresh
        val handlerTask = object : Runnable {
            override fun run() {
                refresh()
            }
        }
        val handler = Handler() //핸들러 변수 생성

        //리스트 뷰에 어댑터 연결
        list_book.adapter = adapter

        list_book.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val select = "10"
            //접수신청된 진료가 있으면, 어댑터 초기화 후 대기번호 발급

            //firebase 진료완료 처리
            writeNewCom("${txt_date.text}", "${txt_time.text}", "${txt_tea.text}", "${txt_sub.text}", "${txt_id.text}")

            temp_adapter.clearItem()
            temp_adapter.addItem("접수중인 진료가 있습니다.", "${txt_sub.text}", "${txt_time.text}", "${txt_tea.text}", "${txt_id.text}")
            list_book.adapter = temp_adapter
            temp_adapter.notifyDataSetChanged()//어댑터에 리스트가 바뀜을 알린다
            list_book.isEnabled = false //리스트 비활성화

            //대기번호를 랜덤으로 생성! -> 그 숫자만큼 분을 기다림 (시연을 위해 초 단위로 설정 할 것임)
            val random = Random()
            val num = random.nextInt(8) + 2 //2-10까지 랜덤으로 생성
            Log.e("listbook in ClickLisner?????!!!!!!","${list_book}")

            txt_waitnum.text = "$num"

            //            val intent = Intent(context, ReceiptReceiver::class.java)
//            val sender = PendingIntent.getBroadcast(this, 0, intent, 0)
//
//            // 알람을 받을 시간을 num분 뒤로 설정
//            val calendar = Calendar.getInstance()
//            calendar.timeInMillis = System.currentTimeMillis()
//            calendar.add(Calendar.SECOND, num)
//
//            // 알람 매니저에 알람을 등록
//            val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            am.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, sender)

            var alarmMgr: AlarmManager? = null
            lateinit var alarmIntent: PendingIntent
            Log.e("listbook after AlarmMgr Created?????!!!!!!","${list_book}")

            alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(context, ReceiptReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, 0)
            }
            Log.e("listbook after Set PendingIntent?????!!!!!!","${list_book}")

            var calendar : Calendar = Calendar.getInstance()
            calendar.setTimeInMillis(System.currentTimeMillis())
            calendar.add(Calendar.SECOND, num-1)


            var time : Long = num.toLong() * 1000
            //num초 뒤에 프래그먼트 refresh
            handler.postDelayed(handlerTask, time)

            //num-1초뒤 울리는 알람
            alarmMgr?.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmIntent
            )
        }

    }

    //유저/진료완료에 데이터 쓰기
    private fun writeNewCom(bd: String, bh: String, bt: String, bs: String, bid: String) {

        val i = bookInfo(bh, bt, bs, " ")
        database.getReference(user).child("진료완료").child(bd).push().setValue(i)
        Log.e("bid","$bid")
        database.getReference(user).child("예약").child(bd).child(bid).removeValue()

        //데이터 지우기
        //예액
    }


    fun refresh(){
//        InOnCreate()
//        InOnViewCreate()
        //프래그먼트 초기화
        var fm = this.fragmentManager!!.beginTransaction()
        fm.detach(this).attach(this).commit()
    }


}
