package com.example.healdoc_mobile_5

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

import android.widget.TextView

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private var mGoogleApiClient: GoogleApiClient? = null
    private var mAuth: FirebaseAuth? = null

    public override fun onStart() {
        super.onStart()
        // 활동을 초기화할 때 사용자가 현재 로그인되어 있는지 확인합니다.
        val currentUser = mAuth?.getCurrentUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()  //싱글톤 패턴으로 작동이된다

        // Google 로그인 옵션
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        //fireabase realtime database test 문구
//        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
//        val myRef : DatabaseReference = database.getReference("message")
//        myRef.setValue("user")

        //구글로그인 버튼에 대한 이벤트
        val button = findViewById(R.id.btn_google) as SignInButton
        button.setOnClickListener {
            //이벤트 발생했을때, 구글로그인 버튼에 대한 (구글정보를 인텐트로 넘기는 값)
            //"방금 로그인한다고 하는사람이 구글 사용자니? "물어보는로직
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    //Intent Result값 반환되는 로직
    override fun  onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) { //구글버튼 로그인 누르고, 구글사용자 확인되면 실행되는 로직
                // Google Sign In was successful, authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!) //구글이용자 확인된 사람정보 파이어베이스로 넘기기
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    //파이어베이스로 값넘기기
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        //파이어베이스로 받은 구글사용자가 확인된 이용자의 값을 토큰으로 받고
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "signInWithCredential:success");
                    //   FirebaseUser user = mAuth.getCurrentUser();
                    // updateUI(user);
                    Toast.makeText(this@LoginActivity, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                    val loginIntent = Intent(this, MainActivity::class.java) //새로운 activity에 넘길 intent

                    val user = FirebaseAuth.getInstance().currentUser       //구글 계정으로 로그인된 사용자의 정보
                    user?.let {
                        // Name, email address, and profile photo Url
                        val name = user.displayName
                        loginIntent.putExtra( "UserName",name) // 넘길 intent에 extra넣겠다.
                        startActivityForResult(loginIntent, 1)

                    }

                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "signInWithCredential:failure", task.getException());
                    // Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",
                    //          Toast.LENGTH_SHORT).show();
                    //  updateUI(null);
                    // If sign in fails, display a message to the user.

                }

                // ...
            })
    }

    override fun onConnectionFailed(@NonNull connectionResult: ConnectionResult) {
        FirebaseAuth.getInstance().signOut() // 로그아웃시 일단 이렇게 생겼는데, 로그아웃 버튼은 안만듬.
    }

    companion object {
        private val RC_SIGN_IN = 10
    }
}
