package com.example.kotlinproject

import android.os.Bundle
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class Activity_Recycler : AppCompatActivity() {

    lateinit var firestore: FirebaseFirestore
    lateinit var rvUsers: RecyclerView
    val modelUserList: List<ModelUser> = ArrayList<ModelUser>()
    private lateinit var adapter: Adapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        firestore=FirebaseFirestore.getInstance()
        rvUsers=findViewById(R.id.Recycler_view)
        val adapterUsers = AdapterUer(modelUserList)



        rvUsers.layoutManager = LinearLayoutManager(this@Activity_Recycler)


        getUsesr()

    }

    private fun getUsesr() {

        firestore.collection("Tasks").get()
            .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>{

                override fun onComplete(task: Task<QuerySnapshot>) {
                    if( task.isSuccessful){

                        modelUserList.clear(); // Clear the list before adding data
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            Modeltask tasks = document.toObject(Modeltask.class);
                            String name=document.getString("id");
                            Toast.makeText(MainActivity.this, ""+name, Toast.LENGTH_SHORT).show();
                            modeltaskList.add(tasks);
                    }


                }

            })
    }

}