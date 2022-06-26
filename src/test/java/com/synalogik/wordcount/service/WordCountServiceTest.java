package com.synalogik.wordcount.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

@DisplayName("Word-Count Controller Tests")
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class WordCountServiceTest {

    @Autowired
    private WordCountService wordCountService;

    @DisplayName("File with data returns valid response")
    @Order(1)
    @SneakyThrows
    @Test
    void testFileWithDataReturnsValidResponse() {
        String expected = "Word Count = 9\n" +
                "Average word length = 4.556\n" +
                "Number of words of length 1 is 1\n" +
                "Number of words of length 2 is 1\n" +
                "Number of words of length 3 is 1\n" +
                "Number of words of length 4 is 2\n" +
                "Number of words of length 5 is 2\n" +
                "Number of words of length 7 is 1\n" +
                "Number of words of length 10 is 1\n" +
                "The most frequently occurring word length is 2, for word lengths of 4,5";

        Optional<String> actual = wordCountService.getWordCountDetailsByFileName(createMockFileWithDataWithExtension("Hello world & good morning. The date is 18/05/2016", ".txt"));
        if (actual.isPresent())
            Assertions.assertEquals(expected, actual.get());
        else
            Assertions.fail();
    }

    @DisplayName("File with data without extension returns valid response")
    @Order(2)
    @SneakyThrows
    @Test
    void testFileWithDataWithoutExtensionReturnsValidResponse() {
        String expected = "Word Count = 9\n" +
                "Average word length = 4.556\n" +
                "Number of words of length 1 is 1\n" +
                "Number of words of length 2 is 1\n" +
                "Number of words of length 3 is 1\n" +
                "Number of words of length 4 is 2\n" +
                "Number of words of length 5 is 2\n" +
                "Number of words of length 7 is 1\n" +
                "Number of words of length 10 is 1\n" +
                "The most frequently occurring word length is 2, for word lengths of 4,5";

        Optional<String> actual = wordCountService.getWordCountDetailsByFileName(createMockFileWithDataWithExtension("Hello world & good morning. The date is 18/05/2016", ""));
        if (actual.isPresent())
            Assertions.assertEquals(expected, actual.get());
        else
            Assertions.fail();
    }

    @DisplayName("File with formatted numbers returns valid response")
    @Order(3)
    @SneakyThrows
    @Test
    void testFileWithFormattedNumbersReturnsValidResponse() {
        String expected = "Word Count = 1\n" +
                "Average word length = 9.0\n" +
                "Number of words of length 9 is 1\n" +
                "The most frequently occurring word length is 1, for word lengths of 9";

        Optional<String> actual = wordCountService.getWordCountDetailsByFileName(createMockFileWithDataWithExtension("914,315,890", ".txt"));
        if (actual.isPresent())
            Assertions.assertEquals(expected, actual.get());
        else
            Assertions.fail();
    }

    @DisplayName("File with decimal numbers returns valid response")
    @Order(4)
    @SneakyThrows
    @Test
    void testFileWithFormattedNumbersDecimalsReturnsValidResponse() {
        String expected = "Word Count = 1\n" +
                "Average word length = 4.0\n" +
                "Number of words of length 4 is 1\n" +
                "The most frequently occurring word length is 1, for word lengths of 4";

        Optional<String> actual = wordCountService.getWordCountDetailsByFileName(createMockFileWithDataWithExtension("9.890", ".txt"));
        if (actual.isPresent())
            Assertions.assertEquals(expected, actual.get());
        else
            Assertions.fail();
    }

    @DisplayName("File with decimal numbers UK format returns valid response")
    @Order(5)
    @SneakyThrows
    @Test
    void testFileWithFormattedNumbersDecimalsUKFormatReturnsValidResponse() {
        String expected = "Word Count = 1\n" +
                "Average word length = 10.0\n" +
                "Number of words of length 10 is 1\n" +
                "The most frequently occurring word length is 1, for word lengths of 10";

        Optional<String> actual = wordCountService.getWordCountDetailsByFileName(createMockFileWithDataWithExtension("1,239,890.891", ".txt"));
        if (actual.isPresent())
            Assertions.assertEquals(expected, actual.get());
        else
            Assertions.fail();
    }

    @DisplayName("File with decimal numbers INDIA format returns valid response")
    @Order(6)
    @SneakyThrows
    @Test
    void testFileWithFormattedNumbersDecimalsINDIAFormatReturnsValidResponse() {
        String expected = "Word Count = 1\n" +
                "Average word length = 10.0\n" +
                "Number of words of length 10 is 1\n" +
                "The most frequently occurring word length is 1, for word lengths of 10";

        Optional<String> actual = wordCountService.getWordCountDetailsByFileName(createMockFileWithDataWithExtension("12,39,890.891", ".txt"));
        if (actual.isPresent())
            Assertions.assertEquals(expected, actual.get());
        else
            Assertions.fail();
    }

    @DisplayName("Empty File with Extension returns 204 No Content")
    @Order(7)
    @SneakyThrows
    @Test
    void testEmptyFileWithExtensionReturns204NoContent() {
        Optional<String> actual = wordCountService.getWordCountDetailsByFileName(createMockFileWithDataWithExtension("", ".txt"));
        Assertions.assertTrue(actual.isEmpty());
    }

    @DisplayName("Empty File without Extension returns 204 No Content")
    @Order(8)
    @SneakyThrows
    @Test
    void testEmptyFileWithOutExtensionReturns204NoContent() {
        Optional<String> actual = wordCountService.getWordCountDetailsByFileName(createMockFileWithDataWithExtension("", ""));
        Assertions.assertTrue(actual.isEmpty());
    }

    private MockMultipartFile createMockFileWithDataWithExtension(String data, String extension) {
        return new MockMultipartFile("fileName", "Synalogik-File" + extension, MediaType.TEXT_PLAIN_VALUE, data.getBytes());
    }
}