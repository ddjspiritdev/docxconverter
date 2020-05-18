package docxconverter


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

/**
 *
 * Created by Douglas Djoumessi on 24.04.20
 * Copyright (c) 2020 spiritdev Softwareentwicklung GmbH. All rights reserved.
 */

@SpringBootApplication
@ComponentScan(basePackages = ["de.spiritdev.de.spiritdev.various.document.service"])
class Application

fun main(args: Array<String>) {

    runApplication<Application>(*args)
}
