package com.example.healdoc_mobile_5


import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
//import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_qr_reader.*
import com.google.firebase.database.DataSnapshot
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.zxing.EncodeHintType
import java.util.*
import kotlin.collections.ArrayList
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.journeyapps.barcodescanner.BarcodeEncoder


//import sun.jvm.hotspot.utilities.IntArray



//import sun.jvm.hotspot.utilities.IntArray


/*data class PatientPharm(var patientName: String? = null){
    patientName = t
    var pname :ArrayList<String> = ArrayList<String>()
    var ptype :ArrayList<String> = ArrayList<String>()
    var ppurpose :ArrayList<String> = ArrayList<String>()
    var pday :ArrayList<String> = ArrayList<String>()
    var ptime :ArrayList<String> = ArrayList<String>()
    var pamount :ArrayList<String> = ArrayList<String>()
}*/

class QrReaderActivity : AppCompatActivity() {

/*    var hints : Hashtable
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8")*/

    internal var bitmap: Bitmap? = null
    private var etqr: EditText? = null
    private var iv: ImageView? = null
    private var btn: Button? = null
    private var tempshow: TextView? = null

    private val patient_name: String = "홍길동"
    private var pharmListener: ValueEventListener? = null
    private lateinit var pharmReference: DatabaseReference
    private lateinit var database: DatabaseReference


    //var hints: Hashtable
    //var hints = hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

    //var hints = Hashtable.apply { put(EncodeHintType.CHARACTER_SET, "UTF-8") }


    //var result :ArrayList<String> = arrayListOf()
    var result: String? = null

    var pname :ArrayList<String> = arrayListOf("포타스틴오디 정", "코푸 정", "뮤코라제 정", "토지세프정250mg", "타스멘정")
    var ptype :ArrayList<String> = arrayListOf("[항히스타민/항소양제]","[진해거담제 & 기침감기약]","[소염효소제]","[2세대 세팔로스포린계]", "[기타 진통제]")
    var ppurpose :ArrayList<String> = arrayListOf("항히스타민제, 항알레르기약, 다년성 알레르기비염, 만성 두드러기 피부염, 피부 질환에 사용","기침, 가래의 증상을 완화",
        "효소제제: 외상 후 종창 완화 작용 객담, 용해 작용: 객담 배출을 용이", "세팔로스포린계항생제: 세균에 의한 각종 감염증 치료", "해열진통제로 열을 내리고 통증을 줄여줌")
    var pday :ArrayList<String> = arrayListOf("3일분","3일분","3일분","3일분","3일분")
    var ptime :ArrayList<String> = arrayListOf("2회","3회","3회","3회","3회")
    var pamount :ArrayList<String> = arrayListOf("1정","1정","1정","1정","1정")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_reader)

        database = FirebaseDatabase.getInstance().reference
        //databaseReference = database.getReference()

        iv = findViewById(R.id.iv) as ImageView
        etqr = findViewById(R.id.etqr) as EditText
        btn = findViewById(R.id.btn) as Button
        tempshow = findViewById(R.id.tempshow) as TextView

        for(i in 0..4){
            result += ("약이름: " + pname[i] +"\n"+ "종류: " + ptype[i]+"\n"+ "효능: " + ppurpose[i]+"\n"
                    + "일: " + pday[i]+"\n"+ "횟수: " + ptime[i]+"\n"+ "정: " + pamount[i]+"\n"+"\n")
        }

        var qr_bef : CreateQRCode = CreateQRCode()
        bitmap = qr_bef.createQRCode(result!!)
        //bitmap = TextToImageEncode(result!!)
        iv!!.setImageBitmap(bitmap)

    }

    override fun onStart() {
        super.onStart()

        //우선 reference를 이용하여 디비와 연결하여 데이터 가져올 수 있게 함.
        pharmReference = FirebaseDatabase.getInstance().reference.child("홍길동")

        val pharmListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val pharm = dataSnapshot.getValue()
                tempshow!!.setText(pharm.toString())

                /*for(temp in dataSnapshot.children){
*//*                    if(temp.key.equals("day")){
                        pday.add(temp.getValue().toString())
                    }*//*
                    pday.add(temp.key.equals("day").toString())
                    ppurpose.add(temp.key.equals("purpose").toString())
                    ptime.add(temp.key.equals("time").toString())
                    ptype.add(temp.key.equals("type").toString())
                    pamount.add(temp.key.equals("amount").toString())
                    pname.add(temp.key.equals("name").toString())
                }*/

                for (snapshot in dataSnapshot.children) {
                    pday.add(snapshot.children.indexOf(snapshot).toString())
                    // Log.d("MainActivity", "Single ValueEventListener : " + snapshot.value!!)
                }



                //tempshow!!.setText(pday.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        pharmReference.addValueEventListener(pharmListener)


        //홍길동이라는 환자 이름이 db에 존재하면 그 환자의 처방 목록을 텍스터 에디터에 넣음




    }

    inner class CreateQRCode {

        fun createQRCode(context: String): Bitmap? {

            var bitmap: Bitmap? = null

            val multiFormatWriter = MultiFormatWriter()
            try {
                /* Encode to utf-8 */
                var hints = Hashtable<EncodeHintType, String>().apply { put(EncodeHintType.CHARACTER_SET, "UTF-8") }

                val bitMatrix =
                    multiFormatWriter.encode(context, BarcodeFormat.QR_CODE, 600, 600, hints)
                val barcodeEncoder = BarcodeEncoder()
                bitmap = barcodeEncoder.createBitmap(bitMatrix)

            } catch (e: WriterException) {
                e.printStackTrace()
            }

            return bitmap
        }
    }


    companion object {

        val QRcodeWidth = 500
        private val IMAGE_DIRECTORY = "/QRcodeDemonuts"
    }

}