package com.example.lab3app.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lab3app.MainActivity
import com.example.lab3app.R
import com.example.lab3app.data.Group
import com.example.lab3app.databinding.FragmentGroupsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GroupsFragment : Fragment(), MainActivity.Edit {

    companion object {
        private var INSTANCE: GroupsFragment?= null
        fun getInstance(): GroupsFragment{
            if(INSTANCE == null) INSTANCE= GroupsFragment()
            return INSTANCE ?: throw Exception("GroupsFragment не создан")
        }
        fun newInstance() : GroupsFragment{
            INSTANCE = GroupsFragment()
            return INSTANCE!!
        }
    }

    private lateinit var viewModel: GroupsViewModel
    private  var tabPosition: Int=0
    private lateinit var _binding: FragmentGroupsBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[GroupsViewModel::class.java]
        val ma = (requireActivity() as UpdateActivity)
        ma.setTitle("Факультет \"${viewModel.faculty?.name}\"")

        viewModel.groupList.observe(viewLifecycleOwner){
            createUI(it)
        }
    }

    private inner class GroupPageAdapter(fa: FragmentActivity, private val groups: List<Group>?): FragmentStateAdapter(fa){
        override fun getItemCount(): Int {
            return (groups?.size ?: 0)
        }

        override fun createFragment(position: Int): Fragment {
            return StudentsFragment.newInstance(groups!![position])
        }
    }

    private fun createUI(groupList: List<Group>){
        binding.tlGroups.clearOnTabSelectedListeners()
        binding.tlGroups.removeAllTabs()

        for (i in 0 until  (groupList.size)){
            binding.tlGroups.addTab(binding.tlGroups.newTab().apply {
                text= groupList.get(i).name
            })
        }

        val adapter=GroupPageAdapter(requireActivity(), viewModel.groupList.value)
        binding.vpStudents.adapter=adapter
        TabLayoutMediator(binding.tlGroups, binding.vpStudents, true, true){
            tab, pos -> tab.text = groupList.get(pos).name
        }.attach()

        tabPosition = 0
        if (viewModel.group != null)
            tabPosition = if(viewModel.getGroupListPosition>=0)
                viewModel.getGroupListPosition
            else
                0
        viewModel.setCurrentGroup(tabPosition)
        binding.tlGroups.selectTab(binding.tlGroups.getTabAt(tabPosition), true)

        binding.tlGroups.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabPosition = tab?.position!!
                viewModel.setCurrentGroup(groupList[tabPosition])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun append() {
        editGroup()
    }

    override fun update() {
        editGroup(viewModel.group?.name ?: "")
    }

    private fun editGroup(groupName: String = "") {

    }

    override fun delete() {
        deleteDialog()
    }

    private fun deleteDialog(){
        if(viewModel.group == null) return
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление!")
            .setMessage("Вы действительно хотите удалить группу ${viewModel.group?.name ?: ""}?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.deleteGroup()
            }
            .setNegativeButton("Нет", null)
            .setCancelable(true)
            .create()
            .show()
    }



}