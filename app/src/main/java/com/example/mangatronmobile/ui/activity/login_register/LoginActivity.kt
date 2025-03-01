package com.example.mangatronmobile.ui.activity.login_register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mangatronmobile.R
import com.example.mangatronmobile.databinding.ActivityLoginBinding
import com.example.mangatronmobile.repository.UserRepositoryImpl
import com.example.mangatronmobile.ui.activity.admin.AdminActivity
import com.example.mangatronmobile.ui.activity.home.HomeActivity
import com.example.mangatronmobile.utils.LoadingUtils
import com.example.mangatronmobile.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var userViewModel: UserViewModel
    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils= LoadingUtils(this)

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.btnSignupnavigate.setOnClickListener({
            val intent= Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        })

        binding.btnLogin.setOnClickListener {
            loadingUtils.show()

            var email :String = binding.editEmail.text.toString()
            var password :String = binding.editPassword.text.toString()

            userViewModel.login(email,password){
                    success,message->
                if(success){
                    loadingUtils.dismiss()
                    Toast.makeText(this@LoginActivity,message, Toast.LENGTH_LONG).show()

                    val intent = if (email == "mangatronadmin@admin.com") {
                        Intent(this@LoginActivity, AdminActivity::class.java) // Admin Redirect
                    } else {
                        Intent(this@LoginActivity, HomeActivity::class.java)
                    }
                    startActivity(intent)
                    finish()


                }else{
                    loadingUtils.dismiss()
                    Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()
                }
            }
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
