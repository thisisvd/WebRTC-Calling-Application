package com.vdcodeassociate.webrtccallingapplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vdcodeassociate.webrtccallingapplication.repository.MainRepository
import com.vdcodeassociate.webrtccallingapplication.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    // view binding
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var mainRepository: MainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply {

            val loggedUserName = mainRepository.getLoggedUsername()
            if (loggedUserName.isNotEmpty()) {
                goToMainActivity()
            }

            onClickListeners()
        }
    }

    // on click listeners
    private fun onClickListeners() {
        binding.apply {

            // sign in btn clicked
            btn.setOnClickListener {
                mainRepository.login(
                    usernameEt.text.toString(), passwordEt.text.toString()
                ) { isDone, message ->
                    if (!isDone) {
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                    } else {
                        goToMainActivity()
                    }
                }
            }
        }
    }

    // navigate to main activity
    private fun goToMainActivity() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }
}