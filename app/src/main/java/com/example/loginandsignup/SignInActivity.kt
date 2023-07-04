package com.example.loginandsignup

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.example.loginandsignup.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executor

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo
    private lateinit var executor: Executor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        executor = ContextCompat.getMainExecutor(this)

        Biometric()
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

    fun Biometric(){
        biometricPrompt = BiometricPrompt(this@SignInActivity, executor,object:BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                SignUp()
            }
        })
        promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint or face")
            .setNegativeButtonText("Cancel")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
    fun SignUp(){
        binding.msgTextView.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}