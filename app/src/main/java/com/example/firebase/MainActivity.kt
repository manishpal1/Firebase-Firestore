package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveButton.setOnClickListener{
            val firstName = inputFirstName.text.toString()
            val lastName = inputLastName.text.toString()

            saveFireStore(firstName,lastName)
        }
        readFireStoreData()
    }
    fun saveFireStore(firstname: String, lastname: String){
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["firstName"] = firstname
        user["lastName"] = lastname

        db.collection("Users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity, "record added Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this@MainActivity, "record Failed to add", Toast.LENGTH_SHORT).show()
            }
        readFireStoreData()


    }
    // Reterive data
    fun readFireStoreData(){
        val db = FirebaseFirestore.getInstance()
        db.collection("Users")
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    for (document in it.result!!){
                        result.append(document.data.getValue("firstName")).append("")
                            .append(document.data.getValue("lastName")).append("\n\n")
                    }
                    textViewResult.setText(result)

                }

            }

    }}

