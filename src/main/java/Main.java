import product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {    // 상품 목록 출력
        List<Product> productsList = new ArrayList<>();
        initDummyProducts(productsList);

        Scanner sc = new Scanner(System.in);

        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        do {
            for (int i = 0; i < productsList.size(); i++) {
                System.out.println(formatProduct(i+1, productsList.get(i)));
            }
            System.out.println("0. 종료");
        } while(sc.nextInt() != 0);
        System.out.print("커머스 플랫폼을 종료합니다.");
    }

    private static void initDummyProducts(List<Product> productsList) {
        productsList.add(new Product("Galaxy S25", 1_200_000, "최신 안드로이드 스마트폰"));
        productsList.add(new Product("iphone 16", 1_350_000, "Apple의 최신 스마트폰"));
        productsList.add(new Product("MackBook Pro", 2_400_000, "M3 칩셉이 탑재된 노트북"));
        productsList.add(new Product("Airpods Pro", 350_000, "노이즈 캔슬링 무선 이어폰"));
    }

    private static String formatProduct(int order, Product product) {
        return String.format("%d. %-14s | %,10d원 | %s", order, product.getName(), product.getPrice(),product.getDescription());
    }
}
