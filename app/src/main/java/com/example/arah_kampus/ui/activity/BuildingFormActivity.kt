package com.example.arah_kampus.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.arah_kampus.R
import com.example.arah_kampus.data.local.Prefs
import com.example.arah_kampus.data.local.db.AppDatabase
import com.example.arah_kampus.data.local.db.entity.Building
import kotlinx.coroutines.launch

class BuildingFormActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    private lateinit var etName: EditText
    private lateinit var etLat: EditText
    private lateinit var etLng: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_form)

        db = AppDatabase.get(this)

        etName = findViewById(R.id.etName)
        etLat = findViewById(R.id.etLat)
        etLng = findViewById(R.id.etLng)

        // Prefill dari SharedPreferences
        val (savedName, savedLat, savedLng) = Prefs.getFavorite(this)
        etName.setText(savedName ?: "")
        etLat.setText(savedLat?.toString() ?: "")
        etLng.setText(savedLng?.toString() ?: "")

        findViewById<Button>(R.id.btnFavorit).setOnClickListener {
            val name = etName.text.toString()
            val lat = etLat.text.toString().toDoubleOrNull() ?: 0.0
            val lng = etLng.text.toString().toDoubleOrNull() ?: 0.0

            // Simpan ke SharedPreferences
            Prefs.saveFavorite(this, name, lat, lng)

            // Simpan ke Room
            lifecycleScope.launch {
                db.buildingDao().upsert(
                    Building(
                        name = name,
                        lat = lat,
                        lng = lng,
                        favorite = true
                    )
                )
            }
        }

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        }

        findViewById<Button>(R.id.btnSeeFavorite).setOnClickListener {
            startActivity(Intent(this, SummaryActivity::class.java))
        }
    }
}
