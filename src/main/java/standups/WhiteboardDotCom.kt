package standups

interface WhiteboardDotCom {
    fun postById(id: Int): String
    fun archivedPostsByLocationId(id: Int): String
}