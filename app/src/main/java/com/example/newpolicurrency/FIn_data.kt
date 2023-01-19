package com.example.newpolicurrency

class FIn_data(var description:String,var image_id:Int, var data:String) {
    fun setUpImageId(imgid:Int){
        this.image_id=imgid
    }
    fun getTheDescription() : String{
        return description
    }
    fun getImage():Int{
        return image_id
    }
    fun gettheData():String{
        return data
    }
    fun setTheDescription(str:String){
        this.description=str
    }
}