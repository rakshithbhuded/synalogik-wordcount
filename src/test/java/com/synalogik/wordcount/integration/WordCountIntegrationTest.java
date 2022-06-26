package com.synalogik.wordcount.integration;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static com.synalogik.wordcount.controller.WordCountController.RESOURCE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false, printOnlyOnFailure = false)
@DisplayName("Word-Count Controller Integration Tests")
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class WordCountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("File with data returns valid response")
    @Order(1)
    @SneakyThrows
    @Test
    void testFileWithDataReturnsValidResponse() {

        mockMvc.perform(multipart(RESOURCE).file(createMockFileWithDataWithExtension("Hello world & good morning. The date is 18/05/2016", ".txt"))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Word Count = 9\n" +
                        "Average word length = 4.556\n" +
                        "Number of words of length 1 is 1\n" +
                        "Number of words of length 2 is 1\n" +
                        "Number of words of length 3 is 1\n" +
                        "Number of words of length 4 is 2\n" +
                        "Number of words of length 5 is 2\n" +
                        "Number of words of length 7 is 1\n" +
                        "Number of words of length 10 is 1\n" +
                        "The most frequently occurring word length is 2, for word lengths of 4,5"));
    }

    @DisplayName("File with data without extension returns valid response")
    @Order(2)
    @SneakyThrows
    @Test
    void testFileWithDataWithoutExtensionReturnsValidResponse() {

        mockMvc.perform(multipart(RESOURCE).file(createMockFileWithDataWithExtension("Hello world & good morning. The date is 18/05/2016", ""))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Word Count = 9\n" +
                        "Average word length = 4.556\n" +
                        "Number of words of length 1 is 1\n" +
                        "Number of words of length 2 is 1\n" +
                        "Number of words of length 3 is 1\n" +
                        "Number of words of length 4 is 2\n" +
                        "Number of words of length 5 is 2\n" +
                        "Number of words of length 7 is 1\n" +
                        "Number of words of length 10 is 1\n" +
                        "The most frequently occurring word length is 2, for word lengths of 4,5"));
    }

    @DisplayName("File with formatted numbers returns valid response")
    @Order(3)
    @SneakyThrows
    @Test
    void testFileWithFormattedNumbersReturnsValidResponse() {

        mockMvc.perform(multipart(RESOURCE).file(createMockFileWithDataWithExtension("914,315,890", ".txt"))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Word Count = 1\n" +
                        "Average word length = 9.0\n" +
                        "Number of words of length 9 is 1\n" +
                        "The most frequently occurring word length is 1, for word lengths of 9"));
    }

    @DisplayName("File with decimal numbers returns valid response")
    @Order(4)
    @SneakyThrows
    @Test
    void testFileWithFormattedNumbersDecimalsReturnsValidResponse() {

        mockMvc.perform(multipart(RESOURCE).file(createMockFileWithDataWithExtension("9.890", ".txt"))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Word Count = 1\n" +
                        "Average word length = 4.0\n" +
                        "Number of words of length 4 is 1\n" +
                        "The most frequently occurring word length is 1, for word lengths of 4"));
    }

    @DisplayName("File with decimal numbers UK format returns valid response")
    @Order(5)
    @SneakyThrows
    @Test
    void testFileWithFormattedNumbersDecimalsUKFormatReturnsValidResponse() {

        mockMvc.perform(multipart(RESOURCE).file(createMockFileWithDataWithExtension("1,239,890.891", ".txt"))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Word Count = 1\n" +
                        "Average word length = 10.0\n" +
                        "Number of words of length 10 is 1\n" +
                        "The most frequently occurring word length is 1, for word lengths of 10"));
    }

    @DisplayName("File with decimal numbers INDIA format returns valid response")
    @Order(6)
    @SneakyThrows
    @Test
    void testFileWithFormattedNumbersDecimalsINDIAFormatReturnsValidResponse() {

        mockMvc.perform(multipart(RESOURCE).file(createMockFileWithDataWithExtension("12,39,890.891", ".txt"))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Word Count = 1\n" +
                        "Average word length = 10.0\n" +
                        "Number of words of length 10 is 1\n" +
                        "The most frequently occurring word length is 1, for word lengths of 10"));
    }

    @DisplayName("Empty File with Extension returns 204 No Content")
    @Order(7)
    @SneakyThrows
    @Test
    void testEmptyFileWithExtensionReturns204NoContent() {

        mockMvc.perform(multipart(RESOURCE).file(createMockFileWithDataWithExtension("", ".txt"))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isNoContent());
    }

    @DisplayName("Empty File without Extension returns 204 No Content")
    @Order(8)
    @SneakyThrows
    @Test
    void testEmptyFileWithOutExtensionReturns204NoContent() {

        mockMvc.perform(multipart(RESOURCE).file(createMockFileWithDataWithExtension("", ""))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isNoContent());
    }

    private MockMultipartFile createMockFileWithDataWithExtension(String data, String extension) {
        return new MockMultipartFile("fileName", "Synalogik-File" + extension, MediaType.TEXT_PLAIN_VALUE, data.getBytes());
    }
}
