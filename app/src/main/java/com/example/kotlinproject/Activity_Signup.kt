package com.example.kotlinproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kotlinproject.Models.signup_model
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class Activity_Signup : AppCompatActivity() {

    lateinit var first__Name : EditText
    lateinit var last_Name : EditText
    lateinit var email_enter : EditText
    lateinit var passwrd_enter : EditText
    lateinit var passwrd_confirm : EditText
    lateinit var signup_btn : Button
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        first__Name=findViewById(R.id.firstName)
        last_Name=findViewById(R.id.lastName)
        email_enter=findViewById(R.id.email_signup)
        passwrd_enter=findViewById(R.id.creat_pass_signup)
        passwrd_confirm=findViewById(R.id.confirm_Pass_signup)
        signup_btn=findViewById(R.id.signup_btn)
        firebaseFirestore=FirebaseFirestore.getInstance()


        signup_btn.setOnClickListener(object :OnClickListener{
            override fun onClick(v: View?) {
                newUser(signup_model(
                    "",
                    first__Name.text.toString(),
                    last_Name.text.toString(),
                    email_enter.text.toString(),
                    passwrd_enter.text.toString(),
                    passwrd_confirm.text.toString()
                )

                )
            }

        })


    }

    private fun newUser(signupModel: signup_model) {

        val documentReference = firebaseFirestore.collection("Users").document()
        signupModel.id = documentReference.id
        documentReference.set(signupModel)
            .addOnCompleteListener(object :OnCompleteListener<Void>{
                override fun onComplete(task : Task<Void>) {
                    if(task.isSuccessful){
                        Toast.makeText(this@Activity_Signup, "Saved!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            .addOnFailureListener(object :OnFailureListener{
                override fun onFailure(e: Exception) {
                    Toast.makeText(this@Activity_Signup, e.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
    }


}