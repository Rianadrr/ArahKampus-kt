package com.example.arah_kampus.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arah_kampus.R
import com.example.arah_kampus.data.local.db.AppDatabase
import com.example.arah_kampus.adapter.SummaryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SummaryActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    private lateinit var rvSummary: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        rvSummary = findViewById(R.id.rvSummary)
        rvSummary.layoutManager = LinearLayoutManager(this)

        db = AppDatabase.get(this)

        lifecycleScope.launch {
            val favorites = db.buildingDao().getFavorites()
            val logCount = db.navLogDao().getLogCount()

            val adapter = SummaryAdapter(favorites, logCount) { building ->
                val intent = Intent(this@SummaryActivity, DetailActivity::class.java)
                intent.putExtra("building_id", building.id)
                startActivity(intent)
            }
            withContext(Dispatchers.Main) {
                rvSummary.adapter = adapter
            }
        }


        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
