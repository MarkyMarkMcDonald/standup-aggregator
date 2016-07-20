package standups

interface WhiteboardDotCom {
    fun standupById(id: Int): String
    fun archivedStandupsTable(locationId: Int): String
}