package com.example.arah_kampus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.arah_kampus.R
import com.example.arah_kampus.data.local.db.entity.Building

class SummaryAdapter(
    private val items: List<Building>,
    private val logCount: Int,
    private val onClick: (Building) -> Unit
) : RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvBuildingName)
        val tvCoords: TextView = view.findViewById(R.id.tvBuildingCoords)
        val tvLogCount: TextView = view.findViewById(R.id.tvLogCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_summary, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val building = items[position]
        holder.tvName.text = building.name
        holder.tvCoords.text = "Lat: ${building.lat}, Lng: ${building.lng}"
        holder.tvLogCount.text = "Total Log Navigasi: $logCount"

        holder.itemView.setOnClickListener {
            onClick(building)
        }
    }
}
