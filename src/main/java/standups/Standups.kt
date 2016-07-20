package standups

import java.time.LocalDate
import java.util.*

class Standups(private val whiteboardDotCom: WhiteboardDotCom) {

    internal interface Whiteboard {
        fun standupIdentifiersBy(locationId: Int): List<StandupIdentifier>
        fun standupBy(standupIdentifier: StandupIdentifier): Standup
    }

    private val whiteboard = Whiteboard(whiteboardDotCom)

    fun mostRecentFromEachStandup(locationIds: List<Int>): ThingsMarkCaresAbout {
        val mostRecentIdentifierPerLocation: List<StandupIdentifier> = locationIds.map { locationId -> whiteboard.standupIdentifiersBy(locationId).maxBy { standupIdentifier -> standupIdentifier.postedAt} }.filterNotNull()
        val standups: List<Standup> = mostRecentIdentifierPerLocation.map { whiteboard.standupBy(it) }
        val (helps, interestings) = standups.reduce { standup1, standup2 -> standup1 + standup2}
        return ThingsMarkCaresAbout(helps, interestings)
    }

    operator fun Standup.plus(other: Standup): Standup {
        return Standup(this.helps + other.helps, this.interestings + other.interestings)
    }
}

data class ThingsMarkCaresAbout(
        val helps: List<StandupItem> = listOf(),
        val interestings: List<StandupItem> = listOf()
)

data class StandupIdentifier(val id: Int, val postedAt: LocalDate)

data class Standup(val helps: List<StandupItem> = listOf(),
                   val interestings: List<StandupItem> = listOf())

data class StandupItem(val title: String, val description: String, val author: String)
