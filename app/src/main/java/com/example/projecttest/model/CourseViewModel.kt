package com.example.projecttest.model

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
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class CourseViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _trainingProgram = MutableStateFlow<List<TrainingProgram>>(emptyList())
    val trainingProgram: StateFlow<List<TrainingProgram>> get() = _trainingProgram

    fun fetchData() {
        viewModelScope.launch {
            try {
                _trainingProgram.value = fetchTrainingPrograms()
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data", e)
                // Xử lý lỗi (ví dụ: hiển thị thông báo cho người dùng)
            }
        }
    }

    private suspend fun fetchTrainingPrograms(): List<TrainingProgram> {
        val listProgram = mutableListOf<TrainingProgram>()
        val querySnapshot = db.collection("TrainingProgram").get().await()

        for (document in querySnapshot.documents) {
            val trainingProgram = document.toObject(TrainingProgram::class.java) ?: continue

            val targetCourses = fetchTargetCourses(document.id)
            trainingProgram.targetCourses = targetCourses
            listProgram.add(trainingProgram)
        }
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