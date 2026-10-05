package product.dto;

import lombok.Builder;
import product.Category;

import static exception.ExceptionMessage.*;

/* 상품 생성 시 필요 데이터 1차 유효성 검증 및 전달
   price의 경우 빌더 누락으로 인해 값이 없는 상태를 구분하기 위해 래퍼 사용 */
@Builder
public record ProductCreateDTO(String name, Category category, Integer price, String description, int stock) {

    /* 필드를 사용하는 측에서 NPE가 안 터질 정도로만 검증 */
    public ProductCreateDTO {
        if (name == null) { throw new IllegalArgumentException(INVALID_NAME_INPUT); }
        if (category == null) { throw new IllegalArgumentException(NONE_CATEGORY_INPUT); }
        if (price == null) { throw new IllegalArgumentException(INVALID_PRICE_INPUT); }
        if (description == null) { throw new IllegalArgumentException(NONE_DESCRIPTION_INPUT); }
    }
}