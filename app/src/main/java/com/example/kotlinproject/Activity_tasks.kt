package com.example.kotlinproject

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.Exception
import kotlin.properties.Delegates

class Activity_tasks : AppCompatActivity() {

    lateinit var btn_when: Button
    lateinit var add_task_btn : Button

    lateinit var addTitle : EditText
    lateinit var addNotes : EditText

    lateinit var visible_date: TextView

    lateinit var calendar: Calendar
    lateinit var firestore: FirebaseFirestore

    var Datee : Long = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        btn_when = findViewById(R.id.button_when)
        calendar = Calendar.getInstance()
        visible_date = findViewById(R.id.visible_date)
        add_task_btn=findViewById(R.id.btn_add_task)
        addTitle=findViewById(R.id.title_text)
        addNotes=findViewById(R.id.notes_text)



        firestore=FirebaseFirestore.getInstance()


        add_task_btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                registerFirebase()
            }

        })

        visible_date.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                showDatePickerDialog()
            }
        })

        val initialDueDate = Calendar.getInstance()
        initialDueDate.add(Calendar.DAY_OF_MONTH, 7) // Default due date in 7 days
        updateDueDate(initialDueDate.time)

        add_task_btn.isEnabled = false

        // Add TextWatcher to monitor EditText fields

        addTitle.addTextChangedListener(watcher)
        addNotes.addTextChangedListener(watcher)
        visible_date.addTextChangedListener(watcher)




        btn_when.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                showDatePickerDialog()
            }
        })


    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }




    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val input1 = addTitle.text.toString()
            val input2 = addNotes.text.toString()
            val input3 = visible_date.text.toString()

            // Enable the button only if both fields are not empty
            add_task_btn.isEnabled = !input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Not needed for this example
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Not needed for this example
        }
    }


    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                updateDueDate(selectedDate.time)
            },
            year,
            month,
            day
        )

        val currentDate = Calendar.getInstance()
        datePickerDialog.datePicker.minDate = currentDate.timeInMillis // Set minimum date as today
        datePickerDialog.show()
        visible_date.visibility = View.VISIBLE
    }

    private fun updateDueDate(dueDate: Date) {
        val currentDate = Calendar.getInstance().time
        val diff = dueDate.time - currentDate.time
        val daysRemaining = diff / (1000 * 60 * 60 * 24)

        val sdf = SimpleDateFormat("EEEE, MMM dd, yyyy")
        val formattedDueDate = sdf.format(dueDate)

        Datee = daysRemaining
        visible_date.text = "Due Date: $formattedDueDate"
    }


    private fun registerFirebase() {
        val collectionReference = firestore.collection("Tasks") // Change to the correct collection name

        val title = addTitle.text.toString()
        val notes = addNotes.text.toString()

        val task = hashMapOf(
            "title" to title,
            "notes" to notes,
            "Date" to Datee
        )

        collectionReference
            .add(task)
            .addOnSuccessListener(object : OnSuccessListener<DocumentReference> {
                override fun onSuccess(documentReference: DocumentReference) {
                    showToast(this@Activity_tasks, "Task added to Firestore")
                }
            })
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(e: Exception) {
                    showToast(this@Activity_tasks, "Error: ${e.message}")
                }
            })
    }

}



