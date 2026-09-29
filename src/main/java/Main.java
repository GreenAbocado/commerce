import product.*;
import java.util.*;

public class Main {  // 프로그램 시작 및 객체 의존 관계 설정
    public static void main(String[] args) {
        List<Product> productsList = new ArrayList<>();
        CommerceSystem cs = new CommerceSystem(productsList);

        cs.start();
    }
}