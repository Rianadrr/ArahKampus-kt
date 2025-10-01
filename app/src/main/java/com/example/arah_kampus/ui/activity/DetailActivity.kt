package com.example.arah_kampus.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.arah_kampus.R
import com.example.arah_kampus.data.local.db.AppDatabase
import com.example.arah_kampus.data.local.db.entity.Building
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    private var buildingId: Int = -1
    private var currentBuilding: Building? = null

    private lateinit var etName: EditText
    private lateinit var etLat: EditText
    private lateinit var etLng: EditText
    private lateinit var cbFavorite: CheckBox
    private lateinit var btnSave: Button
    private lateinit var btnBack: Button
    private lateinit var btnNavigate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        etName = findViewById(R.id.etName)
        etLat = findViewById(R.id.etLat)
        etLng = findViewById(R.id.etLng)
        cbFavorite = findViewById(R.id.cbFavorite)
        btnSave = findViewById(R.id.btnSave)
        btnBack = findViewById(R.id.btnBack)
        btnNavigate = findViewById(R.id.btnNavigate)

        db = AppDatabase.get(this)

        // Ambil ID dari intent
        buildingId = intent.getIntExtra("building_id", -1)

        if (buildingId != -1) {
            lifecycleScope.launch {
                val building = db.buildingDao().getBuildingById(buildingId)
                withContext(Dispatchers.Main) {
                    currentBuilding = building
                    etName.setText(building?.name)
                    etLat.setText(building?.lat.toString())
                    etLng.setText(building?.lng.toString())
                    cbFavorite.isChecked = building?.favorite == true
                }
            }
        }

        btnSave.setOnClickListener {
            saveChanges()
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnNavigate.setOnClickListener {
            currentBuilding?.let {
                val uri = Uri.parse("geo:${it.lat},${it.lng}?q=${Uri.encode(it.name)}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }
        }
    }

    private fun saveChanges() {
        val newName = etName.text.toString()
        val newLat = etLat.text.toString().toDoubleOrNull() ?: 0.0
        val newLng = etLng.text.toString().toDoubleOrNull() ?: 0.0
        val newFav = cbFavorite.isChecked

        val updated = currentBuilding?.copy(
            name = newName,
            lat = newLat,
            lng = newLng,
            favorite = newFav
        )

        updated?.let {
            lifecycleScope.launch {
                db.buildingDao().update(it)
                withContext(Dispatchers.Main) {
                    finish() // kembali setelah save
                }
            }
        }
    }
}
