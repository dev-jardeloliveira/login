package com.example.loginandsignup

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.loginandsignup.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        OnClick()
        SignUp()

    }

    fun OnClick(){
        binding.button.setOnClickListener{
            val email = binding.email.text.toString()
            val pw = binding.password.text.toString()
            if(email.isNotEmpty() && pw.isNotEmpty())
            {
                    firebaseAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,"User not found!", Toast.LENGTH_SHORT)
                            .show();
                        }
                    }

            }else{
                Toast.makeText(this,"Empty Fields Are not Allowed!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun SignUp(){
        binding.msgTextView.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}