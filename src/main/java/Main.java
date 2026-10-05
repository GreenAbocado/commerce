import io.Input;
import product.*;
import java.util.*;

/* 객체 초기화 및 의존 관계 설정 */
public class Main {
    public static void main(String[] args) {
        // LinkedList + HashMap 사용으로 순차 확인 가능 + 단건 조회 등 O(1)
        Map<Long, Product> map = new LinkedHashMap<>();

        ProductRepository productRepository = new ProductRepository(map);
        ProductService productService = new ProductService(productRepository);

        // 더미 상품 삽입
        Arrays.stream(DummyProduct.values())
                .forEach((dummy)-> productService.addProduct(dummy.createDTO()));
        Input input = new Input(System.in);
        CommerceSystem cs = new CommerceSystem(productService, input);

        cs.start();
    }
}