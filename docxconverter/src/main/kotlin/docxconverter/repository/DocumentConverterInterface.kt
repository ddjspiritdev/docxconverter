package docxconverter.repository

import java.io.InputStream
import java.io.OutputStream

/**
 * Converter's Interface
 *
 * Created by Douglas Djoumessi on 24.04.20
 * Copyright (c) 2020 spiritdev Softwareentwicklung GmbH. All rights reserved.
 */
interface DocumentConverterInterface {

    fun convertSourceDocumentToPDF(sourceDocStream: InputStream, param: Map<String, Any>? = null): OutputStream
}