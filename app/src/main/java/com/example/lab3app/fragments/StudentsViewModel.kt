package com.example.lab3app.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab3app.data.Group
import com.example.lab3app.data.Student
import com.example.lab3app.repository.UniversityRepository
import java.util.Date

class StudentsViewModel : ViewModel() {
    var studentList: MutableLiveData<List<Student>> = MutableLiveData()

    private var _student: Student? = null
    val student
        get() = _student

    var group: Group? = null

    fun set_Group(group: Group){
        this.group = group
        UniversityRepository.getInstance().studentList.observeForever{
            studentList.postValue(
                it.filter { it.groupID == group.id } as MutableList<Student>
            )
        }
        UniversityRepository.getInstance().student.observeForever{
            _student = it
        }
    }

    fun deleteStudent(){
        if(student != null){
            UniversityRepository.getInstance().deleteStudent(student!!)
        }
    }

    fun appendStudent(lastName: String, firstName: String, middleName: String, birthDate: Date, phone: String, sex:Int){
        val student=Student()
        student.lastName=lastName
        student.firstName=firstName
        student.middleName=middleName
        student.birthDate=birthDate
        student.phone=phone
        student.sex=sex
        student.groupID=group!!.id
        UniversityRepository.getInstance().newStudent(student)
    }

    fun updateStudent(lastName: String, firstName: String, middleName: String, birthDate: Date, phone: String, sex:Int){
        if(_student!=null){
            _student!!.lastName=lastName
            _student!!.firstName=firstName
            _student!!.middleName=middleName
            _student!!.birthDate=birthDate
            _student!!.phone=phone
            _student!!.sex=sex
            _student!!.groupID=group!!.id
            UniversityRepository.getInstance().updateStudent(_student!!)
        }
    }

    fun setCurrentStudent(student: Student){
        UniversityRepository.getInstance().setCurrentStudent(student)
    }

}
