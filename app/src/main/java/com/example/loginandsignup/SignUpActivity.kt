package com.example.loginandsignup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.loginandsignup.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivitySignUpBinding.inflate(layoutInflater)
            setContentView(binding.root)
            firebaseAuth = FirebaseAuth.getInstance()

            OnClickButton()
            SignIn()
        }

    fun OnClickButton(){
        binding.button.setOnClickListener{
            val email = binding.email.text.toString()
            val pw = binding.password.text.toString()
            val rePw = binding.repassword.text.toString()
            if(email.isNotEmpty() && pw.isNotEmpty() && rePw.isNotEmpty()){
                if(pw == rePw){
                    firebaseAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent = Intent(this, SignUpActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT);
                        }
                    }
                }else{
                    Toast.makeText(this, "Password is not matching",Toast.LENGTH_SHORT)
                        .show()
                }
            }else{
                Toast.makeText(this,"Empty Fields Are not Allowed!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun SignIn(){
        binding.msgTextView.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
