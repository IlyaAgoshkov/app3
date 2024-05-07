package com.example.lab3app.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab3app.data.Faculty
import com.example.lab3app.data.FacultyList
import com.example.lab3app.repository.UniversityRepository

class FacultyListViewModel : ViewModel() {
    var facultyList: MutableLiveData<FacultyList> = MutableLiveData()

    private var _faculty: Faculty? = null

    val faculty
        get() = _faculty


    val university
        get() = UniversityRepository.getInstance().university.value

    private val facultyListObserver = Observer<List<Faculty>>{
            list ->
        facultyList.postValue(
            FacultyList().apply {
                items=list.filter { it.universityID==university?.id } as MutableList<Faculty>
            }
        )
    }

    init{
        UniversityRepository.getInstance().facultyList.observeForever(facultyListObserver)
        UniversityRepository.getInstance().faculty.observeForever{
            _faculty = it
        }
    }

    fun deleteFaculty(){
        if(faculty != null){
            UniversityRepository.getInstance().deleteFaculty(faculty!!)
        }
    }

    fun appendFaculty(name: String){
        val faculty = Faculty()
        faculty.universityID = UniversityRepository.getInstance().university.value?.id
        faculty.name = name
        UniversityRepository.getInstance().newFaculty(faculty)
    }

    fun updateFaculty(name: String){
        if (_faculty != null){
            _faculty!!.name = name
            UniversityRepository.getInstance().updateFaculty(_faculty!!)
        }
    }

    fun setCurrentFaculty(faculty: Faculty){
        UniversityRepository.getInstance().setCurrentFaculty(faculty)
    }
}