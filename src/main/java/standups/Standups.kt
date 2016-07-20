package standups

import java.time.LocalDate

class Standups(private val whiteboardDotCom: WhiteboardDotCom) {

    internal interface Whiteboard {
        fun extractPosts(locationId: Int): List<StandupPostIdentifier>
        fun thingsMarkCaresAbout(standupPostIdentifier: StandupPostIdentifier): ThingsMarkCaresAbout
    }

    private val whiteboard = Whiteboard(whiteboardDotCom)

    fun mostRecentFromEachStandup(standupIds: List<Int>): ThingsMarkCaresAbout {
        throw NotImplementedError()
    }
}

data class StandupPostIdentifier(val id: Int, val postedAt: LocalDate)

data class StandupPost(val helps: List<StandupItem> = listOf(),
                       val interestings: List<StandupItem> = listOf())

data class StandupItem(val title: String, val description: String, val author: String)

data class ThingsMarkCaresAbout(
        val helps: List<StandupItem> = listOf(),
        val interestings: List<StandupItem> = listOf()
)
