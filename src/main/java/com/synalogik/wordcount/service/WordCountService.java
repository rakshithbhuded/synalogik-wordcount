package com.synalogik.wordcount.service;

import com.synalogik.wordcount.helper.StringBuilderPlus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WordCountService {
    private static final String NUMBER_OF_WORDS = "Number of words of length ";
    private static final String WORD_IS = " is ";

    /**
     * Calculate the Word Count, Average Word Length etc
     *
     * @param fileName file to process the data
     * @return Formatted response
     * @throws Exception Exception if any
     */
    public Optional<String> getWordCountDetailsByFileName(MultipartFile fileName) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileName.getResource().getInputStream()));

            List<String> listOfStrings = new ArrayList<>();
            String singleLine;
            while ((singleLine = reader.readLine()) != null) {
                listOfStrings.add(singleLine);
            }

            if (listOfStrings.isEmpty()) {
                return Optional.empty();
            }
            List<String> list = new ArrayList<>();

            Arrays.stream(listOfStrings.toArray(new String[0])).map(s -> s.split(" "))
                    .collect(Collectors.toList()).forEach(strings -> list.addAll(Arrays.stream(strings)
                            .filter(s -> !(s.isEmpty() || s.isBlank())).map(s -> s.replaceAll("[,.]", ""))
                            .collect(Collectors.toList())));

            Map<Integer, List<String>> lengthToWords = new TreeMap<>(list.stream().collect(Collectors.groupingBy(String::length)));

            StringBuilderPlus stringBuilder = new StringBuilderPlus();

            stringBuilder.append("Word Count = ").appendLine(getWordCount(lengthToWords)).append("Average word length = ")
                    .appendLine(getAverageWordLength(lengthToWords));

            lengthToWords.forEach((key, value) -> stringBuilder.append(NUMBER_OF_WORDS).append(key).append(WORD_IS).appendLine(value.size()));

            long max = lengthToWords.values().stream().parallel().mapToLong(Collection::size).max().orElseThrow();
            stringBuilder.append("The most frequently occurring word length is ").append(max).append(", for word lengths of ")
                    .append(getFrequentlyUsedWordLength(lengthToWords, max).stream().map((Integer i) -> Integer.toString(i))
                            .collect(Collectors.joining(",")));

            return Optional.of(stringBuilder.toString());
        } catch (IOException io) {
            log.error("Synalogik Word-Count Details ::: ERROR ::: File Not Found Error - {}", io.getMessage());
            throw new IOException("Synalogik Application - Error while accessing file", io);
        } catch (Exception e) {
            log.error("Synalogik Word-Count Details ::: ERROR ::: {}", e.getMessage());
            throw new Exception("Synalogik Application Error ", e);
        }
    }

    /**
     * Calculate Word Count
     *
     * @param lengthToWords Input data
     * @return Word Count value
     */
    private long getWordCount(Map<Integer, List<String>> lengthToWords) {
        return lengthToWords.values().stream().parallel().mapToLong(Collection::size).sum();
    }

    /**
     * Calculate Average Word Length
     *
     * @param lengthToWords Input Data
     * @return Average Word Length
     */
    private double getAverageWordLength(Map<Integer, List<String>> lengthToWords) {
        double totalWords = lengthToWords.values().stream().parallel().map(List::size).reduce(Integer::sum).orElseThrow();
        double totalLength = lengthToWords.entrySet().stream().parallel().map(key -> key.getKey() * key.getValue().size())
                .reduce((Integer::sum)).orElseThrow();
        return withBigDecimal(totalLength / totalWords);
    }

    /**
     * Calculate Most Frequent used Words
     *
     * @param lengthToWords Input Data
     * @param max           Maximum count of word length
     * @return Total list of maximum count of word length
     */
    private List<Integer> getFrequentlyUsedWordLength(Map<Integer, List<String>> lengthToWords, long max) {
        return lengthToWords.entrySet().stream().filter(entry -> entry.getValue().size() == max).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * Round up the value to 3 decimal places
     *
     * @param value input value
     * @return Rounded double value to 3 decimal places
     */
    private static double withBigDecimal(double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
