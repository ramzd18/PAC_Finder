package com.example.newpolicurrency

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine



class ReportActivity : AppCompatActivity() {
    private var all_valid_candidates= ArrayList<String>()
    private var chart_arr = ArrayList<Comm_data>()
    private var only_candidates= ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report2)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true);
        val colordrawable= ColorDrawable(Color.parseColor("#ADD8E6"))
        actionbar?.setBackgroundDrawable(colordrawable)
        var first= intent.extras?.getString("first input")
        var second= intent.extras?.getString("second input")
        var third= intent.extras?.getString("third input")
        var four= intent.extras?.getString("fourth input")
        var fifth= intent.extras?.getString("fifth input")
        var sixth= intent.extras?.getString("sixth input")
        val firstIn:ArrayList<String> = takeString(first!!, second!!, third!!, four!!, fifth!!, sixth!!)
        val all_string= ArrayList<String>()
        all_string.add(first)
        all_string.add(second)
        all_string.add(third)
        all_string.add(four)
        all_string.add(fifth)
        all_string.add(sixth)
        for(i in 0..all_string.size-1){
            if(all_string.get(i).contains("Politician Name:")){
                all_string.get(i).replace("Politician Name:","")
            }
        }
        Log.d("allstring",all_string.toString())
        val gridview=findViewById<GridView>(R.id.gridView)
        var first12=Polidata(first,R.drawable.politician)
        var second12=Polidata(second,R.drawable.politician)
        var third12=Polidata(third,R.drawable.politician)
        var fourth12=Polidata(four,R.drawable.politician)
        var fifth12=Polidata(fifth,R.drawable.politician)
        var sixth12= Polidata(sixth,R.drawable.politician)


        val list= arrayOf(first12,second12,third12,fourth12,fifth12,sixth12)
        val adapter= ComAdapter(this,list)
       // gridview.adapter=adapter
        lifecycleScope.launch {

            apiGenerateValidNames(firstIn)
            Log.d("completed initial","complete")
            generateApiData(all_valid_candidates)
            Log.d("candidatesList",all_valid_candidates.toString())
            var str1= "size"+ chart_arr.size
            Log.d("before reduce",str1)
            reduceListSize()
            chart_arr.sortDescending()
            val chart_arrString= getChartTotalsString(chart_arr)
            Log.d("ChartString",chart_arrString.toString())
            var first1= ArrayList<Comm_data>()
            var first2= ArrayList<Comm_data>()
            var first3= ArrayList<Comm_data>()
            var first4= ArrayList<Comm_data>()
            var first5= ArrayList<Comm_data>()
            var first6= ArrayList<Comm_data>()
            for (i in 0.. firstIn.size-1) {
                if (i == 0) {
                     first1 = seperateList(chart_arr, firstIn, 0)
                }
                if (i == 1) {
                     first2 = seperateList(chart_arr, firstIn, 1)
                }
                if (i == 2) {
                     first3 = seperateList(chart_arr, firstIn, 2)
                }
                if (i == 3) {
                    first4 = seperateList(chart_arr, firstIn, 3)
                }
                if (i == 4) {
                     first5 = seperateList(chart_arr, firstIn, 4)
                }
                if (i == 5) {
                     first6 = seperateList(chart_arr, firstIn, 5)
                     }
            }
            chart_arr.sortDescending()

            val chart_data= getChartData(first1,first2,first3,first4,first5,first6,chart_arr)
          val   chartdataStr= ArrayList<String>()
            for( i in 0..chart_data.size-1){
                chartdataStr.add(chart_data.get(i).candName)
            }
            Log.d("chartdataStr",chartdataStr.toString())
            first= setupText(first!!,only_candidates)
            first12.setCourseName(first!!)
            second =setupText(second!!,only_candidates)
            second12.setCourseName(second!!)
            third=setupText(third!!,only_candidates)
            third12.setCourseName(third!!)
            four=setupText(four!!,only_candidates)
            fourth12.setCourseName(four!!)
            fifth=setupText(fifth!!,only_candidates)
            fifth12.setCourseName(fifth!!)
            sixth=setupText(sixth!!,only_candidates)
            sixth12.setCourseName(sixth!!)

            val stringArr= getChartTotalsString(chart_data)
            Log.d("stringarr",stringArr.toString())
            val intArr= getChartTotalsInt(chart_data)
            if(stringArr.size==0){
                stringArr.add("No Valid Entries")
            }
            if (intArr.size==0){
                intArr.add(1.0)
            }
            var pie = AnyChart.pie();
            gridview.adapter=adapter
            val data = ArrayList<DataEntry>()
            for (i in 0..stringArr.size-1){
                data.add(ValueDataEntry(stringArr.get(i),intArr.get(i)))
            }
            pie.data(data)
            val anyChartView= findViewById<AnyChartView>(R.id.any_chart_view)
            anyChartView.setChart(pie)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
    // Takes the inputs and determines which ones the users edited
    fun takeString(first:String,second:String,third:String,fourth:String,fifth:String, sixth:String): ArrayList<String>{
        var first_arr= ArrayList<String>()
        var second_arr= ArrayList<String>()
        first_arr.add(first)
        first_arr.add(second)
        first_arr.add(third)
        first_arr.add(fourth)
        first_arr.add(fifth)
        first_arr.add(sixth)
        for (i in 0..5){
            if(first_arr.get(i)!="Politician Name:"){
                Log.d("BeforeCheckPolitician",first_arr.get(i))
                if(first_arr.get(i).contains("Politician Name:")){
                   val newOne= first_arr.get(i).replace("Politician Name:","")
                    Log.d("checkPolitician",newOne)
                    second_arr.add(newOne)
                }
                else{
                    second_arr.add(first_arr.get(i))
                }
            }
        }
        return second_arr
    }
    // Returns an array with all candidate id's with names that generate valid API repsonses
    //Use textviews to stores data
    suspend fun apiGenerateValidNames(list: ArrayList<String>){
        if (list.size>0){
        val firsturl= "https://api.open.fec.gov/v1/candidates/search/?sort_null_only=false&sort_hide_null=false&sort_nulls_last=false&page=1&per_page=20&api_key=40pD4jtsG5Irhh4rvf4okJISqQvfgAUAsPc9uAYB&q="
        val secondurl= "&sort=name"
        for (i in 0..list.size-1){
            val example= list.get(i)
            var first_example=example.split(" ")
            var api_url=""
            //last thing edited
            if(first_example.size>1) {
                if (first_example.size>2){
                    api_url = firsturl+first_example[0]+"%"+ first_example[2]+secondurl

                }
                else {
                    api_url = firsturl + first_example[0] + "%" + first_example[1] + secondurl
                }
            }
            else{
                api_url=firsturl+"error"+secondurl
            }
           val response= getData(api_url)
            if(response.toString().length>5){
                if(response.getJSONArray("results").length()>0){
                val first_arr = response.getJSONArray("results")
                val area = first_arr.getJSONObject(0)
                val title1 = area.get("candidate_id").toString()
                Log.d("candidate_id", title1.toString())
                all_valid_candidates.add(title1.toString())
                all_valid_candidates.add(list.get(i))
                only_candidates.add(list.get(i))
            }
    }
        }
        }
    }
// Gets PAC information for an ArrayList that should contain candidate id's. Returns an array with all information.
    suspend fun generateApiData(list: ArrayList<String>){
        if(list.size>0){
            for(i in 0.. list.size-1) {
                if (i % 2 == 0) {
                    val first_part =
                        "https://api.open.fec.gov/v1/schedules/schedule_e/by_candidate/?support_oppose=S&election_full=true&candidate_id="
                    val second_part =
                        "&per_page=80&sort=-total&page=1&api_key=DEMO_KEY&sort_hide_null=false&sort_nulls_last=false&sort_null_only=false"
                    val api_url = first_part + list.get(i) + second_part

                    val response= getData(api_url)
                    if(response.toString().length>5){
                        if(response.getJSONArray("results").length()>0){
                       Log.d("reposneString",response.toString())

                        var count = response.getJSONArray("results").length()-1
                        var str="Count"+count
                        Log.d("count11",str)
                        val first_arr= response.getJSONArray("results")
                        if(count>=0){
                        for(i in 0..count){
                            val individualized= first_arr.getJSONObject(i)
                            Log.d("second11",individualized.toString())
                            val total= individualized.get("total") as Double
                            var str= "count"+total
                        //    Log.d("totalCountVal",str)

                            val com_name:String= individualized.get("committee_name") as String
                            val cand_name: String= individualized.get("candidate_name") as String
                           Log.d("committee_name",com_name)
                            Log.d("cand_name",cand_name)

                            val comTake:Comm_data=Comm_data(com_name,total,cand_name)

                            if(chart_arr.size>0 && chart_arr.get(chart_arr.size-1).checkEquality1(comTake)) {
                                chart_arr.get(chart_arr.size - 1).changeState(comTake)

                            }
                            else{
                                chart_arr.add(comTake)
                            }
                        }
                        }
                }
            }
                }

        }
        }
    }
    // Seperates big array with all PAC information into sub arrays with pac information for only specific candidate that was inputted
    fun seperateList(list: ArrayList<Comm_data>,strlist:ArrayList<String>, num:Int ): ArrayList<Comm_data>{
        var list_in= ArrayList<Comm_data>()
        if(strlist.size>0&& list.size>0){
            for (i in 0.. list.size-1) {
                Log.d("inputname",strlist.get(num).replace("\\s".toRegex(),""))
                if(list.get(i).changeName().equals((strlist.get(num).replace("\\s".toRegex(),"").lowercase()))){
                    Log.d("exampleTest","exampletest")
                    list_in.add(list.get(i))
                    //list.remove(list.get(i))
                }
            }
        }
        return list_in
    }
    // Take inputs from each into pie chart and then display the hgihest.
    fun getChartData(in1: ArrayList<Comm_data>,in2: ArrayList<Comm_data>,in3: ArrayList<Comm_data>, in4: ArrayList<Comm_data>, in5: ArrayList<Comm_data>, in6: ArrayList<Comm_data>, total_arr:ArrayList<Comm_data>)
    : ArrayList<Comm_data>{
    var chart= ArrayList<Comm_data>()
    in1.sortDescending()
     in2.sortDescending()
    in3.sortDescending()
    in4.sortDescending()
    in5.sortDescending()
    in6.sortDescending()
        if (in1.size>0){
            chart.add(in1.get(0))
            if (in1.size>1){
            chart.add(in1.get(1))
        }
        }
        if (in2.size>0){
            chart.add(in2.get(0))
            if (in2.size>1) {
                chart.add(in2.get(1))
            }
        }
        if (in3.size>0){
            chart.add(in3.get(0))
            if (in3.size>1){
                chart.add(in3.get(1))
        }
        }
        if (in4.size>0){
            chart.add(in4.get(0))
            if (in4.size>1){
                chart.add(in4.get(1))
        }
        }
        if (in5.size>0){
            chart.add(in5.get(0))
            if (in5.size>1){
                chart.add(in5.get(1))
        }
        }
        if (in6.size>0){
            chart.add(in1.get(0))
            if (in6.size>1){
                chart.add(in1.get(1))
        }
        }
        var total_index=0
        var j=0
        if(total_arr.size>0){
            // Look over this while loop it looks buggy
        while(chart.size<14 && j<total_arr.size-1){
            var count= 0 ;
            for(i in 0..chart.size-1){
                if (total_arr.get(j).checkEquality1(chart.get(i))){
                    break;
                }
                count++
            }
            if(count==chart.size){
                chart.add(total_arr.get(j))
            }
            j++
        }
        }
return chart
    }
    // Finds duplicat PACs' and removes duplicate entry after updiating ecisting entry to include both condidates name
    //and cumulitative total.
    fun reduceListSize() {

        chart_arr.sortDescending()
        val list1= ArrayList<String>()
        for(i in 0..chart_arr.size-1){
            list1.add(chart_arr.get(i).candName)
        }
        Log.d("chartlistNew1",list1.toString())
        if(chart_arr.size>1) {
            val tot= chart_arr.size-1
            for (i in 0.. tot) {
                for (j in i+1..tot-1) {
                    if ((chart_arr.get(i).checkEquality1(chart_arr.get(j)))) {
                        if (chart_arr.get(i).candName.contains(chart_arr.get(j).candName)) {
                            Log.d("comNameoldtotal", chart_arr.get(i).comName+"-"+chart_arr.get(i).total)
                            chart_arr.get(i).changeState(chart_arr.get(j))
                            chart_arr.get(j).candName = "empty"
                            chart_arr.get(j).comName = "empty"
                            chart_arr.get(j).total = 0.0
                            Log.d("comNameaftertotal",chart_arr.get(i).comName+"-"+chart_arr.get(i).total)
                        }
                        else{
                            Log.d("comNameoldtotal1", chart_arr.get(i).comName+"-"+chart_arr.get(i).total)
                            chart_arr.get(i).changeState(chart_arr.get(j))
                            chart_arr.get(i).changecand_name(chart_arr.get(j))
                            Log.d("comNameaftertotal1",chart_arr.get(i).comName+"-"+chart_arr.get(i).total)
                            chart_arr.get(j).candName = "empty"
                            chart_arr.get(j).comName = "empty"
                            chart_arr.get(j).total = 0.0
                        }
                    }
                }
            }
        }
       val list=ArrayList<String>()
        for(i in 0..chart_arr.size-1){
            list.add(chart_arr.get(i).candName)
        }
        Log.d("chartlistNew",list.toString())
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
                cont.resume(JSONObject())
            }
        )

        queue.add(inputReq)
    }
    // Get overall total of complete Array to use for percentages in the pie chart

    fun getTotal(list:ArrayList<Comm_data>): Double{
        var tot: Double= 0.0

        if(list.size>0){
            for(i in 0..list.size-1){
                tot+=list.get(i).total
            }
    }
        return tot
}
fun getChartTotalsString(list: ArrayList<Comm_data>):ArrayList<String>{
    val tot_list= ArrayList<String>()
    val checkList=ArrayList<String>()
    if(list.size>0){
        var completeText=""
    for(i in 0..list.size-1){
        completeText=""
        var split= list.get(i).candName.split("+")
        if(split.size>1){
            for (i in 0.. split.size-1){
            var removedText= split.get(i).replace(",","")
            removedText= removedText.replace(".","")
            val text_list= removedText.split(" ")
            if(text_list.size>2){
                if(!checkList.contains(text_list[0])) {
                    completeText += text_list[0] + "+"
                }
            }
                else{
                if(!checkList.contains(text_list[0])) {
                    completeText += text_list.get(0) + "+"
                }
            }
            }
            if(completeText.length>1){
                completeText= completeText.substring(0..completeText.length-2)
            }
            tot_list.add(completeText+"-"+list.get(i).comName)
    }
        else {
            tot_list.add(list.get(i).changeName1() + "-" + list.get(i).comName)
        }
    }
}
    return tot_list
}
    // Get Int total for Chart Data List
    fun getChartTotalsInt(list:ArrayList<Comm_data>): ArrayList<Double>{
       val tot_list=ArrayList<Double>()
        if(list.size>0){
            for(i in 0..list.size-1){
                tot_list.add(list.get(i).total)
            }
            var total:Double=0.0
            for (i in 0..tot_list.size-1){
                total+=tot_list.get(i);
            }
            Log.d("totaltotal","size"+getTotal(list))
            Log.d("undertotal","size"+total)
          //  val restOf:Double = getTotal(chart_arr)-total
           // tot_list.add(restOf)
    }
        return tot_list
}
    fun setupText( check:String,list:ArrayList<String> ): String{
        for (str in list){
            var check1=""
            if(check.contains("Politician Name:")) {
                 check1 = check.replace("Politician Name:", "")
            }
            else {
                check1=check
            }
            if(check1.equals(str)){
                    return check1+"-Found"
                }
        }
        if(check.length>"Politician Name:".length){
            return check.replace("Politician Name:", "")+"-Not Found"
        }
      return check+"-Not Found"
    }
}