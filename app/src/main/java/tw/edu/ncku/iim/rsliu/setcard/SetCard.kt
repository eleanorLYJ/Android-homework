package tw.edu.ncku.iim.rsliu.setcard
import android.os.Parcel
import android.os.Parcelable

data class SetCard(
    val number: Int,
    val shape: Shape,
    val color: Int,
    val shading: Shading
) : Parcelable {
    enum class Shape {
        OVAL, DIAMOND, WORM
    }

    enum class Shading {
        EMPTY, SOLID, STRIP
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        Shape.values()[parcel.readInt()],
        parcel.readInt(),
        Shading.values()[parcel.readInt()]
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(number)
        parcel.writeInt(shape.ordinal)
        parcel.writeInt(color)
        parcel.writeInt(shading.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SetCard> {
        override fun createFromParcel(parcel: Parcel): SetCard {
            return SetCard(parcel)
        }

        override fun newArray(size: Int): Array<SetCard?> {
            return arrayOfNulls(size)
        }
    }
}
