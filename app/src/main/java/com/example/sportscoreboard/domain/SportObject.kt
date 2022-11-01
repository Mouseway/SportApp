package com.example.sportscoreboard.domain
import com.example.sportscoreboard.R
import com.example.sportscoreboard.domain.filters.SportObjectTypeFilter
import com.example.sportscoreboard.others.Constants

sealed class SportObject{

    abstract val id: String
    abstract val name: String
    abstract val sport: String
    abstract val image: String?
    abstract val filter: SportObjectTypeFilter
    abstract val gender: String?
    abstract val defaultImageSource: Int
    abstract val country: String?
    abstract val favorite: Boolean

    fun getImagePath(): String {
        image?.let {
            return Constants.API_IMAGE_PATH + image
        }
        throw IllegalArgumentException()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as SportObject

        return (
            this.id == other.id &&
            this.filter == other.filter &&
            this.name == other.name &&
            this.image == other.image &&
            this.country == other.country &&
            this.gender == other.gender &&
            this.sport == other.sport &&
            this.favorite == other.favorite
        )
    }

    abstract fun copy(id: String = this.id,
             name: String = this.name,
             sport: String =  this.sport,
             image: String? = this.image,
             gender: String? = this.gender,
             country: String? = this.country,
             favorite: Boolean = this.favorite
    ) : SportObject
}

class Contest(
    override val id: String,
    override val name: String,
    override val sport: String,
    override val image: String?,
    override val gender: String?,
    override val country: String?,
    override val favorite: Boolean
) : SportObject (){
    override val filter: SportObjectTypeFilter = SportObjectTypeFilter.CONTEST
    override val defaultImageSource: Int = R.drawable.contest

    override fun copy(
        id: String,
        name: String,
        sport: String,
        image: String?,
        gender: String?,
        country: String?,
        favorite: Boolean
    ): Contest {
        return Contest(id, name, sport, image, gender, country, favorite)
    }
}

class Team(
    override val id: String,
    override val name: String,
    override val sport: String,
    override val image: String?,
    override val gender: String?,
    override val country: String?,
    override val favorite: Boolean
) : SportObject(){
    override val filter: SportObjectTypeFilter = SportObjectTypeFilter.TEAM
    override val defaultImageSource: Int = R.drawable.club

    override fun copy(
        id: String,
        name: String,
        sport: String,
        image: String?,
        gender: String?,
        country: String?,
        favorite: Boolean
    ): Team {
        return Team(id, name, sport, image, gender, country, favorite)
    }
}

class Player(
    override val id: String,
    override val name: String,
    override val sport: String,
    override val image: String?,
    override val gender: String?,
    override val country: String?,
    override val favorite: Boolean
) : SportObject(){
    override val filter: SportObjectTypeFilter = SportObjectTypeFilter.SINGLE_PLAYER
    override val defaultImageSource: Int = if(gender == "Woman") R.drawable.player_woman else R.drawable.player_man

    override fun copy(
        id: String,
        name: String,
        sport: String,
        image: String?,
        gender: String?,
        country: String?,
        favorite: Boolean
    ): Player {
        return Player(id, name, sport, image, gender, country, favorite)
    }
}