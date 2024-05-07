package com.example.lab3app.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3app.MainActivity
import com.example.lab3app.R
import com.example.lab3app.data.University
import com.example.lab3app.databinding.FragmentUniversityListBinding

class UniversityListFragment : Fragment(), MainActivity.Edit {

    companion object {
        private var INSTANCE: UniversityListFragment? = null

        fun getInstance(): UniversityListFragment {
            if (INSTANCE == null) INSTANCE = UniversityListFragment()
            return INSTANCE ?: throw Exception("UniversityListFragment не создан")
        }
    }

    private lateinit var viewModel: UniversityListViewModel
    private lateinit var _binding: FragmentUniversityListBinding
    val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUniversityListBinding.inflate(inflater, container, false)
        binding.rvUniversityList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UniversityListViewModel::class.java)
        viewModel.universityList.observe(viewLifecycleOwner) {
            binding.rvUniversityList.adapter = UniversityAdapter(it!!)
        }
    }

    private inner class UniversityAdapter(private val items: List<University>) :
        RecyclerView.Adapter<UniversityAdapter.ItemHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): UniversityAdapter.ItemHolder {
            val item = layoutInflater.inflate(R.layout.element_university_list, parent, false)
            return ItemHolder(item!!)
        }

        override fun getItemCount(): Int = items.size
        override fun onBindViewHolder(holder: UniversityAdapter.ItemHolder, position: Int) {
            holder.bind(viewModel.universityList.value!![position])
        }

        private var lastView: View? = null
        private fun updateCurrentView(view: View) {
            lastView?.findViewById<ConstraintLayout>(R.id.clUniversityElement)
                ?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.findViewById<ConstraintLayout>(R.id.clUniversityElement)
                ?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.my_blue))
            lastView = view

        }

        private inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
            private lateinit var university: University

            fun bind(university: University) {

                if (university == viewModel.university)
                    updateCurrentView(itemView)

                this.university = university
                val tv = itemView.findViewById<TextView>(R.id.tvUniversityName)
                tv.text = university.name
                val tvc = itemView.findViewById<TextView>(R.id.tvUniversityCity)
                tvc.text = university.city

                val cl = View.OnClickListener {
                    viewModel.setCurrentUniversity(university)
                    updateCurrentView(itemView)
                }
                val c_l = view?.findViewById<ConstraintLayout>(R.id.clUniversityElement)
                c_l?.setOnClickListener(cl)
                tv.setOnClickListener(cl)
                tvc.setOnClickListener(cl)
                val lcl = View.OnLongClickListener {
                    viewModel.setCurrentUniversity(university)

                    (requireContext() as UpdateActivity).setFragment(MainActivity.facultyId)
                    true
                }
                c_l?.setOnLongClickListener(lcl)
                tv.setOnLongClickListener(lcl)
                tvc.setOnLongClickListener(lcl)
            }
        }
    }

    override fun append() {
        newUniversity()
    }

    override fun delete() {
        deleteUniversity()
    }

    override fun update() {
        updateUniversity()
    }

    private fun newUniversity() {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_university_edit, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.etName)
        val inputCity = mDialogView.findViewById<EditText>(R.id.etCity)
        AlertDialog.Builder(requireContext())
            .setTitle("Информация об университете")
            .setView(mDialogView)
            .setPositiveButton("Добавить") { _, _ ->
                if (inputName.text.isNotBlank() and inputCity.text.isNotBlank()) {
                    viewModel.appendUniversity(inputName.text.toString(), inputCity.text.toString())
                }
            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }

    private fun updateUniversity() {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_university_edit, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.etName)
        val inputCity = mDialogView.findViewById<EditText>(R.id.etCity)
        inputName.setText(viewModel.university?.name)
        inputCity.setText(viewModel.university?.city)
        AlertDialog.Builder(requireContext())
            .setTitle("Изменить информацию об университете")
            .setView(mDialogView)
            .setPositiveButton("Изменить") { _, _ ->
                if (inputName.text.isNotBlank() and inputCity.text.isNotBlank()) {
                    viewModel.updateUniversity(inputName.text.toString(), inputCity.text.toString())
                }
            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }

    private fun deleteUniversity(){
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление!")
            .setMessage("Вы действительно хотите удалить университет ${viewModel.university?.name ?: ""}?")
            .setPositiveButton("Да"){_, _ ->
                viewModel.deleteUniversity()
            }
            .setNegativeButton("Нет", null)
            .setCancelable(true)
            .create()
            .show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as UpdateActivity).setTitle("Список университетов")
    }
}