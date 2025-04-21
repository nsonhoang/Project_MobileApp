package com.example.projecttest.data


import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Home (
    val name: String = "",
    val detail: String="",
    var thuThach : List<thuThach> = emptyList()// các thử thách 7x4
)
@Parcelize
data class  thuThach(
    val name: String = "",
//    val tienDo: Int = 0,
    val img : String = "",
    var mucDo : List<mucDo> = emptyList()// các mức độ tập luyện
):  Parcelable

@Parcelize
data class mucDo(
    val name: String = "",
    val timeTraining: Int = 0,// 15 phút
    val detail: String = "",// 20 phút + 7 bài tập
    val img : String="",
//    val tienDo: Int=0,

): Parcelable

data class HomeData(
    val img: String,
    val name: String,
    val detail: String
)

