package docxconverter.service

import docxconverter.configuration.ConfigProperties
import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.*

/**
 * StorageService
 *
 * Created by Douglas Djoumessi on 24.04.20
 * Copyright (c) 2020 spiritdev Softwareentwicklung GmbH. All rights reserved.
 */

@Service
class StorageService(private val globalProperties: ConfigProperties, private val converter: WordConverter) {

    var path = File(globalProperties.storagePath).toPath()
    private var logger: Logger = LoggerFactory.getLogger(StorageService::class.java)

    @Throws(IOException::class)
    fun storeToDir(filename: String, sourceInput: InputStream, extension: String) {

        var destination = path.resolve(setExtension(filename, extension)).toString()
        FileUtils.copyInputStreamToFile(sourceInput, File(destination))
    }

    @Throws(FileNotFoundException::class)
    fun getFileByName(filename: String): File {

        var dir = File(globalProperties.storagePath)
        var list = dir.list()
        if (list != null) {

            File(globalProperties.storagePath).walkTopDown().forEach {
                if (it.name.contentEquals(filename)) {

                    return it
                }
            }
        }

        return File("")
    }

    fun getStoredPdf(filename: String, extension: String): File {

        var file = File("")
        try {
            var destination = path.resolve(setExtension(filename, "pdf")).toString()
            if (!path.resolve(setExtension(filename, "pdf")).toFile().exists()) {

                val stream = path.resolve(setExtension(filename, extension)).toFile()
                converter.docxToPdf(stream, destination)
                file = path.resolve(destination).toFile()
            }

        } catch (exception: Exception) {

            logger.error(exception.printStackTrace().toString())
        }

        return file
    }

    fun deleteFile(filename: String, extension: String) {

        try {
            path.resolve(setExtension(filename, extension)).toFile().delete()
            val file = path.resolve(setExtension(filename, "pdf")).toFile()
            if (file.exists()) {
                file.delete()
            }
        } catch (ex: Exception) {
            logger.error(ex.localizedMessage)
        }
    }

    fun setExtension(name: String, extension: String): String {

        return "$name.$extension"
    }

    fun storeAndConvert(filename: String, extension: String, sourceInput: InputStream): File {

        var file = File("")
        try {

            storeToDir(filename, sourceInput, extension)
            var destination = path.resolve(setExtension(filename, "pdf")).toString()
            converter.docxToPdf(path.resolve(setExtension(filename, extension)).toFile(),
                    destination)
            file = path.resolve(setExtension(filename, "pdf")).toFile()
        } catch (ex: Exception) {

            logger.error(ex.printStackTrace().toString())
        }

        return file
    }
}