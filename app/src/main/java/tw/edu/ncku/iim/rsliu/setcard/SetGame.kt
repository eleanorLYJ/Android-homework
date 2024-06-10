package tw.edu.ncku.iim.rsliu.setcard

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
class SetGame() : Parcelable {
    var deck: MutableList<SetCard> = mutableListOf()
    var table: MutableList<SetCard> = mutableListOf()
    var selectedCards: MutableList<SetCard> = mutableListOf()
    var foundSets: MutableList<List<SetCard>> = mutableListOf()
    init {
        resetGame()
    }

    private fun resetGame() {
        // Initialize the deck with all possible cards
        deck = mutableListOf()
        for (number in 1..3) {
            for (shape in SetCard.Shape.values()) {
                for (color in listOf(Color.RED, Color.GREEN, Color.MAGENTA)) {
                    for (shading in SetCard.Shading.values()) {
                        deck.add(SetCard(number, shape, color, shading))
                    }
                }
            }
        }
        deck.shuffle()
        table = deck.take(12).toMutableList()
        deck = deck.drop(12).toMutableList()
        selectedCards.clear()
        foundSets.clear()
    }

    fun selectCard(card: SetCard) {
        if (selectedCards.contains(card)) {
            selectedCards.remove(card)
        } else {
            selectedCards.add(card)
        }
    }

    fun isSet(cards: List<SetCard>): Boolean {
        if (cards.size != 3) return false
        val numbers = cards.map { it.number }.toSet()
        val shapes = cards.map { it.shape }.toSet()
        val colors = cards.map { it.color }.toSet()
        val shadings = cards.map { it.shading }.toSet()
        return (numbers.size == 1 || numbers.size == 3) &&
                (shapes.size == 1 || shapes.size == 3) &&
                (colors.size == 1 || colors.size == 3) &&
                (shadings.size == 1 || shadings.size == 3)
    }

    fun removeSet(cards: List<SetCard>) {
        table.removeAll(cards)
        if (deck.size >= 3) {
            table.addAll(deck.take(3))
            deck = deck.drop(3).toMutableList()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(table)
        parcel.writeTypedList(deck)
        parcel.writeTypedList(foundSets.map { ArrayList(it) })
        parcel.writeTypedList(selectedCards)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SetGame> {
        override fun createFromParcel(parcel: Parcel): SetGame {
            return SetGame().apply {
                table = parcel.createTypedArrayList(SetCard.CREATOR) ?: mutableListOf()
                deck = parcel.createTypedArrayList(SetCard.CREATOR) ?: mutableListOf()
                foundSets = (parcel.createTypedArrayList(CREATOR) ?: mutableListOf()) as MutableList<List<SetCard>>
                selectedCards = parcel.createTypedArrayList(SetCard.CREATOR) ?: mutableListOf()
            }
        }

        override fun newArray(size: Int): Array<SetGame?> {
            return arrayOfNulls(size)
        }
    }
}

private fun Parcel.writeTypedList(map: List<ArrayList<SetCard>>) {

}
