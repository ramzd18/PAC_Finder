package com.example.newpolicurrency

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ComAdapter(context: Context, PoliList: Array<Polidata>) :
ArrayAdapter<Polidata>(context, 0, PoliList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.com_item, parent, false)
        }

        val courseModel: Polidata? = getItem(position)
        val courseTV = listitemView!!.findViewById<TextView>(R.id.textView)
        val courseIV = listitemView.findViewById<ImageView>(R.id.idIVcourse)

        courseTV.setText(courseModel?.getCourseName())
        courseIV.setImageResource(courseModel!!.getTHEImgid())
        return listitemView
    }
}