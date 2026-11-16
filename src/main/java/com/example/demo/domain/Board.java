package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

//게시글 하나에 들어갈 정보(글 번호, 제목, 내용, 작성자, 작성일 등)를 담는 자바 클래스

@Entity                  // JPA가 관리하는 데이터베이스 테이블과 매핑되는 객체임을 선언
@Getter                // 모든 필드의 Getter 메서드 자동 생성
@Setter                // 모든 필드의 Setter 메서드 자동 생성
@NoArgsConstructor     // 파라미터가 없는 기본 생성자 자동 생성
@AllArgsConstructor    // 모든 필드를 파라미터로 받는 생성자 자동 생성
@Builder               // 객체 생성을 안전하고 편리하게 해주는 빌더 패턴 
public class Board {

    @Id                  // Primary Key (기본키) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment (자동으로 글 번호 증가)
    private Long id; // 글 번호

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
    private String title; // 제목


    @NotBlank(message = "내용은 필수 입력값입니다.")
    @Size(max = 2000, message = "내용은 2000자를 초과할 수 없습니다.")
    private String content;

    @NotBlank(message = "작성자는 필수 입력값입니다.")
    @Size(max = 50, message = "작성자는 50자를 초과할 수 없습니다.")
    private String writer;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 응답 JSON에서 비밀번호 숨김 (보안 조치)
    private String password;


    
    
}
