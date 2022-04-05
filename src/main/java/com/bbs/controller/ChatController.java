package com.bbs.controller;

import com.bbs.model.Chat;
import com.bbs.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatRepository chatRepository;

    @GetMapping("/chat/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable("id") long id) {
        Optional<Chat> chatData = chatRepository.findById(id);

        return chatData.map(chat -> new ResponseEntity<>(chat, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/chat")
    public ResponseEntity<Chat> createChat(@RequestParam long boardId, @RequestParam String title, @RequestParam String comment) {
        try {
            Chat chat = chatRepository.save(new Chat(boardId, title, comment));
            return new ResponseEntity<>(chat, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/chat/{id}")
    public ResponseEntity<Chat> updateChat(@PathVariable("id") long id, @RequestParam String comment) {
        Optional<Chat> chatData = chatRepository.findById(id);

        if (!StringUtils.isEmpty(comment)) {
            Chat chat = chatData.get();
            chat.setComment(comment);

            return new ResponseEntity<>(chatRepository.save(chat), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/chat/{id}")
    public ResponseEntity<HttpStatus> deleteChat(@PathVariable("id") long id) {
        try {
            chatRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
