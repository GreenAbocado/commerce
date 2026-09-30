package product;

import lombok.Getter;
import java.util.*;

/* 상품 비즈니스 로직 수행 및 저장소 관리, 카테고리별 ProductManager */
public class Category {
    @Getter
    private final String name;
    private final List<Product> list = new ArrayList<>();   // 상품 저장소는 정의된 메서드로만 접근 가능

    public Category(String name) {
        this.name = name;
    }


    /* 비즈니스 로직 */

    public void addProduct(String name, int price, String description, int stock) {
        if (findByName(name).isPresent()) { throw new IllegalArgumentException("상품이 이미 존재합니다"); }
        saveProduct(new Product(name, price, description, stock));
    }

    public Product getProductByName(String name) {
        return findByName(name).orElseThrow(() -> new IllegalArgumentException("해당 이름을 갖는 상품이 없습니다."));
    }

    public List<Product> getAllProducts() {
        return findAll();
    }

    public void updateProduct(String name, Integer price, String description) {
        Product product = getProductByName(name);
        product.update(price, description);
    }

    public void deleteProduct(String name) {
        removeProduct(getProductByName(name));
    }


    /* 상품 저장소 관리 / 이후 클래스 무거워질 경우 분리 */

    private void saveProduct(Product product) {
        list.add(product);
    }

    private Optional<Product> findByName(String name) {
        return list.stream().filter((product) -> product.getName().equals(name))
                    .findFirst();
    }

    private List<Product> findAll() {
        return new ArrayList<>(list);   // 원본 데이터 보호를 위해 새로 생성하여 반환
    }

    private void removeProduct(Product product) {
        list.remove(product);
    }
}