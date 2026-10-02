package product.dto;

import lombok.Builder;
import product.Category;

/* 외부 계층 -> 도메인, 필요한 데이터만 전달 및 입력 검증
   price의 경우 null 유무로 선택 변경이므로 구분하기 위해 래퍼 사용 */
@Builder
public record ProductUpdateDTO(String name, Category category, Integer price, String description) {


    // 각 요청의 입력에 대해 1차 유효성 검증 필요
}