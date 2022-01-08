package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.databinding.ActivityAddEditNoteBinding
import com.example.noteapp.databinding.ActivityAddEditNoteBinding.bind
import com.example.noteapp.databinding.ActivityAddEditNoteBinding.inflate
import com.example.noteapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditNoteBinding

    lateinit var viewModel: NoteViewModel
    var noteID = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
            .get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID", -1)

            binding.idBtnAddUpdate.setText("Update Note")
            binding.idEdtNoteTitle.setText(noteTitle)
            binding.idEditNoteDescription.setText(noteDesc)
        } else {
            binding.idBtnAddUpdate.setText("Save Note")
        }

        binding.idBtnAddUpdate.setOnClickListener {

            if (noteType.equals("Edit")) {
                if (binding.idEdtNoteTitle.text.toString().isNotEmpty() && binding.idEditNoteDescription.text.toString().isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM,yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updateNote = Note(binding.idEdtNoteTitle.text.toString(), binding.idEditNoteDescription.text.toString(), currentDate)
                    updateNote.id= noteID
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this, "Note Update..", Toast.LENGTH_SHORT).show()
                }
            }else{
                if (binding.idEdtNoteTitle.toString().isNotEmpty() && binding.idEditNoteDescription.toString().isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String= sdf.format(Date())
                    viewModel.addNote(Note(binding.idEdtNoteTitle.text.toString(), binding.idEditNoteDescription.text.toString(), currentDate))
                    Toast.makeText(this, "Note Added..", Toast.LENGTH_SHORT).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}