import java.util.concurrent.atomic.AtomicInteger

const val ERROR_NOT_FOUND = 180

object WallService {
        private var notes = emptyList<Note>()
        private var noteIdGen: AtomicInteger = AtomicInteger()

        private var comments = emptyList<Comment>()
        private var commentIdGen: AtomicInteger = AtomicInteger()


        fun add(title: String, text: String): UInt {
            val id = noteIdGen.incrementAndGet().toUInt()

                val note = Note(
                    id,
                    title,
                    text
                );

            notes += note
            return id
        }

        fun getById(noteId: UInt): Note? {
            var result: Note? = null

            for (note in notes)
                if (note.id == noteId) {
                    result = note
                    break
                }

            return result
        }

        fun delete(noteId: UInt): Int {
            val note: Note? = getById(noteId)

            return if (note != null)  {
                note.deleted = true
                0
            } else
                ERROR_NOT_FOUND
        }

        fun edit(noteId: UInt, title: String, text: String): Int {
            val note: Note? = getById(noteId)

            return if (note?.deleted == false) {
                val newNote = note.copy(title = title, text = text);
                notes += newNote
                0
            } else
                ERROR_NOT_FOUND
        }

        fun get(index: Int): Note {
           return notes[index]
        }

        fun clear() {
            notes = emptyList<Note>()
            noteIdGen.set(0)

            comments = emptyList<Comment>()
            commentIdGen.set(0)
        }

        fun createComment(noteId: UInt, message: String): Int{
            val note: Note? = getById(noteId)

            return if (note?.deleted == false) {
                val cid = commentIdGen.incrementAndGet().toUInt()
                val comment = Comment(cid, noteId, message)
                comments += comment
                0
            } else
                ERROR_NOT_FOUND
        }

        fun getCommentById(commentId: UInt): Comment? {
           var result: Comment? = null

           for (comment in comments)
              if (comment.id == commentId) {
                result = comment
                break
            }

           return result
        }

        fun deleteComment(commentId: UInt): Int {
           val comment: Comment? = getCommentById(commentId)

           return if (comment != null)  {
              comment.deleted = true
              0
           } else
              ERROR_NOT_FOUND
        }

        fun editComment(commentId: UInt, message: String): Int {
           val comment: Comment? = getCommentById(commentId)

           return if (comment?.deleted == false) {
              val newNComment = comment.copy(message = message);
              comments += newNComment
              0
           } else
              ERROR_NOT_FOUND
        }

        fun restoreComment(commentId: UInt): Int {
            val comment: Comment? = getCommentById(commentId)

            return if (comment != null)  {
                comment.deleted = false
                0
            } else
                ERROR_NOT_FOUND
        }

        fun getComments(noteId: UInt, sort: Boolean, offset: UInt, count: UInt): List<Comment> {
            if (count == 0U)
                throw RuntimeException("Кол-во возвращаемых записей установлено в 0")

            var result = emptyList<Comment>()
            var currPos = 0U

            for (comment in comments)
                if (currPos >= offset) {
                    result += comment
                    currPos++

                    if (currPos == count)
                       break
                }

            return  result
        }
}

