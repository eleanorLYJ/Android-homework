// SetHistoryAdapter.kt
package tw.edu.ncku.iim.rsliu.setcard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SetHistoryAdapter(private var data: List<List<SetCard>>) : RecyclerView.Adapter<SetHistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.TextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_set, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val set = data[position]
        holder.textView.text = set.joinToString(separator = ", ") { card ->
            "${card.number} ${card.color} ${card.shape} ${card.shading}"
        }
    }

    override fun getItemCount(): Int = data.size

    fun updateData(newData: List<List<SetCard>>) {
        data = newData
        notifyDataSetChanged()
    }
}
