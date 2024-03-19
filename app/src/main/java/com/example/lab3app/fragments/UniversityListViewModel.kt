package com.example.lab3app.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.lab3app.data.University
import com.example.lab3app.data.UniversityList
import com.example.lab3app.repository.UniversityRepository

class UniversityListViewModel: ViewModel() {
    var universityList: MutableLiveData<UniversityList> = MutableLiveData()

    private var _university: University? = null

    val university
        get() = _university

    private val universityListObserver = Observer<UniversityList?>{
        list ->
        universityList.postValue(list!!)
    }

    init{
        UniversityRepository.getInstance().universityList.observeForever(universityListObserver)
        UniversityRepository.getInstance().university.observeForever{
            _university = it
        }
    }

    fun deleteUniversity(){
        if(university != null){
            UniversityRepository.getInstance().deleteUniversity(university!!)
        }
    }

    fun appendUniversity(name: String, city: String){
        val university = University()
        university.name = name
        university.city = city
        UniversityRepository.getInstance().newUniversity(university)
    }

    fun updateUniversity(name: String, city: String){
        if (_university != null){
            _university!!.name = name
            _university!!.city = city
            UniversityRepository.getInstance().updateUniversity(_university!!)
        }
    }

    fun setCurrentUniversity(university: University){
        UniversityRepository.getInstance().setCurrentUniversity(university)
    }
}