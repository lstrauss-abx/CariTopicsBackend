package com.openclassrooms.helloworld.controller;

import com.openclassrooms.helloworld.model.TopicFile;
import com.openclassrooms.helloworld.service.TopicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TopicFileController {

    @Autowired
    private TopicFileService fileService;

    /**
     * Create - Add a new file
     * @param file An object file
     * @return The file object saved
     */
    @PostMapping("/file")
    public TopicFile createTopicFile(@RequestBody TopicFile file) {
        return fileService.saveTopicFile(file);
    }

    /**
     * Read - Get one specific file
     * @param id The id of the file
     */
    @GetMapping("/topic{topicID}/file{id}")
    public TopicFile getTopicFile(@PathVariable("id") final Long id) {
        Optional<TopicFile> topicFile = fileService.getTopicFileById(id);
        return topicFile.orElse(null);
    }

    /**
     * Update - update an existing comment
     * @param id The id of the comment
     */
    @PutMapping("/topic{topicID}/file{id}")
    public TopicFile updateTopicFile(@PathVariable("id") final Long id, @RequestBody TopicFile file) {
        //Va chercher le file à modifier grâce à l'id
        Optional<TopicFile> f = fileService.getTopicFileById(id);
        if (f.isPresent()) {
            TopicFile currentFile = f.get();

            /**
             * Vérifie les modifs du @param file
             * et change les anciennes données où il y a besoin
             */
            String fileTitle = file.getFile_title();
            if (fileTitle != null) {
                currentFile.setFile_title(fileTitle);
            }
            String fileContent = file.getFile_content();
            if (fileContent != null) {
                currentFile.setFile_content(fileContent);
            }
            String fileType = file.getFile_type();
            if (fileType != null) {
                currentFile.setFile_type(fileType);
            }
            String fileDate = file.getFile_date();
            if (fileDate != null) {
                currentFile.setFile_date(fileDate);
            }

            //sauvegarde les modifications
            fileService.saveTopicFile(currentFile);
            return currentFile;
        } else {
            return null;
        }
    }

    /**
     * Delete - delete a file
     * @param id
     */
    @DeleteMapping("/topic{topicID}/file{id}")
    public void deleteTopicFile(@PathVariable("id") final Long id) {
        fileService.deleteTopicFile(id);
    }

    /**
     * Read - all files related to a topic
     * @param topicID - The id of the related topic
     */
    @GetMapping("/topic{topicID}/files")
    public Optional<TopicFile> getAllTopicFileForTopic(@PathVariable("topicID") final Long topicID) {
        return fileService.getTopicFilesByTopicId(topicID);
    }

    /**
     * Delete - all files related to a topic
     * @param topicID - The id of the related topic
     */
    @DeleteMapping("/topic{topicID}/files")
    public void deleteAllTopicFileForTopic(@PathVariable("topicID") final Long topicID) {
        fileService.deleteTopicFile(topicID);
    }

}

