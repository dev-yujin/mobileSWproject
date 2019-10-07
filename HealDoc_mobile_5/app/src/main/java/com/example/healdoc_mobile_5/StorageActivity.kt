package com.example.healdoc_mobile_5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

abstract class StorageActivity : AppCompatActivity() {

    lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        storage = FirebaseStorage.getInstance()

        includesForCreateReference()

        val intent = Intent(this,QrReaderActivity::class.java)
        startActivity(intent)
    }

    private fun includesForCreateReference(){
        val storage = FirebaseStorage.getInstance()

        var storageRef = storage.reference

        var imagesRef: StorageReference? = storageRef.child("images")

        var patient1Ref = storageRef.child("images/patient1.png")

        imagesRef = patient1Ref.parent

        val roofRef = patient1Ref.root
        val patient2Ref = patient1Ref.parent?.child("patient2.png")

        val nullRef = patient1Ref.root.parent

        patient1Ref.path
        patient1Ref.name
        patient1Ref.bucket

        storageRef = storage.reference
        imagesRef = storageRef.child("images")

        val filename = "patient1.png"
        patient1Ref = imagesRef.child(filename)

        val path = patient1Ref.path

        val name = patient1Ref.name

        imagesRef = patient1Ref.parent


    }

    /* 우리가 사진 업로드 할 일이 있을까요..? -youngin
    fun includesForUploadFiles(){

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.child("")

    }*/

    fun includesForDownloadFiles(){

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val pathReference = storageRef.child("images/patient1.png");
        val gsReference = storage.getReferenceFromUrl("gs://mobilesw-b1730.appspot.com/patient1.png")

        val httpsReference = storage.getReferenceFromUrl(
            "https://firebasestorage.googleapis.com/v0/b/mobilesw-b1730.appspot.com/o/patient1.png?alt=media&token=71d88d2b-d24a-423c-a139-d4fe0fdfbd8a")

        var patient1Ref = storageRef.child("images/patient.png")

        val ONE_MEGABYTE: Long = 1024 * 1024
        patient1Ref.getBytes(ONE_MEGABYTE).addOnSuccessListener {

        }.addOnFailureListener{

        }

        patient1Ref = storageRef.child("images/patient1.png")

        val localFile = File.createTempFile("images", "png")

        patient1Ref.getFile(localFile).addOnSuccessListener {

        }.addOnFailureListener{

        }

        storageRef.child("users/me/profile.png").downloadUrl.addOnSuccessListener {

        }.addOnFailureListener{

        }

        storageRef.child("users/me/profile.png").getBytes(Long.MAX_VALUE).addOnSuccessListener {

        }.addOnFailureListener{

        }

    }
}
