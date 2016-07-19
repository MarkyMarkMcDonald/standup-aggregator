interface WhiteboardDotCom {
    fun postById(id: Int): String
    fun archivedPostsByStandupId(id: Int): String
}

class Standups(private val whiteboardDotCom: WhiteboardDotCom) {
    fun aggregate(standupIds: List<Int>): ThingsMarkCaresAbout {
        throw NotImplementedError("Implement me!")
    }
}

data class ThingsMarkCaresAbout(val helps: List<Any?>, val interestings: List<Any?>) {

}
