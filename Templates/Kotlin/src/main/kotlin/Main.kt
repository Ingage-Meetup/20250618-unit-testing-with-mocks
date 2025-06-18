import db.InMemoryDatabase
import services.StudentService
import ui.ConsoleUI

fun main() {
    val db = InMemoryDatabase()
    val service = StudentService(db)
    val ui = ConsoleUI(service)

    ui.start()
}
