import StubbedWhiteboardDotCom.Companion.NEW_YORK_LOCATION_ID
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Before
import standups.Standups
import standups.ThingsMarkCaresAbout
import standups.WhiteboardDotCom


class AcceptanceTests {

    lateinit var standups: Standups

    @Before
    fun setUp() {
        standups = Standups(StubbedWhiteboardDotCom())
    }

    @Test
    fun eventsAndHelpsFromMultipleOffices_ForEachLatestStandup() {
        val thingsMarkCaresAbout = standups.mostRecentFromEachStandup(listOf(NEW_YORK_LOCATION_ID, StubbedWhiteboardDotCom.DC_LOCATION_ID))
        containsNYAndDcHelpsAndInterestings(thingsMarkCaresAbout)
    }

    @Test
    fun oneOfficeDoesNotHaveStandups() {
        val thingsMarkCaresAbout = standups.mostRecentFromEachStandup(listOf(StubbedWhiteboardDotCom.LOCATION_WITHOUT_ARCHIVED_POSTS_ID, NEW_YORK_LOCATION_ID, StubbedWhiteboardDotCom.DC_LOCATION_ID))
        containsNYAndDcHelpsAndInterestings(thingsMarkCaresAbout)
    }

    private fun containsNYAndDcHelpsAndInterestings(thingsMarkCaresAbout: ThingsMarkCaresAbout) {
        assertThat(thingsMarkCaresAbout.helps.size).isEqualTo(2)
        assertThat(thingsMarkCaresAbout.helps).asList()
                .extracting("title").contains(
                "Office retro time?",
                "bike to work from DC next week"
        )

        assertThat(thingsMarkCaresAbout.interestings.size).isEqualTo(2)
        assertThat(thingsMarkCaresAbout.interestings).asList()
                .extracting("title").contains(
                "Strongest Long Jump in Trials history",
                "Bash script tooling"
        )
    }
}

class StubbedWhiteboardDotCom : WhiteboardDotCom {

    companion object {
        private fun fixtureByName(name: String): String {
            val fileStream = StubbedWhiteboardDotCom::class.java.classLoader.getResourceAsStream("fixtures/" + name)
            return BufferedReader(InputStreamReader(fileStream))
                    .lines().collect(Collectors.joining("\n"))
        }

        val NEW_YORK_LOCATION_ID = 2
        val DC_LOCATION_ID = 28
        val LOCATION_WITHOUT_ARCHIVED_POSTS_ID = -1

        val NY_MOST_RECENT_POST_ID = 6185
        val DC_MOST_RECENT_POST_ID = 6212
    }

    override fun postById(id: Int): String {
        when (id) {
            NY_MOST_RECENT_POST_ID -> return fixtureByName("ny_most_recent_post.html")
            DC_MOST_RECENT_POST_ID -> return fixtureByName("dc_most_recent_post.html")
        }
        throw RuntimeException("Failed to load archived post - This stub only knows about the most recent post per office")
    }

    override fun archivedPostsByStandupId(id: Int): String {
        when (id) {
            NEW_YORK_LOCATION_ID -> return fixtureByName("ny_archived_posts.html")
            DC_LOCATION_ID -> return fixtureByName("dc_archived_posts.html")
            LOCATION_WITHOUT_ARCHIVED_POSTS_ID -> return fixtureByName("no_archived_posts.html")
        }
        throw RuntimeException("Failed to load list of archived posts")
    }

}
