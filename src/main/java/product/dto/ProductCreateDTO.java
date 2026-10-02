package product.dto;

import lombok.Builder;
import product.Category;

/* 외부 계층 -> 도메인, 필요한 데이터만 전달 및 입력 검증
   price의 경우 검증에서 값이 없는 상태인지를 구분하기 위해 래퍼 사용 */
@Builder
public record ProductCreateDTO(String name, Category category, Integer price, String description, int stock) {


    // 각 요청의 입력에 대해 1차 유효성 검증 필요
}