import product.*;
import java.util.*;

/* 객체 초기화 및 의존 관계 설정 */
public class Main {
    public static void main(String[] args) {
        List<Category> categoryList = initCategoryList();

        CommerceSystem cs = new CommerceSystem(categoryList);

        cs.start();
    }

    private static List<Category> initCategoryList() {

        Category electronic = new Category("전자제품");
        electronic.addProduct("Galaxy S25", 1_200_000, "최신 안드로이드 스마트폰", 20);
        electronic.addProduct("iphone 16", 1_350_000, "Apple의 최신 스마트폰", 30);
        electronic.addProduct("MackBook Pro", 2_400_000, "M3 칩셉이 탑재된 노트북", 40);
        electronic.addProduct("Airpods Pro", 350_000, "노이즈 캔슬링 무선 이어폰", 50);

        Category cloth = new Category("의류");
        cloth.addProduct("A - 의류", 50_000, "A", 10);
        cloth.addProduct("B - 의류", 1_000_000, "B", 20);
        cloth.addProduct("C - 의류", 100_000, "C", 30);

        Category food = new Category("음식");
        food.addProduct("A - 음식", 10_000, "AA", 40);
        food.addProduct("B - 음식", 100_000, "BB", 50);

        return List.of(electronic, cloth, food);
    }
}