package com.example.newpolicurrency

import android.util.Log


class Comm_data(var comName:String, var total:Double, var candName: String): Comparable<Comm_data>{



    fun checkEquality1(input:Comm_data):Boolean{
       val firstcheck= comName.replace("\\s".toRegex(), "").lowercase()
        val secondcheck= input.comName.replace("\\s".toRegex(), "").lowercase()

        if(firstcheck.length != secondcheck.length){
            return false
        }
        for (i in 0..firstcheck.length-1){
            if(firstcheck.get(i)!=secondcheck.get(i)){
                return false
            }
        }
        return true
    }

         fun changeName(): String {
             var removedText= candName.replace(",","")
             removedText= removedText.replace(".","")
             Log.d("removedText",removedText)
             val text_list= removedText.split(" ")
             if(text_list.size>1){
                 removedText= text_list[text_list.size-1]+" "+text_list[0]
                 }
             removedText=removedText.replace("\\s".toRegex(),"")
             Log.d("removedText2",removedText)

             return removedText.lowercase()
         }
    fun changeName1():String{
        var removedText= candName.replace(",","")
        removedText= removedText.replace(".","")
        Log.d("removedText",removedText)
        val text_list= removedText.split(" ")
        if(text_list.size>1){
            removedText= text_list[1]+" "+text_list[0]
        }
        return removedText
    }
    fun changeState(input:Comm_data){
        total += input.total
    }
      // Make function take an arrayList and final all duplicates in lis


         // Seperate Arraylist into string and ints for pie chart
    fun seperateListChart(listL:ArrayList<Comm_data>)   {
        //TODO
    }
         fun changecand_name(input: Comm_data){
             candName= candName+"+"+input.candName
         }

         override fun compareTo(other: Comm_data): Int {
            return total.compareTo(other.total)
         }
     }
