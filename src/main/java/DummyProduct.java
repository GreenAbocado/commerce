import product.Category;
import product.dto.ProductCreateDTO;

import static product.Category.*;

public enum DummyProduct {

    ELECTRONIC_1("Galaxy S25", ELECTRONIC,1_200_000, "최신 안드로이드 스마트폰", 20),
    ELECTRONIC_2("iphone 16", ELECTRONIC,1_350_000, "Apple의 최신 스마트폰", 30),
    ELECTRONIC_3("MackBook Pro", ELECTRONIC,2_400_000, "M3 칩셉이 탑재된 노트북", 40),
    ELECTRONIC_4("Airpods Pro", ELECTRONIC,350_000, "노이즈 캔슬링 무선 이어폰", 50),

    CLOTH_1("의류 A", CLOTH,50_000, "A", 10),
    CLOTH_2("의류 B", CLOTH,1_000_000, "B", 20),
    CLOTH_3("의류 C", CLOTH,100_000, "C", 30),

    FOOD_1("음식 A", FOOD,10_000, "AA", 40),
    FOOD_2("음식 B", FOOD,100_000, "BB", 50);

    private final String name;
    private final Category category;
    private final int price;
    private final String description;
    private final int stock;

    DummyProduct(String name, Category category, int price, String description, int stock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public ProductCreateDTO createDTO() {
        return ProductCreateDTO.builder()
                    .name(name)
                    .category(category)
                    .price(price)
                    .description(description)
                    .stock(stock).build();

    }
}