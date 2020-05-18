package docxconverter.controller


import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.nio.file.Paths

/**
 *ControllerTest
 *
 * Created by Douglas Djoumessi on 24.04.20
 * Copyright (c) 2020 spiritdev Softwareentwicklung GmbH. All rights reserved.
 */

@ExtendWith(MockitoExtension::class)
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc

class DocumentControllerTest {

    private lateinit var mockMvc: MockMvc
    private var logger: Logger = LoggerFactory.getLogger(DocumentControllerTest::class.java)
    private val file = Paths.get("src", "test", "resources", "testDocx.docx").toFile()
    private val input = FileInputStream(file)
    private val multipartFile = MockMultipartFile("upload", file.name, "multipart/form-data", input)

    @Before
    fun init() {

        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetPdfDocument() {

        try {
            this.mockMvc.perform(
                    MockMvcRequestBuilders.get("/service/storage/getPdf/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                    .andExpect(status().isOk)
        } catch (ex: Exception) {
            logger.error(ex.localizedMessage)
        }
    }

    @Test
    fun testStoreAndConvert() {

        try {

            this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("service/storage/storeConvert")
                    .file(multipartFile)
                    .accept(MediaType.APPLICATION_OCTET_STREAM))
                    .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                    .andExpect(status().isOk)
        } catch (exception: Exception) {

            logger.error(exception.localizedMessage)
        }
    }

    @Test
    fun testStoreDocument() {

        try {

            this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("service/storage/add")
                    .file(multipartFile)
                    .accept(MediaType.APPLICATION_OCTET_STREAM))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk)
        } catch (exception: Exception) {

            logger.error(exception.localizedMessage)
        }
    }

    @Test
    fun testGetSourceDocument() {

        try {

            this.mockMvc.perform(
                    MockMvcRequestBuilders.get("/service/storage/sourceDoc/1")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                    .andExpect(status().isOk)
        } catch (ex: Exception) {

            logger.error(ex.localizedMessage)
        }
    }

    @Test
    fun testDeleteDocument() {

        try {
            this.mockMvc.perform(
                    MockMvcRequestBuilders.get("/service/storage/deleteDoc/1")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk)
        } catch (ex: Exception) {

            logger.error(ex.localizedMessage)
        }
    }
}