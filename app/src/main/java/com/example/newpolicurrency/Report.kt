package com.example.newpolicurrency

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

/**
 * A simple [Fragment] subclass.
 * Use the [Report.newInstance] factory method to
 * create an instance of this fragment.
 */
class Report : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    lateinit var firstedit: EditText
    lateinit var secondedit: EditText
    lateinit var thirdedit: EditText
    lateinit var fourthedit: EditText
    lateinit var fifthedit: EditText
    lateinit var sixthedit: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val Rootview = inflater.inflate(R.layout.fragment_report, container, false)
        // Inflate the layout for this fragment
        //val button1= Rootview.findViewById<Button>(R.id.button)
        // Rootview.findViewById<Button>(R.id.button).setOnClickListener(this)

      //  Rootview.findViewById<Button>(R.id.button).setOnClickListener(this)

        // Rootview.findViewById<Button>(R.id.button).setOnClickListener(this)
        return Rootview
        //   Rootview.findViewById<Button>(R.id.button).setOnClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       firstedit= view.findViewById<EditText>(R.id.editTextTextPersonName)
        secondedit= view.findViewById<EditText>(R.id.editTextTextPersonName2)
        thirdedit= view.findViewById<EditText>(R.id.editTextTextPersonName3)
        fourthedit= view.findViewById<EditText>(R.id.editTextTextPersonName4)
        fifthedit= view.findViewById<EditText>(R.id.editTextTextPersonName5)
       sixthedit=  view.findViewById<EditText>(R.id.editTextTextPersonName6)


        view.findViewById<Button>(R.id.button).setOnClickListener(this)
        //super.onViewCreated(view, savedInstanceState)
    }

        override fun onClick(p0: View?) {

            val intent = Intent(this.activity, ReportActivity::class.java)
            Log.d("check input", thirdedit?.text.toString())
            intent.putExtra("first input", firstedit?.text.toString())
            intent.putExtra("second input", secondedit?.text.toString())
            intent.putExtra("third input", thirdedit?.text.toString())
            intent.putExtra("fourth input", fourthedit?.text.toString())
            intent.putExtra("fifth input", fifthedit?.text.toString())
            intent.putExtra("sixth input", sixthedit?.text.toString())
            activity?.startActivity(intent)

        }

}


