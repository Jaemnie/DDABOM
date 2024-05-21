package com.example.team;

// 자격증 아이템 클래스
public class CertificationItem {

    // 자격증 제목을 저장할 변수
    private String title;
    // 자격증 설명을 저장할 변수
    private String description;

    // 생성자: 자격증 제목과 설명을 초기화
    public CertificationItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // 자격증 제목을 반환하는 메서드
    public String getTitle() {
        return title;
    }

    // 자격증 설명을 반환하는 메서드
    public String getDescription() {
        return description;
    }
}
