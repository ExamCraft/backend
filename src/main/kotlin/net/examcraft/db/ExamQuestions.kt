package net.examcraft.db

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ArrayColumnType
import org.jetbrains.exposed.sql.TextColumnType

object ExamQuestions : IdTable<String>("exam_questions") {
    override val id = text("id")
        .entityId()

    val examBoard = text("exam_board")
    val qualification = text("qualification")
    val subject = text("subject")
    val examSeries = text("exam_series")
    val paper = text("paper")
    val questionNumber = integer("question_number")


    val question = binary("question")
    val answer = binary("answer")

    val tags = registerColumn("tags", ArrayColumnType(TextColumnType()))

    override val primaryKey = PrimaryKey(id)
}