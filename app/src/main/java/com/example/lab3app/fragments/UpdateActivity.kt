package com.example.lab3app.fragments

import com.example.lab3app.data.Student

interface UpdateActivity {
    fun setTitle(_title: String)
    fun setFragment(fragmentID: Int, student: Student?=null)

}