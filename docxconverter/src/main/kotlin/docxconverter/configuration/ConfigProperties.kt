package docxconverter.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * ConfigProperties.kt
 *
 * Created by Douglas Djoumessi on 24.04.20
 * Copyright (c) 2020 spiritdev Softwareentwicklung GmbH. All rights reserved.
 */

@Component
//@ConfigurationProperties("storage")
class ConfigProperties {

    @Value("\${storage.path}")
    lateinit var storagePath: String

    fun loadStoragePath(): String {

        return storagePath
    }

}