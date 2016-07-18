package web

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.GetMethod
import spark.Spark.*
import standups.StandupItem
import standups.Standups
import standups.WhiteboardDotCom

object StandupsMarkCaresAbout {
    val ids = listOf(98, 104, 49, 30, 3, 23, 10, 33, 11, 99, 100, 17, 2, 18, 12, 31, 6, 1, 5, 38, 90, 35, 14, 28)

    val portOverride: String? = System.getenv("PORT")
    val standups = Standups(WebScraper)

    @JvmStatic fun main(args: Array<String>) {
        val thingsMarkCaresAbout = standups.aggregate(ids)

        if (portOverride != null) {
            port(portOverride.toInt())
        }

        get("/standups-mark-cares-about") { request, response ->
            index(thingsMarkCaresAbout.helps, thingsMarkCaresAbout.interestings)
        }
    }

    private fun index(helps: List<StandupItem>, interestings: List<StandupItem>): String {
        return createHTML(true).html {
            body {
                h2 { +"Helps" }
                ul {
                    helps.forEach { help ->
                        li {
                            +help.title
                            br
                            +help.description
                        }
                    }
                }
                h2 { +"Interestings" }
                ul {
                    interestings.forEach { interesting ->
                        li {
                            +interesting.title
                            br
                            +interesting.description
                        }
                    }
                }
            }
        }
    }
}

object WebScraper : WhiteboardDotCom {
    override fun postById(id: Int): String {
        val request = GetMethod("http://whiteboard.pivotallabs.com/standups/$id/posts/archived")
        HttpClient().executeMethod(request)
        return String(request.responseBody)
    }

    override fun archivedPostsByStandupId(id: Int): String {
        val request = GetMethod("http://whiteboard.pivotallabs.com/posts/$id")
        HttpClient().executeMethod(request)
        return String(request.responseBody)
    }
}
