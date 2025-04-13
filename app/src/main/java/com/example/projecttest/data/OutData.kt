package com.example.projecttest.data

import java.io.Serializable

data class TrainingProgram(
    val name: String ="",//mục đầu tiên đấy
    val detail: String="", //kh có
    var targetCourses: List<TargetCourse> = emptyList()// 1 list gồm 6 múi,Ngực, Cánh tay & vai , Mong & chân
)

data class TargetCourse(
    val img: String= "",
    val name: String = "",// 6  múi
    val detail: String = "",//"Khi kết hợp với HITT bạn có thể đánh tan mỡ thừa nhanh chóng"
    var courses: List<Course> = emptyList()//
): Serializable

data class Course(
    //
    val name: String ="git",//Đốt cháy mỡ bụng với HITT người mới bắt đầu
    val detail: String= "",//14 phút + Người bắt đầu"
    val level: String="",// người bắt đầu
    val totalTime: Int=0,// 14 phút
    var modules: List<CourseModule> = emptyList()
)

data class CourseModule(
    val name: String = "",// đá mông
    val trainingTime: Int = 0,// 30s
)

data class OutData(
    val img : Int,
    val name: String,
    val  detail: String
    )