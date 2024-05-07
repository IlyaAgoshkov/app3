package com.example.lab3app.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lab3app.data.Faculty
import com.example.lab3app.data.Group
import com.example.lab3app.data.Student
import com.example.lab3app.data.University
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface UniversityDBRepository {
    fun getUniversities(): Flow<List<University>>
    suspend fun insertUniversity(university: University)
//    suspend fun updateUniversity(university: University)
    suspend fun insertListUniversity(universityList: List<University>)
    suspend fun deleteUniversity(university: University)
    suspend fun deleteAllUniversities()



    fun getFaculties(): Flow<List<Faculty>>
    fun getUniversityFaculties(universityID: UUID): Flow<List<Faculty>>
    suspend fun insertFaculty(faculty: Faculty)
//    suspend fun updateFaculty(faculty: Faculty)
    suspend fun insertListFaculty(facultyList: List<Faculty>)
    suspend fun deleteFaculty(faculty: Faculty)
    suspend fun deleteUniversityFaculties(universityID: UUID)
    suspend fun deleteAllFaculties()


    fun getAllGroups(): Flow<List<Group>>
    fun getFacultyGroups(facultyID: UUID): Flow<List<Group>>
    suspend fun insertGroup(group: Group)
    suspend fun deleteGroup(group: Group)
    suspend fun deleteAllGroups()



    fun getAllStudents(): Flow<List<Student>>
    fun getGroupStudents(groupID: UUID): Flow<List<Student>>
    suspend fun insertStudent(student: Student)
    suspend fun deleteStudent(student: Student)
    suspend fun deleteAllStudents()

}