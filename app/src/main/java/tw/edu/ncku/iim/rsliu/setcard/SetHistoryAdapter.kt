package tw.edu.ncku.iim.rsliu.setcard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.edu.ncku.iim.rsliu.setcard.R

class SetHistoryAdapter(private val sets: List<List<SetCard>>) :
    RecyclerView.Adapter<SetHistoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.setTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_set, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val set = sets[position]
        holder.textView.text = set.joinToString(", ") { it.toString() }
    }

    override fun getItemCount(): Int = sets.size
}
