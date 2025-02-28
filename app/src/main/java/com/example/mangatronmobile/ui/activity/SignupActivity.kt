package com.example.mangatronmobile.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mangatronmobile.R
import com.example.mangatronmobile.databinding.ActivitySignupBinding
import com.example.mangatronmobile.model.UserModel
import com.example.mangatronmobile.repository.UserRepositoryImpl
import com.example.mangatronmobile.utils.LoadingUtils
import com.example.mangatronmobile.viewmodel.UserViewModel

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    lateinit var userViewModel: UserViewModel
    lateinit var loadingUtils: LoadingUtils



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //yeha initialize garney
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils= LoadingUtils(this)


        var repo= UserRepositoryImpl()
        userViewModel= UserViewModel(repo)


        binding.signUp.setOnClickListener {
            loadingUtils.show()

            var email = binding.registerEmail.text.toString()
            var password = binding.registerPassword.text.toString()
            var firstName = binding.registerFname.text.toString()
            var lastName = binding.registerLName.text.toString()
            var address = binding.registerAddress.text.toString()
            var phoneNumber=binding.registerContact.text.toString()

            userViewModel.signup(email,password){
                    success,message,userId->
                if (success){
                    var userModel = UserModel(
                        userId.toString(),firstName,
                        lastName, address,
                        phoneNumber, email
                    )
                    userViewModel.addUserToDatabase(userId,userModel){
                            success,message->
                        if (success){
                            var intent =Intent(this@SignupActivity, LoginActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(
                                this@SignupActivity,
                                message, Toast.LENGTH_LONG
                            ).show()
                        }else{
                            loadingUtils.dismiss()
                            Toast.makeText(
                                this@SignupActivity,
                                message, Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }else{
                    Toast.makeText(this@SignupActivity,
                        message, Toast.LENGTH_LONG).show()
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