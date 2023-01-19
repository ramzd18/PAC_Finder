package com.example.newpolicurrency

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject
import java.util.concurrent.Executors

class News : Fragment() {
private var list= ArrayList<String>()
    fun createnews(ig:ImageView){
         val url = "https://api.open.fec.gov/v1/candidates/search/?sort_null_only=false&sort_hide_null=false&sort_nulls_last=false&page=1&per_page=20&api_key=40pD4jtsG5Irhh4rvf4okJISqQvfgAUAsPc9uAYB&q=Raphael%Warnock&sort=name"
        val pic_url= "https://as1.ftcdn.net/v2/jpg/00/86/04/64/1000_F_86046478_rPox7JzkRkB5P5Ts9n6Qc1lcTyR4iOoQ.jpg"
        //val imageView = v.findViewById<ImageView>(R.id.imageView2)
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
       // Log.d("Picture","Pictureset")

        var image: Bitmap? = null
        executor.execute {

            try {
                val `in` = java.net.URL(pic_url).openStream()
                image = BitmapFactory.decodeStream(`in`)

                // Only for making changes in UI
                handler.post {
                    if (ig!= null) {
                        //Log.d("Picture1","Pictureset")
                        ig.setImageBitmap(image)
                     //   Log.d("Picture3","Pictureset")

                    }
                }
            }

            // If the URL doesnot point to
            // image or any other kind of failure
            catch (e: Exception) {
                Log.d("Picture2","Pictureset")

                e.printStackTrace()
            }
        }
    }
    fun apidata(tv:TextView, num: Int) {
        //val queue = Volley.newRequestQueue(vie)
        val url =
            "https://api.open.fec.gov/v1/candidates/search/?sort_null_only=false&sort_hide_null=false&sort_nulls_last=false&page=1&per_page=20&api_key=40pD4jtsG5Irhh4rvf4okJISqQvfgAUAsPc9uAYB&q=Raphael%Warnock&sort=name"

       // val queue = Volley.newRequestQueue(this.context)

        val api_Url =
            "https://newsapi.org/v2/top-headlines?country=us&apiKey=77260203007c4ffdb39fe69e60b2f50e"
      //  val textView = v.findViewById<TextView>(R.id.textView2)

        /**  val intentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
        ) { results -> } */


        AndroidNetworking.initialize(this.context);
        AndroidNetworking.get("https://newsapi.org/v2/top-headlines?apiKey=77260203007c4ffdb39fe69e60b2f50e")
            .addQueryParameter("country", "us")
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{

               override fun onResponse(response: JSONObject) {
                 //  textView.text = response.getString("\"title\"")
                   val text= response.getJSONArray("articles")
                 //  textView.text= text.substring(0,100)
                  //val first= text.getString(0)
                   val first =text.getJSONObject(num)
                   val text1="jeff"
                   tv.text= first.get("title") as CharSequence?


                }

                override fun onError(error: ANError) {
                    tv.text = "That didn't work!"
                }
            });
    }

override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    // Inflate the layout for this fragment
    val Rootview: View=  inflater.inflate(R.layout.fragment_news, container, false)
    val firstimage=Rootview.findViewById<ImageView>(R.id.imageView3)
    val secondimage=Rootview.findViewById<ImageView>(R.id.imageView2)
    val thirdimage=Rootview.findViewById<ImageView>(R.id.imageView)
    val fourthimage= Rootview.findViewById<ImageView>(R.id.imageView5)
    val fifthimage= Rootview.findViewById<ImageView>(R.id.imageView4)

    createnews(firstimage)
    createnews(secondimage)
    createnews(thirdimage)
    createnews(fourthimage)
    createnews(fifthimage)
    val firsttext= Rootview.findViewById<TextView>(R.id.textView2)
    val secondtext= Rootview.findViewById<TextView>(R.id.textView3)
    val thirdtext= Rootview.findViewById<TextView>(R.id.textView6)
    val fourthtext= Rootview.findViewById<TextView>(R.id.textView7)
    val fifthtext= Rootview.findViewById<TextView>(R.id.textView8)
    apidata(firsttext,0)
    apidata(secondtext,1)
    apidata(thirdtext,2)
    apidata(fourthtext,3)
    apidata(fifthtext,4)

   // apidata(Rootview,2,)
    return Rootview
}


}
