package io.github.fobo66.wearmmr.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

/**
 * (c) 2017 Andrey Mukamolow <fobo66@protonmail.com>
 * Created 12/20/17.
 */
@Entity(tableName = "mmr")
data class MatchmakingRating(

    @PrimaryKey val playerId: Int,

    @ColumnInfo(name = "player_name") val name: String,

    @ColumnInfo(name = "player_persona_name") val personaName: String,

    @ColumnInfo(name = "player_avatar_url") val avatarUrl: String?,

    @ColumnInfo(name = "player_rating") val rating: Int?

) : Parcelable {

  constructor(parcel: Parcel) : this(
      parcel.readInt(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readInt())

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeInt(playerId)
    parcel.writeString(name)
    parcel.writeString(personaName)
    parcel.writeString(avatarUrl)
    if (rating != null) {
      parcel.writeInt(rating)
    } else {
      parcel.writeInt(0)
    }
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Creator<MatchmakingRating> {
    override fun createFromParcel(parcel: Parcel): MatchmakingRating {
      return MatchmakingRating(parcel)
    }

    override fun newArray(size: Int): Array<MatchmakingRating?> {
      return arrayOfNulls(size)
    }
  }
}