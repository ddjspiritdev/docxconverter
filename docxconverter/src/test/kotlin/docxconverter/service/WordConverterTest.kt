package docxconverter.service

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.poi.xwpf.extractor.XWPFWordExtractor
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.nio.file.Paths

/**
 * WordConverterTest
 *
 * Created by Douglas Djoumessi on 24.04.20
 * Copyright (c) 2020 spiritdev Softwareentwicklung GmbH. All rights reserved.
 */

@ExtendWith(SpringExtension::class)
@SpringBootTest
class WordConverterTest {

    private var logger: Logger = LoggerFactory.getLogger(WordConverterTest::class.java)

    @Autowired
    private lateinit var wordConverter: WordConverter
    val origin = Paths.get("src", "test", "resources", "testDocx.docx").toString()
    val destination = Paths.get("src", "test", "resources", "testDocx.pdf").toString()

    @Before
    fun init() {

        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testToPdf() {

        val originFile = File(origin)
        try {

            wordConverter.docxToPdf(originFile, destination)
            var converted = File(destination)

            val document = PDDocument.load(converted)
            val pdfContent = PDFTextStripper().getText(document).lines()
            document.close()

            val docx = XWPFDocument(FileInputStream(origin))
            val extract = XWPFWordExtractor(docx)
            val originContent = extract.text.lines()

            Assert.assertTrue(originContent[0] == pdfContent[0])
        } catch (ex: Exception) {

            logger.error(ex.localizedMessage)
        }
    }

    @After
    fun deleteFile() {

        try {
            File(destination).delete()
        } catch (ex: Exception) {

            logger.error(ex.localizedMessage)
        }
    }
}