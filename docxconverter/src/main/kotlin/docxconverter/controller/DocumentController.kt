package docxconverter.controller

import docxconverter.service.DocumentService

import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import javax.servlet.http.HttpServletRequest

/**
 * DocumentController.kt
 *
 * Created by Douglas Djoumessi on 24.04.20
 * Copyright (c) 2020 spiritdev Softwareentwicklung GmbH. All rights reserved.
 */

@Controller
@RequestMapping(value = ["service/storage"])
class DocumentController(private val documentService: DocumentService) {

    @PostMapping(value = ["/add"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Throws(IOException::class)
    fun storeDocument(@RequestParam(value = "file") file: MultipartFile): ResponseEntity<String> {

        var id = documentService.saveDocument(file)

        return ResponseEntity(id.toString(), HttpStatus.ACCEPTED)
    }

    @GetMapping(value = ["/getPdf/{id}"], produces = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getPdfDocument(@PathVariable("id") id: Long, request: HttpServletRequest): ResponseEntity<Any?> {

        val file = documentService.getPdfFromDocument(id)

        return fileAsResponse(file, request)
    }

    @GetMapping(value = ["/sourceDoc/{id}"], produces = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getSourceDocument(@PathVariable("id") id: Long, request: HttpServletRequest): ResponseEntity<Any?> {

        val file = documentService.getSourceDocument(id)

        return fileAsResponse(file, request)
    }

    @PostMapping(value = ["/storeConvert"], produces = [MediaType.APPLICATION_PDF_VALUE])
    fun storeAndConvertDocument(@RequestParam(value = "file") inputFile: MultipartFile, request: HttpServletRequest): ResponseEntity<Any?> {

        val file = documentService.saveAndConvert(inputFile)

        return fileAsResponse(file, request)
    }

    @DeleteMapping(value = ["deleteDoc/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteDocument(@PathVariable("id") id: Long): ResponseEntity<String> {

        if (documentService.deleteDocument(id)) {

            return ResponseEntity(HttpStatus.OK)
        }

        return ResponseEntity("document not found!", HttpStatus.NOT_FOUND)
    }

    private fun fileAsResponse(file: File, request: HttpServletRequest): ResponseEntity<Any?> {

        var res: ByteArrayResource

        if (file.exists()) {

            var type = request.servletContext.getMimeType(file.path)

            res = ByteArrayResource(Files.readAllBytes(file.toPath()))

            if (type == null) {

                type = "application/octet-stream"
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= " + file.name)
                    .contentType(MediaType.parseMediaType(type))
                    .contentLength(file.length())
                    .body(res)
        }

        return ResponseEntity.notFound().build()
    }
}