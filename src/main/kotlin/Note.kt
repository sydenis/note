data class Note(
    val id : UInt = 0U,
    val title : String = "",
    val text : String = "",
    var deleted : Boolean = false
)

data class Comment(
    val id : UInt = 0U,
    val noteId : UInt = 0U,
    val message : String = "",
    var deleted : Boolean = false
)
