package product;

import product.dto.*;
import java.util.*;
import static exception.ExceptionMessage.*;

/* 카테고리 기반 상품 관리 (비즈니스 로직 + 저장소 로직) */
public class ProductManager {

    // EnumMap을 통해, 정의한 Category순으로 순서를 매겨 엔트리 정렬
    private final Map<Category, Map<String, Product>> store = new EnumMap<>(Category.class);

    public ProductManager() {
        store.keySet()                 // LinkedList + HashMap 사용으로 순차 확인 가능 + 중복 체크 및 단건 조회 등 O(1)
                .forEach((key) -> store.put(key, new LinkedHashMap<>()));
    }

    /* 순수한 상품 관리가 아닌 카테고리와 결합하여 상품관리만을 확장성 있게 사용 X
       -> 이후 카테고리와 상품 관리 분리 예정 */

    /* 비즈니스 로직 */

    public void addProduct(ProductCreateDTO dto) {
        if (findByCategoryAndName(dto.category(), dto.name()).isPresent()) {
            throw new IllegalArgumentException(PRODUCT_ALREADY_EXIST);
        }

        saveProduct(Product.builder()
                        .name(dto.name())
                        .category(dto.category())
                        .price(dto.price())
                        .description(dto.description())
                        .stock(dto.stock()).build());
    }

    public ProductResponseDTO getProductByCategoryAndName(Category category, String name) {
        Product product = findByCategoryAndName(category, name)
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_EXIST));

        return ProductResponseDTO.from(product);
    }

    public List<ProductResponseDTO> getAllByCategory(Category category) {
        return findAllByCategory(category).stream()
                    .map(ProductResponseDTO::from)
                    .toList();
    }

    /* 부분 변경(PATCH) 입력 해석 후 도메인에 위임 (null : 변경하지 않음) */
    public void updateProductInfo(ProductUpdateDTO dto) {
        Product product = findByCategoryAndName(dto.category(), dto.name())
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_EXIST));

        int price = (dto.price() != null ? dto.price() : product.getPrice());
        String description = (dto.description() != null ? dto.description() : product.getDescription());

        product.updateInfo(price, description);
    }

    /* 재고 증가/감소 해석 후 도메인에 위임 */
    public void adjustStock(Category category, String name, int amount) {
        Product product = findByCategoryAndName(category, name)
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_EXIST));

        // 0일 경우 도메인에서 검증 후 예외
        if (amount > 0) { product.increaseStock(amount); }
        else { product.decreaseStock(amount); }
    }

    public void deleteProductByCategoryAndName(Category category, String name) {
        Product product = findByCategoryAndName(category, name)
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_EXIST));

        removeProduct(product);
    }


    /* 상품 저장소 관리 / 이후 클래스 무거워질 경우 분리 */

    /* 카테고리는 상수이고 null은 입력에서 검증하므로 따로 존재 여부 확인 X */

    private void saveProduct(Product product) {
        store.get(product.getCategory()).put(product.getName(), product);
    }

    private Optional<Product> findByCategoryAndName(Category category, String name) {
        return Optional.ofNullable(store.get(category).get(name));
    }

    private List<Product> findAllByCategory(Category category) {
        return new ArrayList<>(store.get(category).values());
    }

    private void removeProduct(Product product) {
        store.get(product.getCategory()).remove(product.getName());
    }
}