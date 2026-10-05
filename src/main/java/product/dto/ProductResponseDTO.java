package product.dto;

import lombok.Builder;
import lombok.Getter;
import product.Product;

/* 상품 조회 후 외부 계층으로 필요 데이터만 전달
   빌더를 통해 생성 시 가독성 향상 */
@Builder
public record ProductResponseDTO(Long id, String name, int price, String description, int stock) {
}