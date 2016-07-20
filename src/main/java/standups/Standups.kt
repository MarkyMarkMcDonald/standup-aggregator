package standups

import java.time.LocalDate

public class Standups(private val whiteboardDotCom: WhiteboardDotCom) {

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
        val methods = ThingsMarkCaresAbout::class.members.filter { member -> member.name.contains("component") }

        return thingsMarkCaresAbouts.reduce {
            thingsMarkCaredAboutSoFar, someThingsMarkCaresAbout ->

            val firstHelps = methods[0].call(thingsMarkCaredAboutSoFar) as List<StandupItem>
            val secondHelps = methods[0].call(someThingsMarkCaresAbout) as  List<StandupItem>

            val firstInterestings = methods[1].call(thingsMarkCaredAboutSoFar) as List<StandupItem>
            val secondInterestings = methods[1].call(someThingsMarkCaresAbout) as  List<StandupItem>

            ThingsMarkCaresAbout(firstHelps + secondHelps, firstInterestings + secondInterestings)
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

