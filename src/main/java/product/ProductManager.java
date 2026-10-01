package product;

import java.util.*;
import static exception.ExceptionMessage.*;

/* 카테고리별 상품 관리 */
public class ProductManager {

    /* 저장소 필드 */


    /* 비즈니스 로직 */

    public void addProduct(String name, int price, String description, int stock) {
        if (findByName(name).isPresent()) { throw new IllegalArgumentException(PRODUCT_ALREADY_EXIST); }
        saveProduct(new Product(name, price, description, stock));
    }

    public Product getProductByName(String name) {
        return findByName(name).orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_EXIST));
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
