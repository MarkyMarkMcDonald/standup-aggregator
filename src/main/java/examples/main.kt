package examples

import org.jsoup.Jsoup
import standups.Standups
import standups.WhiteboardDotCom
import java.net.URL

fun main(args: Array<String>) {
    val standups = Standups(TheRealDealWhiteboardDotCom())
    val mostRecentFromEachStandup = standups.mostRecentFromEachStandup(listOf(2, 28))
    println(mostRecentFromEachStandup)
}

private val baseUrl = URL("http", "whiteboard.pivotallabs.com", 80, "/")

class TheRealDealWhiteboardDotCom : WhiteboardDotCom {
    override fun standupById(id: Int): String {
        return gimme("/posts/$id/edit")
    }

    override fun archivedStandupsTable(locationId: Int): String {
        return gimme("/standups/$locationId/posts/archived")
    }

    private fun gimme(path: String): String {
        val url = URL(baseUrl, path).toString()
        return Jsoup.connect(url).execute().body()
    }

}
