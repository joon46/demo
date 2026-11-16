package com.example.demo.service;

import com.example.demo.domain.Board;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//게시글 등록 시 검증 로직, 조회수 증가, 수정/삭제 권한 확인 등 핵심 비즈니스 로직을 작성

@Service
@RequiredArgsConstructor // Repository를 주입받기 위한 Lombok 어노테이션
public class BoardService {

    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호

    // 1. 글 작성
    public Board saveBoard(Board board) {
        board.setPassword(passwordEncoder.encode(board.getPassword()));
        return boardRepository.save(board);
    }

    // 2. 글 목록 조회
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    // 3. 글 상세 조회
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    // 4. 글 삭제
    // 4. 글 삭제 (작성자 본인 확인 로직 추가)
    public boolean deleteBoard(Long id, String rawPassword) {
        Board board = boardRepository.findById(id).orElse(null);
        
        // 게시글이 없거나, 입력한 비밀번호가 암호화된 DB 비밀번호와 다르면 거부
        if (board == null || board.getPassword() == null || !passwordEncoder.matches(rawPassword, board.getPassword())) {
            return false;
        }
        
        boardRepository.deleteById(id);
        return true;
    }
}