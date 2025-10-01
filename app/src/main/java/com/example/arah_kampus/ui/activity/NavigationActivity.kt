package com.example.arah_kampus.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.arah_kampus.R
import com.example.arah_kampus.data.local.db.AppDatabase
import com.example.arah_kampus.data.local.Prefs
import com.example.arah_kampus.data.local.db.entity.NavLog
import kotlinx.coroutines.launch

class NavigationActivity : ComponentActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        db = AppDatabase.get(this)

        val (name, lat, lng) = Prefs.getFavorite(this)
//        if (name != null && lat != null && lng != null) {
        if (name != null) {
//            val gmmIntentUri = Uri.parse("geo:$lat,$lng?q=$lat,$lng($name)")
            val gmmIntentUri = Uri.parse("geo:0,0?q=$name")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            var opened = false
            try {
                startActivity(mapIntent)
                opened = true
            } catch (e: Exception) {
                Toast.makeText(this, "Maps tidak tersedia", Toast.LENGTH_SHORT).show()
            }

            lifecycleScope.launch {
                db.navLogDao().insertLog(
                    NavLog(
                        buildingName = name,
                        opened = opened,
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
        }

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            startActivity(Intent(this, SummaryActivity::class.java))
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
