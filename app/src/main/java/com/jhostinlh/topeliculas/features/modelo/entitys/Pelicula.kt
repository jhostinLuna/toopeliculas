package com.jhostinlh.topeliculas.features.modelo.entitys


import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie

@Entity
data class Pelicula(
    @ColumnInfo    val adult: Boolean,
    @ColumnInfo    val backdropPath: String?,
    @ColumnInfo    val genreIds: String?,
    @PrimaryKey    val id: Int,
    @ColumnInfo    val originalLanguage: String?,
    @ColumnInfo    val originalTitle: String?,
    @ColumnInfo    val overview: String?,
    @ColumnInfo    val popularity: Double,
    @ColumnInfo    val posterPath: String?,
    @ColumnInfo    val releaseDate: String?,
    @ColumnInfo    val title: String?,
    @ColumnInfo    val video: Boolean,
    @ColumnInfo    val voteAverage: Double,
    @ColumnInfo    val voteCount: Int,
    @ColumnInfo    var favorito: Boolean = false
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(backdropPath)
        parcel.writeString(genreIds)
        parcel.writeInt(id)
        parcel.writeString(originalLanguage)
        parcel.writeString(originalTitle)
        parcel.writeString(overview)
        parcel.writeDouble(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeString(title)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeDouble(voteAverage)
        parcel.writeInt(voteCount)
        parcel.writeByte(if (favorito) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pelicula> {
        override fun createFromParcel(parcel: Parcel): Pelicula {
            return Pelicula(parcel)
        }

        override fun newArray(size: Int): Array<Pelicula?> {
            return arrayOfNulls(size)
        }
    }
    fun toMovie() = Movie(
        adult,
        backdropPath,
        jsonGenreIdToList(),
        id,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount,
        favorito
    )
    fun jsonGenreIdToList() :List<Int> {
        val gson = GsonBuilder().create()
        val lisType: TypeToken<List<Int?>?> = object : TypeToken<List<Int?>?>() {}
        var lista:List<Int> = listOf()
        if (!genreIds.isNullOrEmpty())
            lista = gson.fromJson<List<Int>>(genreIds, lisType.type).toList()
        return lista
    }
}