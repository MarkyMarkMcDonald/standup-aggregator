package standups

import java.time.LocalDate

class Standups(private val whiteboardDotCom: WhiteboardDotCom) {

    internal interface Whiteboard {
        fun thingsMarkCaresAbout(standupPostIdentifier: StandupPostIdentifier): ThingsMarkCaresAbout
        fun extractPosts(locationId: Int): List<StandupPostIdentifier>
    }

    private val whiteboard = Whiteboard(whiteboardDotCom)

    fun mostRecentFromEachStandup(standupIds: List<Int>): ThingsMarkCaresAbout {
        //TODO: Use standups.Whiteboard to do stuff!
        val posts : List<StandupPostIdentifier> = standupIds.map { standupId -> whiteboard.extractPosts(standupId).sortedByDescending { post -> post.postedAt }.firstOrNull() }.filter { it != null }.map {it!!}
        val thingsMarkCaresAbouts: List<ThingsMarkCaresAbout> = posts.map { standupPostIdentifier -> whiteboard.thingsMarkCaresAbout(standupPostIdentifier) }
        return merge(thingsMarkCaresAbouts)
    }

    private fun merge(thingsMarkCaresAbouts: List<ThingsMarkCaresAbout>): ThingsMarkCaresAbout {
        return thingsMarkCaresAbouts.reduce {
            thingsMarkCaredAboutSoFar, someThingsMarkCaresAbout ->
                ThingsMarkCaresAbout(thingsMarkCaredAboutSoFar.helps + someThingsMarkCaresAbout.helps, thingsMarkCaredAboutSoFar.interestings + someThingsMarkCaresAbout.interestings)
        }
    }
}

data class StandupPostIdentifier(val id: Int, val postedAt: LocalDate)

data class StandupPost(val helps: List<StandupItem> = listOf(),
                       val interestings: List<StandupItem> = listOf())

data class ThingsMarkCaresAbout(
        val helps: List<StandupItem> = listOf(),
        val interestings: List<StandupItem> = listOf()
)

data class StandupItem(val title: String, val description: String, val author: String)

