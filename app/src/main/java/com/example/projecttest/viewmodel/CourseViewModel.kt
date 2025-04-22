
package com.example.projecttest.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projecttest.data.Course
import com.example.projecttest.data.CourseModule
import com.example.projecttest.data.TargetCourse
import com.example.projecttest.data.TrainingProgram
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class CourseViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _trainingProgram = MutableStateFlow<List<TrainingProgram>>(emptyList())
    val trainingProgram: StateFlow<List<TrainingProgram>> get() = _trainingProgram


    val trainingProgramId = "KpN1HjWE31d0ALiQJGKF"// id ở mục đầu tiên
    val idListForYouAndStretching = "3RU8qGg9jVVjGfJFxYE8"
    val idListHome = "O4nc8JAu0mXxdDuXHMyz"


    private val _trainingProgramListHome = MutableStateFlow<List<Course>>(emptyList())
    val trainingProgramListHome : StateFlow<List<Course>> get() = _trainingProgramListHome

    fun fetchDataListHome(){
        val idlist = "4hgm6pmMFVylsDEfMaKs"
        viewModelScope.launch {
            try {
                _trainingProgramListHome.value = fetchCourses(idListHome,idlist)
            }catch (e: Exception) {
                Log.e(TAG, "Error fetching data", e)
                // Xử lý lỗi (ví dụ: hiển thị thông báo cho người dùng)
                throw e
            }
        }
    }


    fun fetchDataTrainingProgram() {// lấy dữ liệu ở mục 6 múi ...
        viewModelScope.launch {
            try {
                _trainingProgram.value = fetchTrainingPrograms(trainingProgramId)// lấu dữ liệu ở mục đầu tiền như là bài tập 6 múi ....
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data", e)
                // Xử lý lỗi (ví dụ: hiển thị thông báo cho người dùng)
            }
        }
    }



    private val _trainingProgramListForYou = MutableStateFlow<List<Course>>(emptyList())
    val trainingProgramListForYou: StateFlow<List<Course>> get() = _trainingProgramListForYou


    private val _trainingProgramListStretching = MutableStateFlow<List<Course>>(emptyList())
    val trainingProgramListStretching: StateFlow<List<Course>> get() = _trainingProgramListStretching


    fun fetchDataListForYouAndStretching(){
        val idListForyou= "1nB2jydlQmfPzDfWZDvs"//id ở mục dành cho bạn
        val idListStretching = "J6ZEiiSwCeBDMJ5IjqIp"//id ở mục dãn cơ
        viewModelScope.launch {
            launch {
                try {
                    _trainingProgramListStretching.value=fetchCourses(idListForYouAndStretching,idListStretching)

                }catch (e: Exception){
                    Log.e(TAG, "Error fetching data listStreching", e)
                    throw e
                }
            }
            launch {
                try {
                    _trainingProgramListForYou.value=fetchCourses(idListForYouAndStretching,idListForyou)

                }catch (e: Exception){
                    Log.e(TAG, "Error fetching data List for you", e)
                    throw e
                }
            }
        }
    }


    private suspend fun fetchTrainingPrograms(documentId : String): List<TrainingProgram> {
        val listProgram = mutableListOf<TrainingProgram>()
        val querySnapshot = db.collection("TrainingProgram")
            .document(documentId)
            .get().await()

        val trainingProgram = querySnapshot.toObject(TrainingProgram::class.java) ?: return  emptyList()

        val targetCourses = fetchTargetCourses(querySnapshot.id)
        trainingProgram.targetCourses = targetCourses
        listProgram.add(trainingProgram)

        return listProgram
    }

    private suspend fun fetchTargetCourses(trainingProgramId: String): List<TargetCourse> {
        val listTarget = mutableListOf<TargetCourse>()
        val targetCoursesSnapshot = db.collection("TrainingProgram")
            .document(trainingProgramId)
            .collection("targetCourses")
            .get().await()

        for (targetCourseDoc in targetCoursesSnapshot.documents) {
            val targetCourse = targetCourseDoc.toObject(TargetCourse::class.java) ?: continue

            val courses = fetchCourses(trainingProgramId, targetCourseDoc.id)
            targetCourse.courses = courses
            listTarget.add(targetCourse)
        }
        return listTarget
    }

    private suspend fun fetchCourses(trainingProgramId: String, targetCourseId: String): List<Course> {
        val listCourse = mutableListOf<Course>()
        val courseSnapshot = db.collection("TrainingProgram")
            .document(trainingProgramId)
            .collection("targetCourses")
            .document(targetCourseId)
            .collection("courses")
            .get().await()

        for (courseDoc in courseSnapshot.documents) {
            val course = courseDoc.toObject(Course::class.java) ?: continue

            val module = fetchModules(trainingProgramId,targetCourseId,courseDoc.id)
            course.modules= module
            listCourse.add(course)
        }
        return listCourse
    }
    private suspend fun fetchModules(trainingProgramId: String, targetCourseId: String,courseId: String): List<CourseModule>{
        val listModule = mutableListOf<CourseModule>()

        val moduleSnapshot =  db.collection("TrainingProgram")
            .document(trainingProgramId)
            .collection("targetCourses")
            .document(targetCourseId)
            .collection("courses")
            .document(courseId)
            .collection("modules")
            .get().await()


        for(moduleDoc in moduleSnapshot.documents){
            val module = moduleDoc.toObject(CourseModule::class.java) ?: continue
            listModule.add(module)
        }
        return listModule
    }
}
