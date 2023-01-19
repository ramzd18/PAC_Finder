package com.example.newpolicurrency

class Polidata(var course_name: String, var imgid: Int) {

    fun getCourseName(): String {
        return course_name
    }

    fun setCourseName(course_name: String) {
        this.course_name = course_name
    }

    fun getTHEImgid(): Int {
        return imgid
    }

    fun setTHEImgid(imgid: Int) {
        this.imgid = imgid
    }
}
