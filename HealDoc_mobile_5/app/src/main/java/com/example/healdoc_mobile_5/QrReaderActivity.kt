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
import java.util.Calendar
//import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_qr_reader.*
import com.google.firebase.database.DataSnapshot
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
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
    internal var bitmap: Bitmap? = null
    private var etqr: EditText? = null
    private var iv: ImageView? = null
    private var btn: Button? = null
    private var tempshow: TextView? = null

    private val patient_name: String = "홍길동"
    private var pharmListener: ValueEventListener? = null
    private lateinit var pharmReference: DatabaseReference
    private lateinit var database: DatabaseReference

    var pname :ArrayList<String> = ArrayList<String>()
    var ptype :ArrayList<String> = ArrayList<String>()
    var ppurpose :ArrayList<String> = ArrayList<String>()
    var pday :ArrayList<String> = ArrayList<String>()
    var ptime :ArrayList<String> = ArrayList<String>()
    var pamount :ArrayList<String> = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_reader)

        database = FirebaseDatabase.getInstance().reference
        //databaseReference = database.getReference()

        iv = findViewById(R.id.iv) as ImageView
        etqr = findViewById(R.id.etqr) as EditText
        btn = findViewById(R.id.btn) as Button
        tempshow = findViewById(R.id.tempshow) as TextView

    }

    override fun onStart() {
        super.onStart()

        //우선 reference를 이용하여 디비와 연결하여 데이터 가져올 수 있게 함.
        pharmReference = FirebaseDatabase.getInstance().reference.child("홍길동")

        val pharmListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //val pharm = dataSnapshot.getValue()
                //tempshow!!.setText(pharm.toString())

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



                tempshow!!.setText(pday.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        pharmReference.addValueEventListener(pharmListener)


        //홍길동이라는 환자 이름이 db에 존재하면 그 환자의 처방 목록을 텍스터 에디터에 넣음
        btn!!.setOnClickListener {
            if (etqr!!.text.toString().trim { it <= ' ' }.length != 0) {
                try {
                    bitmap = TextToImageEncode(etqr!!.text.toString())
                    iv!!.setImageBitmap(bitmap)
                    //val path = saveImage(bitmap)  //give read write permission
                    //Toast.makeText(this@QrReaderActivity, "QRCode saved to -> $path", Toast.LENGTH_SHORT).show()
                } catch (e: WriterException) {
                    e.printStackTrace()
                }

            }
        }


    }


    @Throws(WriterException::class)
    private fun TextToImageEncode(Value: String): Bitmap? {
        val bitMatrix: BitMatrix
        try {
            bitMatrix = MultiFormatWriter().encode(
                Value,
                BarcodeFormat.QR_CODE,
                QRcodeWidth, QRcodeWidth, null
            )

        } catch (Illegalargumentexception: IllegalArgumentException) {

            return null
        }

        val bitMatrixWidth = bitMatrix.getWidth()

        val bitMatrixHeight = bitMatrix.getHeight()

        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                pixels[offset + x] = if (bitMatrix.get(x, y))
                    resources.getColor(R.color.black)
                else
                    resources.getColor(R.color.white)
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }

    companion object {

        val QRcodeWidth = 500
        private val IMAGE_DIRECTORY = "/QRcodeDemonuts"
    }

}