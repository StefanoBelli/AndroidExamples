package it.ste.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val sval : IntArray) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var numberTv = itemView.findViewById<TextView>(R.id.numberTv)

        fun setNumberInTv(n: Int) {
            numberTv.text = n.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("RVAdapter", "onCreateViewHolder() called")
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("RVAdapter", "onBindViewHolder() called")
        holder.setNumberInTv(sval[position])
    }

    override fun getItemCount(): Int {
        return sval.size
    }
}