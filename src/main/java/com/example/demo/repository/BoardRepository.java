package com.example.demo.repository;

import com.example.demo.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

// DB에 접근해 데이터를 조회, 저장, 수정, 삭제(CRUD)하는 인터페이스나 메서드를 만듦

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 아무것도 적지 않아도 아래 메서드들을 기본 제공한다!
    // - save(board)   : 글 저장 / 수정
    // - findById(id)  : 글 단건 조회
    // - findAll()     : 글 전체 목록 조회
    // - deleteById(id): 글 삭제
}