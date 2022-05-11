import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class WallServiceTest {

    @Before
    fun setUp() {
        WallService.clear()
        val idNote = WallService.add("new","test")
        WallService.createComment(idNote, "comment")
    }

    @Test
    fun add() {
        val expected = 2U
        val actual = WallService.add("new","test")

        assertEquals(expected, actual)
    }

    @Test
    fun getById() {
        val noteId = 1U
        val expected = "new"

        val actual = WallService.getById(noteId)?.title
        assertEquals(expected, actual)
    }

    @Test
    fun delete() {
        val noteId = 1U
        val expected = null

        WallService.delete(noteId)
        val actual = WallService.getById(noteId)
        assertEquals(expected, actual)
    }

    @Test
    fun edit() {
        val noteId = 1U
        val expected = "updated"

        WallService.edit(noteId, "new", expected)
        val actual = WallService.getById(noteId)?.text
        assertEquals(expected, actual)
    }

    @Test
    fun createComment() {
        val expected = 2U
        val actual = WallService.createComment(1U,"new comment")

        assertEquals(expected, actual)
    }

    @Test
    fun getCommentById() {
        val commentId = 1U
        val expected = "comment"

        val actual = WallService.getCommentById(commentId)?.message
        assertEquals(expected, actual)
    }

    @Test
    fun deleteComment() {
        val commentId = 1U
        val expected = false

        WallService.deleteComment(commentId)
        val actual = WallService.getById(commentId)?.deleted
        assertEquals(expected, actual)
    }

    @Test
    fun editComment() {
        val commentId = 1U
        val expected = "updated"

        WallService.editComment(commentId, expected)
        val actual = WallService.getCommentById(commentId)?.message
        assertEquals(expected, actual)
    }

    @Test (expected = RuntimeException::class)
    fun getComments() {
        val noteId = 1U
        WallService.getComments(noteId, true, 0U, 0U)
    }

}