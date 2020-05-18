package docxconverter.service


import docxconverter.repository.DocumentConverterInterface
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.*
import java.lang.Exception

/**
 * WordConverter Service
 *
 * Created by Douglas Djoumessi on 24.04.20
 * Copyright (c) 2020 spiritdev Softwareentwicklung GmbH. All rights reserved.
 */

@Service
class WordConverter : DocumentConverterInterface {

    private var logger: Logger = LoggerFactory.getLogger(WordConverter::class.java)
    private var options: PdfOptions = PdfOptions.create()

    override fun convertSourceDocumentToPDF(sourceDocStream: InputStream, param: Map<String, Any>?): OutputStream {

        val destination = param?.get("destination")
        val outFile = File(destination.toString())

        return FileOutputStream(outFile)
    }

    fun docxToPdf(inputFile: File, docDestination: String) {

        val sourceDocStream = FileInputStream(inputFile)
        try {
            val document = XWPFDocument(sourceDocStream)
            val param = mapOf("destination" to docDestination)

            PdfConverter.getInstance().convert(document, convertSourceDocumentToPDF(sourceDocStream, param), options)
        } catch (ex: Exception) {

            logger.error(ex.localizedMessage)
        }

    }

}