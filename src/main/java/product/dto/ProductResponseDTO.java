package product.dto;

import lombok.Getter;
import product.Product;

/* 도메인 -> 외부 계층, 필요한 데이터만 전달 */
@Getter
public class ProductResponseDTO {
    private final String name;
    private final int price;
    private final String description;
    private final int stock;

    // 정적 팩토리로만 생성 가능
    private ProductResponseDTO(String name, int price, String description, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    // ProductManager에서 DTO 변환 로직 복잡도로 인해 책임 위임
    public static ProductResponseDTO from(Product product) {
        return new ProductResponseDTO(product.getName(), product.getPrice(), product.getDescription(), product.getStock());
    }
}
