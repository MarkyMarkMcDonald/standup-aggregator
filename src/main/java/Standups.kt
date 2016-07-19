import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface WhiteboardDotCom {
    fun postById(id: Int): String
    fun archivedPostsByStandupId(id: Int): String
}

class Standups(private val whiteboardDotCom: WhiteboardDotCom) {

    fun mostRecentFromEachStandup(standupIds: List<Int>): ThingsMarkCaresAbout {
        throw NotImplementedError()
    }

    private fun extractPosts(): (Int) -> List<Post> {
        return { id ->
            val archivedPostsText = whiteboardDotCom.archivedPostsByStandupId(id)
            val archivedPostsDoc = Jsoup.parse(archivedPostsText)
            archivedPostsDoc.select("table tr")
                    .skipFirst()
                    .map { row ->
                        val dataElements = row.select("td")
                        val postId = dataElements[0].select("a").attr("href").split("/").last().toInt()
                        val postedAt = LocalDate.parse(dataElements[2].text(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"))
                        Post(postId, postedAt)
                    }
        }
    }

    private fun thingsMarkCaresAbout(): (Post) -> ThingsMarkCaresAbout {
        return { post ->
            val postText = whiteboardDotCom.postById(post.id)
            val postDoc = Jsoup.parse(postText)
            val helps = postDoc.select(".help .item").map(standupItem())
            val interestings = postDoc.select(".interesting .item").map(standupItem())
            ThingsMarkCaresAbout(helps, interestings)
        }
    }

    private fun standupItem(): (Element) -> StandupItem {
        return { element ->
            val title = element.select(".title").text()
            val description = element.select(".description").text()
            val author = element.select(".author").text()
            StandupItem(title, description, author)
        }
    }

}

fun <E> List<E>.skipFirst(): List<E> {
    return this.subList(1, this.size)
}

private data class Post(val id: Int, val postedAt: LocalDate)

private data class Standup(val helps: List<StandupItem> = listOf(),
                           val interestings: List<StandupItem> = listOf())

data class ThingsMarkCaresAbout(
        val helps: List<StandupItem> = listOf(),
        val interestings: List<StandupItem> = listOf()
)

data class StandupItem(val title: String, val description: String, val author: String)
