package com.example.newpolicurrency
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope

class  FinDataAdapter(context: Context, Fin_dataArrayList: ArrayList<FIn_data?>?) :
    ArrayAdapter<FIn_data?>(context, 0, Fin_dataArrayList!!) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)
        }

        val finData: FIn_data? = getItem(position)
        val courseTV = listitemView!!.findViewById<TextView>(R.id.idTVCourse)
        val courseIV = listitemView.findViewById<ImageView>(R.id.idIVcourse)

        courseTV.setText(finData?.getTheDescription())
        if (finData != null) {
            courseIV.setImageResource(finData.getImage())
        }
        return listitemView
    }

}
