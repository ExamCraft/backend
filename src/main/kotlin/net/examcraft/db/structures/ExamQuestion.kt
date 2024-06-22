package net.examcraft.db.structures

import net.examcraft.db.ExamQuestions
import org.jetbrains.exposed.sql.ResultRow

data class ExamQuestion(
    val id: String,
    val examBoard: String,
    val qualification: String,
    val subject: String,
    val examSeries: String,
    val paper: String,
    val questionNumber: Int,
    val tags: List<String>,
    val question: ByteArray,
    val answer: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExamQuestion

        if (id != other.id) return false
        if (examBoard != other.examBoard) return false
        if (qualification != other.qualification) return false
        if (subject != other.subject) return false
        if (examSeries != other.examSeries) return false
        if (paper != other.paper) return false
        if (questionNumber != other.questionNumber) return false
        if (!question.contentEquals(other.question)) return false
        if (!answer.contentEquals(other.answer)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + examBoard.hashCode()
        result = 31 * result + qualification.hashCode()
        result = 31 * result + subject.hashCode()
        result = 31 * result + examSeries.hashCode()
        result = 31 * result + paper.hashCode()
        result = 31 * result + questionNumber
        result = 31 * result + question.contentHashCode()
        result = 31 * result + answer.contentHashCode()
        return result
    }
}

fun ResultRow.toExamQuestion(): ExamQuestion {
    return ExamQuestion(
        this[ExamQuestions.id].value,
        this[ExamQuestions.examBoard],
        this[ExamQuestions.qualification],
        this[ExamQuestions.subject],
        this[ExamQuestions.examSeries],
        this[ExamQuestions.paper],
        this[ExamQuestions.questionNumber],
        this[ExamQuestions.tags],
        this[ExamQuestions.question],
        this[ExamQuestions.answer]
    )
}