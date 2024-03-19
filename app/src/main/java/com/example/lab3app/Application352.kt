package com.example.lab3app

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.example.lab3app.repository.UniversityRepository

class Application352: Application() {
    override fun onCreate() {
        super.onCreate()
        UniversityRepository.getInstance().loadData()
    }


    init {
        instance  = this
    }

    companion object{
        private var instance: Application352? = null
        val context
            get() = applicationContext()

        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}