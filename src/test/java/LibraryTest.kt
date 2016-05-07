import org.junit.Test
import org.junit.Assert.*

class LibraryTest {
    @Test fun testSomeLibraryMethod() {
        val classUnderTest = Library()
        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod())
    }
}
