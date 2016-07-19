package standups

import java.time.LocalDate

class Standups(private val whiteboardDotCom: WhiteboardDotCom) {

    internal interface Whiteboard {
        fun extractPosts(): (Int) -> List<Post>
        fun thingsMarkCaresAbout(): (Post) -> ThingsMarkCaresAbout
    }

    private val whiteboard = Whiteboard(whiteboardDotCom)

    fun mostRecentFromEachStandup(standupIds: List<Int>): ThingsMarkCaresAbout {
        //TODO: Use standups.Whiteboard to do stuff!
        throw NotImplementedError()
    }
}

data class Post(val id: Int, val postedAt: LocalDate)

data class Standup(val helps: List<StandupItem> = listOf(),
                   val interestings: List<StandupItem> = listOf())

data class ThingsMarkCaresAbout(
        val helps: List<StandupItem> = listOf(),
        val interestings: List<StandupItem> = listOf()
)

data class StandupItem(val title: String, val description: String, val author: String)

