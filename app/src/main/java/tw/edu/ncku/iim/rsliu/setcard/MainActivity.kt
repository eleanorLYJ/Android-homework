package tw.edu.ncku.iim.rsliu.setcard

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.os.Bundle
import android.util.DisplayMetrics
import tw.edu.ncku.iim.rsliu.setcard.SetCardView
import android.view.LayoutInflater
import android.widget.GridLayout
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import tw.edu.ncku.iim.rsliu.setcard.R
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_game -> {
                    switchFragment(GameFragment())
                    true
                }
                R.id.nav_history -> {
                    switchFragment(HistoryFragment())
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.nav_game
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
