package product.dto;

import product.Category;

/* 콘솔 -> 컨트롤러 반환 시 사용 */
public record ProductSearchDTO(Category category, String name) {
}
