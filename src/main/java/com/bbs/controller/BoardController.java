package com.bbs.controller;

import com.bbs.model.Board;
import com.bbs.repository.BoardRepository;
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

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;

    @GetMapping("/board")
    public ResponseEntity<List<Board>> getAllBoard() {
        try {
            List<Board> boardList = boardRepository.findAll();

            if (boardList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(boardList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable("id") long id) {
        Optional<Board> boardData = boardRepository.findById(id);

        return boardData.map(board -> new ResponseEntity<>(board, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/board")
    public ResponseEntity<Board> createBoard(@RequestParam String title) {
        try {
            Board board = boardRepository.save(new Board(title));
            return new ResponseEntity<>(board, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") long id, String title) {
        Optional<Board> bordData = boardRepository.findById(id);

        if (!StringUtils.isEmpty(title)) {
            Board board = bordData.get();
            board.setTitle(title);
            return new ResponseEntity<>(boardRepository.save(board), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<HttpStatus> deleteBoard(@PathVariable("id") long id) {
        try {
            boardRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
