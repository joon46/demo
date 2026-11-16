package com.example.demo.controller;

import com.example.demo.domain.Board;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 1. 전체 게시글 조회 (GET /api/boards)
    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.findAllBoards();
    }

    // 2. 게시글 상세 조회 (GET /api/boards/1)
    @GetMapping("/{id}")
    public Board getBoardById(@PathVariable("id") Long id) {
        return boardService.findBoardById(id);
    }

    // 3. 게시글 작성 (POST /api/boards)
    @PostMapping
    public Board createBoard(@Valid @RequestBody Board board) {
        return boardService.saveBoard(board);
    }

    // 4. 게시글 삭제 (DELETE /api/boards/1?writer=홍길동)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(
            @PathVariable("id") Long id,
            @RequestBody Map<String, String> payload) {

        String password = payload.get("password");
        if (password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body("비밀번호를 입력해주세요.");
        }

        boolean isDeleted = boardService.deleteBoard(id, password);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("비밀번호가 일치하지 않거나 존재하지 않는 게시글입니다.");
        }
        return ResponseEntity.ok("삭제되었습니다.");
    }
}