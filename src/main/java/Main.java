import product.*;
import java.util.*;

/* 객체 초기화 및 의존 관계 설정 */
public class Main {
    public static void main(String[] args) {
        ProductManager productManager = new ProductManager();

        // 더미 상품 삽입
        Arrays.stream(DummyProduct.values())
                .forEach((dummy)-> productManager.addProduct(dummy.createDTO()));

        CommerceSystem cs = new CommerceSystem(productManager);

        cs.start();
    }
}