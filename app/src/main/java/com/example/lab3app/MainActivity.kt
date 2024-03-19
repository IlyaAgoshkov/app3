package com.example.lab3app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.lab3app.fragments.UniversityListFragment
import com.example.lab3app.fragments.UpdateActivity
import com.example.lab3app.repository.UniversityRepository

class MainActivity : AppCompatActivity(), UpdateActivity {

    interface Edit{
        fun append()
        fun update()
        fun delete()
    }

    private var _miNewUniversity: MenuItem? = null
    private var _miUpdateUniversity: MenuItem? = null
    private var _miDeleteUniversity: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fcwMain, UniversityListFragment.getInstance()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        _miNewUniversity = menu?.findItem(R.id.miNewUniversity)
        _miUpdateUniversity = menu?.findItem(R.id.miUpdateUniversity)
        _miDeleteUniversity = menu?.findItem(R.id.miDeleteUniversity)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.miNewUniversity ->{
                val fedit: Edit = UniversityListFragment.getInstance()
                fedit.append()
                true
            }
            R.id.miUpdateUniversity ->{
                val fedit: Edit = UniversityListFragment.getInstance()
                fedit.update()
                true
            }
            R.id.miDeleteUniversity ->{
                val fedit: Edit = UniversityListFragment.getInstance()
                fedit.delete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        UniversityRepository.getInstance().saveData()
        super.onStop()
    }

    override fun updateTitle(newTitle: String) {
        title = newTitle
    }
}