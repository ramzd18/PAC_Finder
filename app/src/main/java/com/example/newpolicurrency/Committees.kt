package com.example.newpolicurrency

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [Committees.newInstance] factory method to
 * create an instance of this fragment.
 */

class Committees : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
private lateinit var editTxt: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val Rootview= inflater.inflate(R.layout.fragment_committees, container, false)
        return Rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         editTxt= view.findViewById<EditText>(R.id.editTextTextPersonName7)
        view.findViewById<Button>(R.id.button2).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val intent= Intent(this.activity,Com_Activity::class.java)
       val str= editTxt.text.toString()
        intent.putExtra("Str value",str)
        activity?.startActivity(intent)
    }
}