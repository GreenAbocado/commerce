package product;

import java.util.*;

public class CommerceSystem {   // 상품 관리 및 입출력
    private final List<Product> products;

    public void start() {
        Scanner sc = new Scanner(System.in);

        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        do {
            for (int i = 0; i < products.size(); i++) {
                System.out.println(formatProduct(i+1, products.get(i)));
            }
            System.out.println("0. 종료");
        } while(sc.nextInt() != 0);
        System.out.print("커머스 플랫폼을 종료합니다.");
    }

    private static String formatProduct(int order, Product product) {
        return String.format("%d. %-14s | %,10d원 | %s", order, product.getName(), product.getPrice(),product.getDescription());
    }

    // 생성 시 상품 저장소 DI 및 더미데이터 추가
    public CommerceSystem(List<Product> products) {
        this.products = products;

        products.add(new Product("Galaxy S25", 1_200_000, "최신 안드로이드 스마트폰"));
        products.add(new Product("iphone 16", 1_350_000, "Apple의 최신 스마트폰"));
        products.add(new Product("MackBook Pro", 2_400_000, "M3 칩셉이 탑재된 노트북"));
        products.add(new Product("Airpods Pro", 350_000, "노이즈 캔슬링 무선 이어폰"));
    }
}
