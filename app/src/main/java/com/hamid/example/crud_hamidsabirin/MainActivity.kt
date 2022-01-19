package com.hamid.example.crud_hamidsabirin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamid.example.crud_hamidsabirin.room.Constant
import com.hamid.example.crud_hamidsabirin.room.Note
import com.hamid.example.crud_hamidsabirin.room.NoteDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { NoteDB(this) }
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadNote()

    }
    fun loadNote(){
        CoroutineScope(Dispatchers.IO).launch {
            noteAdapter.setData(db.noteDao().getNotes())
            withContext(Dispatchers.Main) {
                noteAdapter.notifyDataSetChanged()
            }
        }
    }
    private fun setupView (){
        supportActionBar!!.apply {
            title = "Catatan"
        }
    }
    fun setupListener(){
        button_create.setOnClickListener {
            intenEdit(Constant.TYPE_CREATE, 0)
        }
    }


    private fun setupRecyclerView(){
        noteAdapter = NoteAdapter(arrayListOf(), object :NoteAdapter.OnAdapterListener{
            override fun onClick(note: Note) {
                //read detail note
                intenEdit(note.id, Constant.TYPE_READ)
            }

            override fun onUpdate(note: Note) {
                intenEdit(note.id,Constant.TYPE_UPDATE)
            }

            override fun onDelete(note: Note) {
                deleteDialog(note)
            }
        })
        list_note.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter
        }
    }
    fun intenEdit(noteId: Int, intenType: Int){
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", noteId)
                .putExtra("intent_type", intenType)
        )

    }

    private fun deleteDialog(note:Note){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin Hapus? ${note.title}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().deleteNote(note)
                    dialogInterface.dismiss()
                    loadNote()
                }
            }
        }

        dialog.show()
    }
}