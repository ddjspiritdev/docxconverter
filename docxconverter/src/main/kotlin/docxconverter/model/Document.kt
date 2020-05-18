package docxconverter.model

import java.util.*
import javax.persistence.*

/**
 *Document.kt
 *
 * Created by Douglas Djoumessi on 24.04.20
 * Copyright (c) 2020 spiritdev Softwareentwicklung GmbH. All rights reserved.
 */

@Entity
@Table(name = "document")
data class Document(
        var docName: String = "",
        var docType: String = ""
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
    private var creationDate: Date = Calendar.getInstance().time
    private var storedAs: String = ""

    @PrePersist
    fun prePersist() {

        creationDate = Date()
        storedAs = UUID.randomUUID().toString()
    }

    fun getStorageName(): String {
        return storedAs
    }
}