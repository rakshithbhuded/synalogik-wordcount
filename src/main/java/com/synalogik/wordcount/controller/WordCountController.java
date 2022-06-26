package com.synalogik.wordcount.controller;

import com.synalogik.wordcount.service.WordCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(WordCountController.RESOURCE)
@Slf4j
public class WordCountController {
    public static final String RESOURCE = "/word-count";

    @Autowired
    private WordCountService wordCountService;

    /**
     * Synalogik Word-Count Details
     *
     * @param fileName File Name
     * @return Word-Count details for the requested file.
     */
    @PostMapping
    public ResponseEntity<String> getWordCountDetailsByFileName(@RequestParam("fileName") MultipartFile fileName) {
        log.debug("Synalogik Word-Count Details ::: File Name - {} ", fileName.getOriginalFilename());
        try {
            return wordCountService.getWordCountDetailsByFileName(fileName)
                    .map(ResponseEntity::ok)
                    .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (IOException io) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
