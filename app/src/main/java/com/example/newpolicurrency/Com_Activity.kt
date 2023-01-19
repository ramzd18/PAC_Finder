package com.example.newpolicurrency

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Com_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_com)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true);
        val colordrawable= ColorDrawable(Color.parseColor("#ADD8E6"))
        actionbar?.setBackgroundDrawable(colordrawable)
        val first = intent.extras!!.getString("Str value")
        Log.d("firstValue",first.toString())
        val txtvW= findViewById<TextView>(R.id.textView17)
        txtvW.text = first.toString()
        val gridview = findViewById<GridView>(R.id.gridView)
        val finDataList: ArrayList<FIn_data?> = ArrayList<FIn_data?>()
        val first1 = FIn_data("Address" + "-" , R.drawable.adress, "wait")
        val second = FIn_data("Website" + "-" , R.drawable.website, "1000")
        val third = FIn_data(
            "Individual contribution percent" + "-", R.drawable.money, "1000")
        val fourth = FIn_data("Cash on Hand" + "-", R.drawable.money, "1000")
        val fifth = FIn_data("Disbursements" + "-", R.drawable.money, "1000")
        val sixth =
            FIn_data("Operating Expenditures" + "-" , R.drawable.money, "1000")
        val seventh = FIn_data("Contributions" + "-" , R.drawable.money, "1000")
        val eigth = FIn_data("Debts" + "-" , R.drawable.money, "1000")
        val ninth=FIn_data("Independent Expenditures" + "-" , R.drawable.money, "1000")
        val tenth=FIn_data("Complete Data:" + "-" , R.drawable.money, "1000")
        finDataList.add(first1)
        finDataList.add(second)
        finDataList.add(third)
        finDataList.add(fourth)
        finDataList.add(fifth)
        finDataList.add(sixth)
        finDataList.add(seventh)
        finDataList.add(eigth)
        finDataList.add(ninth)
        finDataList.add(tenth)

        val adapter = FinDataAdapter(this,finDataList)
        gridview.adapter = adapter

        lifecycleScope.launch {
            val comID = getComId(first.toString())
            val list = getDisplayData(comID)
            val list1 = displayDataHistory(comID)
            val str = debtData(comID)
            first1.setTheDescription("Address" + "-"+list1.get(0))
            second.setTheDescription("Website" + "-"+list1.get(1))
            third.setTheDescription("Individual contribution percent" + "-"+list.get(0))
            fourth.setTheDescription("Money Spent" + "-"+list.get(2))
            fifth.setTheDescription("Operating Expenditures" + "-"+list.get(3))
            sixth.setTheDescription("Money Raised" + "-"+list.get(4))
            seventh.setTheDescription("Cash on Hand" + "-"+list.get(1))
            eigth.setTheDescription("Debts" + "-"+str.get(0))
            ninth.setTheDescription("Communication Expenditures YTD" + "-"+str.get(1))
            tenth.setTheDescription("Complete Dataset" + "-"+str.get(2))
            // gridview.adapter=adapter
            gridview.setOnItemClickListener { adapterView, view, i, l ->
                val fin: FIn_data = adapterView.getItemAtPosition(i) as FIn_data

            }
            gridview.adapter = adapter


        }


    }

    suspend fun getData(api_url:String) = suspendCoroutine<JSONObject>{ cont ->
        val queue = Volley.newRequestQueue(this)
        Log.d("gettingapi",api_url)
        val inputReq = JsonObjectRequest(
            Request.Method.GET, api_url, null,
            { response ->
                val response1=  cont.resume(response)
                /*val first_arr = response.getJSONArray("results")
                val area = first_arr.getJSONObject(0)
                 val title1 = area.get("candidate_id").toString()
                Log.d("candidate_id", title1.toString())
                all_valid_candidates.add(title1.toString())
                all_valid_candidates.add(list.get(i))

                 */
            },
            { error ->
                    //val str= "results:[{}]"
                    cont.resume(JSONObject())
                }
        )

        queue.add(inputReq)
    }
    suspend fun getComId(str:String):String{
    val first_url="https://api.open.fec.gov/v1/schedules/schedule_e/?sort_null_only=false&per_page=20&q_spender="
    val second_url="&sort_nulls_last=false&api_key=40pD4jtsG5Irhh4rvf4okJISqQvfgAUAsPc9uAYB&sort=-expenditure_date&sort_hide_null=false"
    var mid=""
    var api_url=""
        val split_list=str.split(" ")
        val split= ArrayList<String>()
        if (split_list.size>0) {
            for (i in 0..split_list.size-1) {
                Log.d("splitvalue",split_list.get(i))
                if (split_list.get(i) == " " || split_list.get(i) == "") {
                } else {
                    split.add(split_list.get(i))
                }
            }
        }
        Log.d("splitsize",split.toString())
            if(split.size>0){
            if (split.size == 1) {
                mid = split.get(0)
            } else {
                for (i in 0..split.size - 2) {
                        mid += split.get(i) + "%20"
                }
                mid+=split_list.get(split.size-1)
            }
                Log.d("checkifthere","checkifthere")
            api_url=first_url+mid+second_url
        }
        else{
            Log.d("erroror","err")
            api_url=first_url+"error"+second_url
        }
        Log.d("api_url",api_url)
        val respo= getData(api_url)
        Log.d("respoString",respo.toString())
        if(respo.toString().length>5){
        if (respo.getJSONArray("results").length()>0){
            Log.d("check","checkcheck")
            val first_arr= respo.getJSONArray("results")
            val jsonobj = first_arr.getJSONObject(0)
            val com= jsonobj.getJSONObject("committee")
            val com_id= com.get("committee_id") as String
            return com_id
        }
        }
        return ""
    }
suspend fun getDisplayData(str:String): ArrayList<String>{
    var dataList= ArrayList<String>()
    val first_url="https://api.open.fec.gov/v1/committee/"
    val last_url="/totals/?page=1&sort_null_only=false&api_key=40pD4jtsG5Irhh4rvf4okJISqQvfgAUAsPc9uAYB&sort=-cycle&per_page=20&sort_hide_null=false&sort_nulls_last=false"
    var middle=""
    if(str.length>0){
        middle=str
    }
    else{
        middle="error"
    }
   val api_url=first_url+middle+last_url
    Log.d("apiurl1",api_url)
    val responsedata= getData(api_url)
    if(responsedata.toString().length>5){
        if (responsedata.getJSONArray("results").length()>0){
        val first_arr= responsedata.getJSONArray("results")
        val respo= first_arr.getJSONObject(0)
            if(respo.toString().contains("individual_contributions_percent")) {
                val individualContributions =
                    respo.get("individual_contributions_percent") as Double
                dataList.add("" + individualContributions)
            }
            else{
                dataList.add("No Data")
            }
            if(respo.toString().contains("last_cash_on_hand_end_period")) {
                val cashonHandPeriod = respo.get("last_cash_on_hand_end_period") as Double
                dataList.add("" + cashonHandPeriod)
            }
            else{
                dataList.add("No Data")
            }
            if(respo.toString().contains("disbursements")) {
                val disbursements = respo.get("disbursements") as Double
                dataList.add("" + disbursements)
            }
            else{
                dataList.add("No Data")
            }
            if(respo.toString().contains("operating_expenditures")) {
                val operatingExpenditure = respo.get("operating_expenditures") as Double
                dataList.add("" + operatingExpenditure)
            }
            else{
                dataList.add("No Data")
            }
            if(respo.toString().contains("\"contributions\":")) {
                val contributions = respo.get("contributions") as Double
                dataList.add("" + contributions)
            }
            else{
                dataList.add("No Data")
            }
    }
        else{
            dataList.add("error")
            dataList.add("error")
            dataList.add("error")
            dataList.add("error")
            dataList.add("error")

        }
    }
    else{
        dataList.add("error")
        dataList.add("error")
        dataList.add("error")
        dataList.add("error")
        dataList.add("error")

    }
return dataList
}
suspend fun displayDataHistory(str:String):ArrayList<String>{
    var datalist= ArrayList<String>()
    val api_url="https://api.open.fec.gov/v1/committee/"+str+"/?api_key=40pD4jtsG5Irhh4rvf4okJISqQvfgAUAsPc9uAYB&per_page=20&sort_hide_null=false&page=1&sort_null_only=false&sort=name&sort_nulls_last=false"
   Log.d("api_url2",api_url)
    val response= getData(api_url)
    if(response.toString().length>5){
    if (response.getJSONArray("results").length()>0) {
        val first_arr = response.getJSONArray("results")
        val respo = first_arr.getJSONObject(0)
        if (respo.toString().contains("\"street_1\":")) {
            val address =
                respo.get("street_1").toString() + "," + respo.get("city").toString() + "," + respo.get(
                    "state_full"
                ).toString()
            val website = respo.get("website").toString()
            datalist.add(address)
            if(website.equals("null")){
                datalist.add("No Website")
            }
            else {
                datalist.add(website)
            }
        }
        else{
            datalist.add("No Data")
            datalist.add("No Data")
        }
    }
    else{
        datalist.add("invalid name")
        datalist.add("invalid name")
    }
    }
    else{
        datalist.add("invalid name")
        datalist.add("invalid name")
    }
    return datalist
}
    suspend fun debtData(str:String):ArrayList<String>{
        val list= ArrayList<String>()
        val first_url="https://api.open.fec.gov/v1/committee/"
        val second_url="/reports/?api_key=40pD4jtsG5Irhh4rvf4okJISqQvfgAUAsPc9uAYB&sort_hide_null=false&per_page=20&page=1&sort_null_only=false&sort=-coverage_end_date&sort_nulls_last=false"
        val api_url=first_url+str+second_url
        val responsedata= getData(api_url)
        if(responsedata.toString().length>5){
        if (responsedata.getJSONArray("results").length()>0) {
            val first_arr = responsedata.getJSONArray("results")
            val respo = first_arr.getJSONObject(0)
            if (respo.toString().contains("debts_owed_by_committee")) {
                val debts = respo.get("debts_owed_by_committee") as Double
                list.add(""+debts)
            } else {
                list.add("No data")
            }
            if (respo.toString().contains("independent_expenditures_ytd")) {
                val indExpend = respo.get("independent_expenditures_ytd") as Double
                list.add(""+indExpend)
            }
            else {
                list.add("No data")
            }
            if (respo.toString().contains("csv_url")) {
                val csv = respo.get("csv_url") as String
                list.add(""+csv)
            }
            else {
                list.add("No data")
            }
        }
            else{
                list.add("No data")
            list.add("No data")
            list.add("No data")
        }
        }
        else{
            list.add("Error")
            list.add("Error")
            list.add("Error")        }
        return list
}
}