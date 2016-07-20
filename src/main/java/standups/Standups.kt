package standups

import java.time.LocalDate

class Standups(private val whiteboardDotCom: WhiteboardDotCom) {

    internal interface Whiteboard {
        fun standupIdentifiersBy(locationId: Int): List<StandupIdentifier>
        fun standupBy(standupIdentifier: StandupIdentifier): Standup
    }

    private val whiteboard = Whiteboard(whiteboardDotCom)

    fun mostRecentFromEachStandup(locationIds: List<Int>): ThingsMarkCaresAbout {
        throw NotImplementedError()
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
