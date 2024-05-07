package com.example.lab3app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.lab3app.Application34
import com.example.lab3app.data.Faculty
import com.example.lab3app.data.Group
import com.example.lab3app.data.Student
import com.example.lab3app.data.University
import com.example.lab3app.database.UniversityDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

const val TAG = "com.example.lab3app.TAG"

class UniversityRepository private constructor() {
    companion object {
        private var INSTANCE: UniversityRepository? = null

        fun getInstance(): UniversityRepository {
            if (INSTANCE == null) {
                INSTANCE = UniversityRepository()
            }
            return INSTANCE
                ?: throw IllegalStateException("UniversityRepository: Репозиторий не инициализирован")
        }
    }

    private val universityDB by lazy {DBRepository(UniversityDB.getDatabase(Application34.context).universityDAO())}
    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)

//    var universityList: MutableLiveData<UniversityList?> = MutableLiveData()
    var university: MutableLiveData<University> = MutableLiveData()
    val universityList: LiveData<List<University>> = universityDB.getUniversities().asLiveData()


    fun onDestroy(){
        myCoroutineScope.cancel()
    }

    fun newUniversity(university: University){
        myCoroutineScope.launch {
            universityDB.insertUniversity(university)
            setCurrentUniversity(university)
        }
    }

    fun updateUniversity(university: University){
        myCoroutineScope.launch {
            universityDB.insertUniversity(university)
        }
    }

//    fun newUniversity(university: University) {
//        var listTmp = (universityList.value ?: UniversityList()).apply {
//            items.add(university)
//        }
//        universityList.postValue(listTmp)
//        setCurrentUniversity(university)
//    }

    fun setCurrentUniversity(_university: University) {
        university.postValue(_university)
    }

    fun setCurrentUniversity(position: Int) {
        if (universityList.value == null || position < 0 || (universityList.value?.size!! <= position))
            return
        setCurrentUniversity(universityList.value!![position])
    }

    fun getUniversityPosition(university: University): Int =
        universityList.value?.indexOfFirst {
            it.id == university.id
        } ?: 1

    fun getUniversityPosition() = getUniversityPosition(university.value ?: University())

//    fun updateUniversity(university: University) {
//        val position = getUniversityPosition(university)
//        if (position < 0) newUniversity(university)
//        else {
//            val listTmp = universityList.value!!
//            listTmp.items[position] = university
//            universityList.postValue(listTmp)
//        }
//    }

    fun deleteUniversity(university: University) {
        myCoroutineScope.launch {
            universityDB.deleteUniversity(university)
        }
        setCurrentFaculty(0)
        setCurrentUniversity(0)
    }

//    fun saveData() {
//        val context = Application352.context
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//        sharedPreferences.edit().apply() {
//            val gson = Gson()
//            val lst = universityList.value?.items ?: listOf<University>()
//            val jsonString = gson.toJson(lst)
//            Log.d(TAG, "Сохранение $jsonString")
//            putString(context.getString(R.string.preference_key_university_list), jsonString)
//            putString(
//                context.getString(R.string.preference_key_faculty_list),
//                gson.toJson(facultyList.value?.items ?: listOf<Faculty>())
//            )
//            apply()
//        }
//
//    }
//
//    fun loadData() {
//        val context = Application352.context
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//        sharedPreferences.apply {
//            var jsonString =
//                getString(context.getString(R.string.preference_key_university_list), null)
//            if (jsonString != null) {
//                Log.d(TAG, "Чтение University $jsonString")
//                val listType = object : TypeToken<List<University>>() {}.type
//                val tempList = Gson().fromJson<List<University>>(jsonString, listType)
//                val temp = UniversityList()
//                temp.items = tempList.toMutableList()
//                Log.d(TAG, "Загрузка University ${temp.toString()}")
//                universityList.value = temp
//                setCurrentUniversity(0)
//            }
//
//            jsonString =
//                getString(context.getString(R.string.preference_key_faculty_list), null)
//            if (jsonString != null) {
//                Log.d(TAG, "Чтение Faculty $jsonString")
//                val listType = object : TypeToken<List<Faculty>>() {}.type
//                val tempList = Gson().fromJson<List<Faculty>>(jsonString, listType)
//                val temp = FacultyList()
//                temp.items = tempList.toMutableList()
//                Log.d(TAG, "Загрузка Faculty ${temp.toString()}")
//                facultyList.value = temp
//                setCurrentFaculty(0)
//            }
//        }
//
//    }


    var facultyList: LiveData<List<Faculty>> = universityDB.getFaculties().asLiveData()
    var faculty: MutableLiveData<Faculty> = MutableLiveData()

    fun newFaculty(faculty: Faculty) {
        myCoroutineScope.launch {
            universityDB.insertFaculty(faculty)
            setCurrentFaculty(faculty)
        }
    }

    fun setCurrentFaculty(_faculty: Faculty) {
        faculty.postValue(_faculty)
    }

    fun setCurrentFaculty(position: Int) {
        if (facultyList.value == null || position < 0 || (facultyList.value?.size!! <= position))
            return
        setCurrentFaculty(facultyList.value!![position])
    }

    fun getFacultyPosition(faculty: Faculty): Int = facultyList.value?.indexOfFirst {
        it.id == faculty.id
    } ?: 1

    fun getFacultyPosition() = getFacultyPosition(faculty.value ?: Faculty())

    fun updateFaculty(faculty: Faculty) {
        newFaculty(faculty)
    }

    fun deleteFaculty(faculty: Faculty) {
        myCoroutineScope.launch {
            universityDB.deleteFaculty(faculty)
        }
        setCurrentFaculty(0)
    }








    var groupList: LiveData<List<Group>> = universityDB.getAllGroups().asLiveData()
    var group: MutableLiveData<Group> = MutableLiveData()

    fun newGroup(group: Group) {
        myCoroutineScope.launch {
            universityDB.insertGroup(group)
            setCurrentGroup(group)
        }
    }

    fun setCurrentGroup(_group: Group) {
        group.postValue(_group)
    }

    fun setCurrentGroup(position: Int) {
        if (groupList.value == null || position < 0 || (groupList.value?.size!! <= position))
            return
        setCurrentGroup(groupList.value!![position])
    }

    fun getGroupPosition(group: Group): Int = groupList.value?.indexOfFirst {
        it.id == group.id
    } ?: 1

    fun getGroupPosition() = getGroupPosition(group.value ?: Group())

    fun updateGroup(group: Group) {
        newGroup(group)
    }

    fun deleteGroup(group: Group) {
        myCoroutineScope.launch {
            universityDB.deleteGroup(group)
        }
        setCurrentGroup(0)
    }





    var studentList: LiveData<List<Student>> = universityDB.getAllStudents().asLiveData()
    var student: MutableLiveData<Student> = MutableLiveData()

    fun newStudent(student: Student) {
        myCoroutineScope.launch {
            universityDB.insertStudent(student)
            setCurrentStudent(student)
        }
    }

    fun setCurrentStudent(_student: Student) {
        student.postValue(_student)
    }

    fun setCurrentStudent(position: Int) {
        if (studentList.value == null || position < 0 || (studentList.value?.size!! <= position))
            return
        setCurrentStudent(studentList.value!![position])
    }

    fun getStudentPosition(student: Student): Int = studentList.value?.indexOfFirst {
        it.id == student.id
    } ?: 1

    fun getStudentPosition() = getStudentPosition(student.value ?: Student())

    fun updateStudent(student: Student) {
        newStudent(student)
    }

    fun deleteStudent(student: Student) {
        myCoroutineScope.launch {
            universityDB.deleteStudent(student)
            setCurrentStudent(0)
        }
    }

}