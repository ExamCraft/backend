package net.examcraft.handler.gen.exam

import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfWriter
import net.examcraft.db.structures.ExamQuestion
import java.io.ByteArrayOutputStream

fun generateExam(questions: List<ExamQuestion>) = generateQuestionPaper(questions)

private fun generateQuestionPaper(questions: List<ExamQuestion>): ByteArray {
    val document = Document()
    val dataStream = ByteArrayOutputStream()
    PdfWriter.getInstance(document, dataStream)
    document.open()

    questions.forEach {
        document.add(Image.getInstance(it.question).apply {
            scaleAbsolute(
                (width / 1.3).toFloat(),
                (height / 1.3).toFloat()
            )
        })
    }

    questions.forEach {
        document.add(Image.getInstance(it.answer).apply {
            scaleAbsolute(
                (width / 1.3).toFloat(),
                (height / 1.3).toFloat()
            )
        })
    }

    document.close()
    return dataStream.toByteArray()
}