// HistoryFragment.kt
package tw.edu.ncku.iim.rsliu.setcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SetHistoryAdapter(viewModel.foundSets.value ?: mutableListOf())

        viewModel.foundSets.observe(viewLifecycleOwner) { sets ->
            (recyclerView.adapter as SetHistoryAdapter).updateData(sets)
        }

        return view
    }
}
