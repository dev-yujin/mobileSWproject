package com.example.healdoc_mobile_5


import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar



class QrReaderActivity : AppCompatActivity() {
    internal var bitmap: Bitmap? = null
    private var etqr: EditText? = null
    private var iv: ImageView? = null
    private var btn: Button? = null
    //private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_reader)

        //database = FirebaseDatabase.getInstance().reference

        iv = findViewById(R.id.iv) as ImageView
        etqr = findViewById(R.id.etqr) as EditText
        btn = findViewById(R.id.btn) as Button


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