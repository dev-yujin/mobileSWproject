package com.example.healdoc_mobile_5

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var authStateListener: FirebaseAuth.AuthStateListener? = null
    var googleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //로그인 세션을 체크하는 부분
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->

            Log.e("LLPP", "로그인")

            var user = firebaseAuth.currentUser
            if (user != null) {
                var iT = Intent(this, MainActivity::class.java)
                startActivity(iT)
            }
        }

        //구글 로그인 옵션
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        //구글 로그인 클래스
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        //구글로 접속
        btn_google.setOnClickListener {
            var signInIntent = googleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            //구글 로그인에 성공했을때 넘어오는 토큰값을 가지고 있는 Task
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            //ApiException 캐스팅
            var account = task.getResult(ApiException::class.java)
            //Credentail 구글 로그인에 성공했다는 인증서
            var credential = GoogleAuthProvider.getCredential(account?.idToken, null)

            //인증서를 Firebase에 넘겨줌(구글 사용자가 등록)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "구글아이디 인증성공", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    override fun onPause() {
        super.onPause()
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener!!)
    }
}