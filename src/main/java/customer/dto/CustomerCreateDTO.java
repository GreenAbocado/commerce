package customer.dto;

import lombok.Builder;

/* 필요 데이터 1차 유효성 검증 후 전달
   빌더로 클라이언트측 가독성 향상 */
@Builder
public record CustomerCreateDTO(String userName, String password, String name) {
    public CustomerCreateDTO {
        if (userName == null) { throw new IllegalArgumentException(); }
        if (password == null) { throw new IllegalArgumentException(); }
        if (name == null) { throw new IllegalArgumentException(); }
    }
}