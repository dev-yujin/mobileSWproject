package com.example.healdoc_mobile_5


import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment

//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar

//import android.support.v4.content.ContextCompat
import androidx.core.content.ContextCompat

//import android.R
import android.app.Activity
import android.content.Intent

//import android.support.v4.app.SupportActivity
import androidx.core.app.ComponentActivity

//import android.support.v4.app.SupportActivity.ExtraData
import androidx.core.app.ComponentActivity.ExtraData

//import android.support.v4.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemService

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlinx.android.synthetic.main.activity_qr_reader.*

//import sun.jvm.hotspot.utilities.IntArray





class QrReaderActivity : AppCompatActivity() {
    internal var bitmap: Bitmap? = null
    private var etqr: EditText? = null
    private var show_prescrip: ImageView? = null
    private var btn: Button? = null

    //++yujin : 랜덤으로 값을 넣어주면 될듯
    //private var ran_etqr = 777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_reader)


        //++yujin : 지도 화면으로 넘어가는 리스너
        btn_drug.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }




        show_prescrip = findViewById(R.id.show_prescrip) as ImageView
/*        etqr = findViewById(R.id.etqr) as EditText
        btn= findViewById(R.id.btn) as Button*/
/*

        btn!!.setOnClickListener {
            //++yujin : 비었을때 하는 부분을 없애기
//            if (etqr!!.text.toString().trim { it <= ' ' }.length == 0) {
//                Toast.makeText(this@QrReaderActivity, "Enter String!", Toast.LENGTH_SHORT).show()
//            } else {
                try {
                    //++yujin :  버튼만 눌러서 나오게 할것임 / 스트링을 바로 넣어줌
                    bitmap = TextToImageEncode(ran_etqr.toString())
//                    bitmap = TextToImageEncode(etqr!!.text.toString())
                    iv!!.setImageBitmap(bitmap)
                    val path = saveImage(bitmap)  //give read write permission
                    Toast.makeText(this@QrReaderActivity, "QRCode saved to -> $path", Toast.LENGTH_SHORT).show()
                } catch (e: WriterException) {
                    e.printStackTrace()
                }

//            }
        }
    }
*/


     class QrReaderActivity : Activity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_qr_reader)

        }
    }

 /*   fun saveImage(myBitmap: Bitmap?): String {
        val bytes = ByteArrayOutputStream()
        myBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs())
            wallpaperDirectory.mkdirs()
        }

        try {
            val f = File(wallpaperDirectory, Calendar.getInstance()
                .timeInMillis.toString() + ".jpg")
            f.createNewFile()   //give read write permission
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.path),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.absolutePath)

            return f.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""

    }*/
/*

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
                    ContextCompat.getColor(applicationContext, R.color.black)

                //resources.getColor(R.color.black)
                else
                //resources.getColor(R.color.white)
                    ContextCompat.getColor(applicationContext, R.color.white)
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
*/

}}