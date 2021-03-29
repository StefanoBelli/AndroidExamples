package it.ste.guessanumber

import android.os.Parcel
import android.os.Parcelable
import kotlin.random.Random

class Game() : Parcelable {
    companion object {
        private const val RAND_FROM = 0
        private const val RAND_TO = 100
        private const val MAX_ATTEMPTS = 5
        val CREATOR = object : Parcelable.Creator<Game> {
            override fun createFromParcel(parcel: Parcel): Game {
                return Game(parcel)
            }

            override fun newArray(size: Int): Array<Game?> {
                return arrayOfNulls(size)
            }
        }
    }

    private var attempts = 0
    private var targetNumber = 0

    enum class Status {
        WIN,    //reset
        LOOSE,  //reset
        TOO_BIG,
        TOO_SMALL
    }

    init {
        reset()
    }

    private fun reset() {
        targetNumber = Random.nextInt(RAND_FROM, RAND_TO)
        attempts = MAX_ATTEMPTS
    }

    public fun check(n: Int): Status {
        if(n == targetNumber) {
            reset()
            return Status.WIN
        }

        if(--attempts == 0) {
            reset()
            return Status.LOOSE
        }

        if(n < targetNumber) {
            return Status.TOO_SMALL
        }

        return Status.TOO_BIG
    }

    constructor(parcel: Parcel) {
        attempts = parcel.readInt()
        targetNumber = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(attempts)
        parcel.writeInt(targetNumber)
    }

    override fun describeContents(): Int {
        return 0
    }
}