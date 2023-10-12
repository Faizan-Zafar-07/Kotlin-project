package com.example.kotlinproject.uii


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinproject.Activity_Signup
import com.example.kotlinproject.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.lang.Exception

class ActivityLogin : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var Login_btn : Button
    lateinit var Signup_btn_login : TextView
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        email=findViewById(R.id.email_add)
        password=findViewById(R.id.Passwrd)
        Login_btn=findViewById(R.id.Login)
        Signup_btn_login=findViewById(R.id.signup_on_login)
        firestore=FirebaseFirestore.getInstance()


        Login_btn.setOnClickListener(object :OnClickListener{
            override fun onClick(v: View?) {
                userLogin()
            }
        })

        Signup_btn_login.setOnClickListener(object :OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@ActivityLogin, Activity_Signup::class.java)
                startActivity(intent)
            }

        })

    }

    private fun userLogin() {
        val userEmail = email.text.toString()
        val userPassword = password.text.toString()

        firestore.collection("newUser").whereEqualTo("email_sp",userEmail)
            .get()
            .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                override fun onComplete(task: Task<QuerySnapshot>) {
                    if (task.isSuccessful) {
                        val querySnapshot = task.result
                        if (querySnapshot != null && !querySnapshot.isEmpty) {
                            val document =
                                querySnapshot.documents[0] // Assuming one document matches the email

                            val correctPassword = document.getString("creat_pass_sp")
                            if (correctPassword == userPassword) {
                                val intent = Intent(this@ActivityLogin, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this@ActivityLogin,
                                    "Incorrect Password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this@ActivityLogin,
                                "Email not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })

                .addOnFailureListener (object :OnFailureListener{
                    override fun onFailure(p0: Exception) {
                        Toast.makeText(this@ActivityLogin, p0.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
    }
}