package tw.edu.ncku.iim.rsliu.setcard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var game: SetGame = SetGame()
//    val foundSets: MutableLiveData<List<SetCard>> = MutableLiveData(listOf())
    val foundSets: MutableList<List<SetCard>> = mutableListOf()
    fun addSet(set: List<SetCard>) {
        foundSets.add(set)
    }
}