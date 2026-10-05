package product.dto;

import lombok.Builder;

/* 상품 수정 시 필요 데이터 1차 유효성 검증 및 전달 */
@Builder
public record ProductUpdateDTO(Long id, Integer price, String description, Integer stock) {

    /* 필드를 사용하는 측에서 NPE가 안 터질 정도로만 검증 */
    public ProductUpdateDTO {
        if (id == null) { throw new IllegalArgumentException("식별자가 입력되지 않았습니다"); }

        // price, description, stock은 null 가능 (null : 변경 안함)
    }
}