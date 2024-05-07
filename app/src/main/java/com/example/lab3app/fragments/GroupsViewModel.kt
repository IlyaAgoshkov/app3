package com.example.lab3app.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab3app.data.Group
import com.example.lab3app.repository.UniversityRepository

class GroupsViewModel : ViewModel() {
    var groupList: MutableLiveData<List<Group>> = MutableLiveData()
    private var _group : Group? = null
    val group
        get() =_group

    init {
        UniversityRepository.getInstance().groupList.observeForever{
            groupList.postValue(
                it.filter { it.facultyID==UniversityRepository.getInstance().faculty.value?.id }.sortedBy { it.name } as MutableList<Group>
            )
        }
        UniversityRepository.getInstance().group.observeForever{
            _group=it
        }

        UniversityRepository.getInstance().faculty.observeForever{
            groupList.postValue(
                UniversityRepository.getInstance().groupList.value?.filter
                {it.facultyID== UniversityRepository.getInstance().faculty.value?.id}?.sortedBy { it.name } as MutableList<Group>
            )
        }
    }

    fun deleteGroup(){
        if(group != null)
            UniversityRepository.getInstance().deleteGroup(group!!)
    }

    val faculty
        get() = UniversityRepository.getInstance().faculty.value

    fun appendGroup(groupName: String){
        val group= Group()
        group.name=groupName
        group.facultyID=faculty?.id
        UniversityRepository.getInstance().newGroup(group)
    }

    fun updateGroup(groupName: String){
        if(_group != null){
            _group!!.name=groupName
            UniversityRepository.getInstance().updateGroup(_group!!)
        }
    }

    fun setCurrentGroup(position: Int){
        if((groupList.value?.size ?: 0) > position)
            groupList.value?.let{UniversityRepository.getInstance().setCurrentGroup(it.get(position))}
    }

    fun setCurrentGroup(group: Group){
        UniversityRepository.getInstance().setCurrentGroup(group)
    }

    val getGroupListPosition
        get()= groupList.value?.indexOfFirst { it.id==group?.id } ?: -1
}