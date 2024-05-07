package com.example.lab3app

import android.app.Application
import android.content.Context

class Application34: Application() {
    override fun onCreate() {
        super.onCreate()
//        UniversityRepository.getInstance().loadData()
    }


    init {
        instance  = this
    }

    companion object{
        private var instance: Application34? = null
        val context
            get() = applicationContext()

        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}