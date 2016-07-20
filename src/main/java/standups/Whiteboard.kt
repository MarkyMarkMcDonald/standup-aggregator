package standups

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Whiteboard(private val whiteboardDotCom: WhiteboardDotCom) : Standups.Whiteboard {
    override fun extractPosts(locationId: Int): List<StandupPostIdentifier> {
        val archivedPostsText = whiteboardDotCom.archivedPostsByStandupId(locationId)
        val archivedPostsDoc = Jsoup.parse(archivedPostsText)
        return archivedPostsDoc.select("table tr")
                .skipFirst()
                .map { row ->
                    val dataElements = row.select("td")
                    val postId = dataElements[0].select("a").attr("href").split("/").last().toInt()
                    val postedAt = LocalDate.parse(dataElements[2].text(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"))
                    StandupPostIdentifier(postId, postedAt)
                }
    }

    override fun thingsMarkCaresAbout(standupPostIdentifier: StandupPostIdentifier): ThingsMarkCaresAbout {
        val postText = whiteboardDotCom.postById(standupPostIdentifier.id)
        val postDoc = Jsoup.parse(postText)
        val helps = postDoc.select(".help .item").map(standupItem())
        val interestings = postDoc.select(".interesting .item").map(standupItem())
        return ThingsMarkCaresAbout(helps, interestings)
    }

    private fun standupItem(): (Element) -> StandupItem {
        return { element ->
            val title = element.select(".title").text()
            val description = element.select(".description").text()
            val author = element.select(".author").text()
            StandupItem(title, description, author)
        }
    }

    private fun <E> List<E>.skipFirst(): List<E> {
        return this.subList(1, this.size)
    }
}