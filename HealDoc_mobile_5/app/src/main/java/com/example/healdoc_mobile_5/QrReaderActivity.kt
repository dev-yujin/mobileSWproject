package com.example.healdoc_mobile_5
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.healdoc_mobile_5.R
import com.example.healdoc_mobile_5.model.Pharm
import com.example.healdoc_mobile_5.model.Url
import com.google.firebase.database.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
//import sun.jvm.hotspot.utilities.IntArray
//import android.R
import java.util.regex.Matcher
import java.util.regex.Pattern


class QrReaderActivity : AppCompatActivity() {
    internal var bitmap: Bitmap? = null
    private var iv: ImageView? = null
    private var linkview: TextView? = null
    var urllink: String? = null
    private lateinit var urlReference: DatabaseReference
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_reader)

        iv = findViewById(R.id.iv) as ImageView
        linkview = findViewById(R.id.linkview) as TextView

        val person = "홍길동"
        urlReference = FirebaseDatabase.getInstance().getReference("$person/url")

        val urlListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                urllink = dataSnapshot.getValue().toString()
                Log.d("QrReaderActivity", "In listener : ${urllink}")
                if (urllink != null) {
                    bitmap = TextToImageEncode(urllink!!)
                    Log.d("QrReaderActivity", "urllink is not null : ${urllink}")
                    iv!!.setImageBitmap(bitmap)
                    //Linkify.addLinks(linkview, Linkify.urllink)
                    val mTransform = object : Linkify.TransformFilter {
                        override fun transformUrl(match: Matcher, url: String): String {
                            return ""
                        }
                    }
                    val pattern1 = Pattern.compile("나의 처방 내역 자세히 보기")
                    Linkify.addLinks(linkview, pattern1, urllink,null,mTransform);
                }
               else {
                    Log.d("QrReaderActivitiy", "error here!!")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("QrReaderActivity", "loadUrl:onCancelled", databaseError.toException())
            }
        }
        urlReference.addValueEventListener(urlListener)


    }

    //@Throws(WriterException::class)
    private fun TextToImageEncode(Value: String): Bitmap? {
        val bitMatrix: BitMatrix
        Log.d("QrReaderActivitiy", "try bitMat!!")
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
        Log.d("QrReaderActivitiy", "return next!!")
        return bitmap
    }

    companion object {

        val QRcodeWidth = 500
        private val IMAGE_DIRECTORY = "/QRcodeDemonuts"
    }

}