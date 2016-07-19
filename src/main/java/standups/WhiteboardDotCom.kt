package standups

interface WhiteboardDotCom {
    fun postById(id: Int): String
    fun archivedPostsByStandupId(id: Int): String
}