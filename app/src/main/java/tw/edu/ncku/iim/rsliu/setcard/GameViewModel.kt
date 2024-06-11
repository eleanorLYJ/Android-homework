// GameViewModel.kt
package tw.edu.ncku.iim.rsliu.setcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _game = MutableLiveData<SetGame>()
    val game: LiveData<SetGame> get() = _game

    private val _foundSets = MutableLiveData<MutableList<List<SetCard>>>()
    val foundSets: LiveData<MutableList<List<SetCard>>> get() = _foundSets

    init {
        _game.value = SetGame()
        _foundSets.value = mutableListOf()
    }

    fun restartGame() {
        _game.value = SetGame()
        _foundSets.value?.clear()
    }

    fun addSet(set: List<SetCard>) {
        _foundSets.value?.add(set)
        _foundSets.value = _foundSets.value // To trigger LiveData update
    }
}
