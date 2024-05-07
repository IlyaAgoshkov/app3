package com.example.lab3app.database

import androidx.room.Dao
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

@Dao
interface UniversityDAO {
    @Query("select * from University order by university_name")
    fun getUniversities(): Flow<List<University>>

    @Insert(entity = University::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniversity(university: University)

//    @Update(entity = University::class)
//    suspend fun updateUniversity(university: University)

    @Insert(entity = University::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListUniversity(universityList: List<University>)

    @Delete(entity = University::class)
    suspend fun deleteUniversity(university: University)

    @Query("delete from University")
    suspend fun deleteAllUniversities()



    @Query("select * from faculties order by faculty_name")
    fun getFaculties(): Flow<List<Faculty>>

    @Query("select * from faculties where university_id=:universityID")
    fun getUniversityFaculties(universityID: UUID): Flow<List<Faculty>>

    @Insert(entity = Faculty::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFaculty(faculty: Faculty)

//    @Update(entity = Faculty::class)
//    suspend fun updateFaculty(faculty: Faculty)

    @Insert(entity = Faculty::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListFaculty(facultyList: List<Faculty>)

    @Delete(entity = Faculty::class)
    suspend fun deleteFaculty(faculty: Faculty)

    @Query("delete from faculties where university_id=:universityID")
    suspend fun deleteUniversityFaculties(universityID: UUID)

    @Query("delete from faculties")
    suspend fun deleteAllFaculties()





    @Query("select * from groups order by group_name")
    fun getAllGroups(): Flow<List<Group>>

    @Query("select * from groups where faculty_id=:facultyID")
    fun getFacultyGroups(facultyID: UUID): Flow<List<Group>>

    @Insert(entity = Group::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: Group)

    @Delete(entity = Group::class)
    suspend fun deleteGroup(group: Group)

    @Query("delete from groups")
    suspend fun deleteAllGroups()



    @Query("select * from students order by firstName")
    fun getAllStudents(): Flow<List<Student>>

    @Query("select * from students where group_id=:groupID")
    fun getGroupStudents(groupID: UUID): Flow<List<Student>>

    @Insert(entity = Student::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Delete(entity = Student::class)
    suspend fun deleteStudent(student: Student)

    @Query("delete from students")
    suspend fun deleteAllStudents()

}