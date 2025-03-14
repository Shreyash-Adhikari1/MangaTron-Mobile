package com.example.mangatronmobile.repository

import com.example.mangatronmobile.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserRepositoryImpl: UserRepository {
    //for authentication (Firebase)
    var  auth: FirebaseAuth = FirebaseAuth.getInstance()

    //For realtime database
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    //reference
    var ref : DatabaseReference = database.reference.child("users")


    override fun login(email: String, password: String,
                       callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    callback(true,"Login Successful")
                }else{
                    callback(false,it.exception?.message.toString())
                }

            }

    }

    override fun signup(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        callback(true, "Registration Successful", userId)
                    } else {
                        callback(false, "Error: User ID is null", "")
                    }
                } else {
                    callback(false, task.exception?.localizedMessage ?: "Unknown error", "")
                }
            }
    }


    override fun addUserToDatabase(
        userId: String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId.toString()).setValue(userModel)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    callback(true,"Registration Success")
                }else{
                    callback(false,it.exception?.message.toString())
                }
            }
    }
    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    callback(true,"Reset email sent to $email")
                }else{
                    callback(false,it.exception?.message.toString())
                }
            }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return  auth.currentUser
    }
}