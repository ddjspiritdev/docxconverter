package docxconverter.service

import docxconverter.model.Document
import docxconverter.repository.DocumentRepository
import org.apache.commons.io.FilenameUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException

/**
 * DocumentService
 *
 * Created by Douglas Djoumessi on 24.04.20
 * Copyright (c) 2020 spiritdev Softwareentwicklung GmbH. All rights reserved.
 */

@Service
class DocumentService(private val repository: DocumentRepository, private val storageService: StorageService) {

    private var logger: Logger = LoggerFactory.getLogger(DocumentService::class.java)

    @Throws(IOException::class)
    fun saveDocument(file: MultipartFile): Long {

        if (file != null) {

            var extension = FilenameUtils.getExtension(file.originalFilename)
            var filename = FilenameUtils.removeExtension(file.originalFilename).orEmpty()
            var document = filename?.let {
                Document(
                        docName = it,
                        docType = extension
                )
            }
            val data = repository.save(document)
            storageService.storeToDir(data.getStorageName(), file.inputStream, data.docType)
            logger.debug("File successfully stored")

            return data.id
        }
        return 0
    }

    fun deleteDocument(id: Long): Boolean {

        if (repository.existsById(id)) {
            val data = repository.findByIdOrNull(id)
            repository.deleteById(id)
            storageService.deleteFile(data!!.getStorageName(), data.docType)
            logger.debug("File with id '${data.id}' successfully deleted")

            return true
        }

        return false
    }

    @Throws(IOException::class)
    fun getSourceDocument(id: Long): File {

        if (repository.existsById(id)) {

            val fileData = repository.findByIdOrNull(id)

            return storageService.getFileByName(fileData!!.getStorageName() + "." + fileData.docType)
        }

        return File(" ")
    }

    @Throws(IOException::class)
    fun getPdfFromDocument(id: Long): File {

        if (repository.existsById(id)) {

            val fileData = repository.findByIdOrNull(id)
            logger.debug("File with id '${id}' was successfully converted")

            return storageService.getStoredPdf(fileData!!.getStorageName(), fileData.docType)
        }

        return File(" ")

    }

    fun saveAndConvert(file: MultipartFile): File {

        if (file != null) {

            var extension = FilenameUtils.getExtension(file.originalFilename)
            var filename = FilenameUtils.removeExtension(file.originalFilename.orEmpty()).orEmpty()
            val fileData = Document(
                    docName = filename,
                    docType = extension
            )
            val data = repository.save(fileData)
            logger.debug("File with id '${data.id}' war successfully stored and converted")

            return storageService.storeAndConvert(data.getStorageName(), data.docType, file.inputStream)
        }

        return File("")
    }
}