package com.example.noteapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.NoteRvItemBinding

class NoteAdapter(val context : Context,
                  val noteClickInterface: NoteClickInterface,
                  val noteClickDeleteInterface: NoteClickDeleteInterface): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val allNotes = ArrayList<Note>()

    inner class ViewHolder(val binding: NoteRvItemBinding) :
        RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NoteRvItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.idTVNoteTitle.setText(allNotes.get(position).noteTitle)
        holder.binding.idTVNoteTimeSlap.setText("Last Upadted : " + allNotes.get(position).timeStamp)

        holder.binding.idTvDelete.setOnClickListener{
            noteClickDeleteInterface.onDeleteIconClickd(allNotes.get(position))
        }

        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes.get(position))
        }

    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

}

interface NoteClickDeleteInterface{
    fun onDeleteIconClickd(note: Note)
}

interface NoteClickInterface{
    fun onNoteClick(note: Note)
}