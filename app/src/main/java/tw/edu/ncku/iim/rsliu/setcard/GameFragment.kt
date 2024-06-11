// GameFragment.kt
package tw.edu.ncku.iim.rsliu.setcard

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tw.edu.ncku.iim.rsliu.setcard.SetCardView

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var cardViews: List<SetCardView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
        val gridLayout = view.findViewById<GridLayout>(R.id.gridLayout)
        val restartButton = view.findViewById<Button>(R.id.restartButton)

        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val cardWidth = screenWidth / 3
        val cardHeight = screenHeight / 5 // Adjusted for better scaling with the button

        cardViews = List(12) { index ->
            val cardView = SetCardView(requireContext()).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = cardWidth
                    height = cardHeight
                }
                id = View.generateViewId()
            }
            gridLayout.addView(cardView)
            cardView
        }

        cardViews.forEachIndexed { index, cardView ->
            cardView.setOnClickListener {
                val game = viewModel.game.value ?: return@setOnClickListener
                if (index < game.table.size) {
                    val card = game.table[index]
                    game.selectCard(card)
                    if (game.selectedCards.size == 3) {
                        if (game.isSet(game.selectedCards)) {
                            game.removeSet(game.selectedCards)
                            Toast.makeText(requireContext(), "Set found and removed!", Toast.LENGTH_SHORT).show()
                            viewModel.addSet(game.selectedCards)
                        } else {
                            Toast.makeText(requireContext(), "Not a valid set!", Toast.LENGTH_SHORT).show()
                        }
                        game.selectedCards.clear()
                    }
                    updateUI()
                    checkGameEnd()
                }
            }
        }

        restartButton.setOnClickListener {
            viewModel.restartGame()
            updateUI()
        }

        viewModel.game.observe(viewLifecycleOwner) { game ->
            updateUI()
        }

        updateUI()
        return view
    }

    private fun updateUI() {
        val game = viewModel.game.value ?: return
        cardViews.forEachIndexed { index, cardView ->
            if (index < game.table.size) {
                val card = game.table[index]
                cardView.number = card.number
                cardView.shape = card.shape
                cardView.color = card.color
                cardView.shading = card.shading
                cardView.visibility = View.VISIBLE
            } else {
                cardView.visibility = View.INVISIBLE
            }
        }

        logValidSets()
    }

    private fun logValidSets() {
        val game = viewModel.game.value ?: return
        for (i in 0 until game.table.size - 2) {
            for (j in i + 1 until game.table.size - 1) {
                for (k in j + 1 until game.table.size) {
                    if (game.isSet(listOf(game.table[i], game.table[j], game.table[k]))) {
                        Log.d("SetGame", "Valid set found at indices: $i, $j, $k")
                    }
                }
            }
        }
    }

    private fun checkGameEnd() {
        val game = viewModel.game.value ?: return
        if (game.deck.isEmpty() && !hasPossibleSet()) {
            Toast.makeText(requireContext(), "Game over! No more sets possible.", Toast.LENGTH_LONG).show()
        }
    }

    private fun hasPossibleSet(): Boolean {
        val game = viewModel.game.value ?: return false
        for (i in 0 until game.table.size - 2) {
            for (j in i + 1 until game.table.size - 1) {
                for (k in j + 1 until game.table.size) {
                    if (game.isSet(listOf(game.table[i], game.table[j], game.table[k]))) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
