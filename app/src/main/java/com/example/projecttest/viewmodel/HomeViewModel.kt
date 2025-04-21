package com.example.projecttest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projecttest.data.Home
import com.example.projecttest.data.mucDo
import com.example.projecttest.data.thuThach
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val TAG = "HomeViewModel"

    private val _homeList = MutableStateFlow<List<Home>>(emptyList())
    val homeList: StateFlow<List<Home>> get() = _homeList

    fun fetchHomeList() {
        viewModelScope.launch {
            try {
                val list = fetchHomes()
                _homeList.value = list
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching Home list", e)
            }
        }
    }

    private suspend fun fetchHomes(): List<Home> {
        val homes = mutableListOf<Home>()
        val snapshot = db.collection("Home").get().await()

        for (doc in snapshot.documents) {
            val home = doc.toObject(Home::class.java) ?: continue
            Log.d(TAG, "Fetched Home: ${home.name}")  // Log thông tin home

            val thuThachList = fetchThuThach(doc.id)
            home.thuThach = thuThachList
            homes.add(home)
        }
        return homes
    }

    private suspend fun fetchThuThach(homeId: String): List<thuThach> {
        val challenges = mutableListOf<thuThach>()
        val snapshot = db.collection("Home")
            .document(homeId)
            .collection("thuThach")
            .get()
            .await()

        for (doc in snapshot.documents) {
            val challenge = doc.toObject(thuThach::class.java) ?: continue
            Log.d(TAG, "Fetched Challenge: ${challenge.name}")  // Log thông tin challenge

            val mucDoList = fetchMucDo(homeId, doc.id)
            challenge.mucDo = mucDoList
            challenges.add(challenge)
        }
        return challenges
    }

    private suspend fun fetchMucDo(homeId: String, thuThachId: String): List<mucDo> {
        val mucDos = mutableListOf<mucDo>()
        val snapshot = db.collection("Home")
            .document(homeId)
            .collection("thuThach")
            .document(thuThachId)
            .collection("mucDo")
            .get()
            .await()

        for (doc in snapshot.documents) {
            val mucdo = doc.toObject(mucDo::class.java) ?: continue
            Log.d(TAG, "Fetched MucDo: ${mucdo.name}")  // Log thông tin mucDo
            mucDos.add(mucdo)
        }
        return mucDos
    }
}