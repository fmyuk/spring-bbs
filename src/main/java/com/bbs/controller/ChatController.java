package com.bbs.controller;

import com.bbs.model.Chat;
import com.bbs.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatRepository chatRepository;

    @GetMapping("/chat/{board}")
    public ResponseEntity<List<Chat>> getChatByBoard(@PathVariable("board") long board, @RequestParam String title) {
        List<Chat> chatList = new ArrayList<>();
        chatList = chatRepository.findByBoard(board);
        if (CollectionUtils.isEmpty(chatList)) {
            chatList.add(new Chat(board, title, ""));
        }

        return new ResponseEntity<>(chatList, HttpStatus.OK);
    }

    @PostMapping("/chat")
    public ResponseEntity<Chat> createChat(@RequestParam long board, @RequestParam String title, @RequestParam String comment) {
        try {
            Chat chat = chatRepository.save(new Chat(board, title, comment));
            return new ResponseEntity<>(chat, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/chat/{id}")
    public ResponseEntity<List<Chat>> updateChat(@PathVariable("id") long id, @RequestParam long board, @RequestParam String comment) {
        Optional<Chat> chatData = chatRepository.findById(id);

        if (!StringUtils.isEmpty(comment)) {
            Chat chat = chatData.get();
            chat.setComment(comment);
            chatRepository.save(chat);


            return new ResponseEntity<>(chatRepository.findByBoard(board), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/chat/{id}")
    public ResponseEntity<List<Chat>> deleteChat(@PathVariable("id") long id, long board) {
        try {
            chatRepository.deleteById(id);
            return new ResponseEntity<>(chatRepository.findByBoard(board), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
