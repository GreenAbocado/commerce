package product.io;

import lombok.RequiredArgsConstructor;
import product.Category;
import product.dto.ProductResponseDTO;

import java.io.PrintStream;
import java.util.*;

/* 상품 관련 출력 포맷팅 및 출력 */
@RequiredArgsConstructor
public class ProductOutput {

    private final PrintStream printer;

    public static final String INPUT_NAME = "상품명을 입력해주세요: ";
    public static final String INPUT_PRICE = "가격을 입력해주세요: ";
    public static final String INPUT_DESCRIPTION = "상품 설명을 입력해주세요: ";
    public static final String INPUT_STOCK = "재고 수량을 입력해주세요: ";
    public static final String CREATE_SUCCESS = "상품이 성공적으로 추가되었습니다!\n";
    public static final String DELETE_SUCCESS = "상품이 성공적으로 삭제되었습니다!\n";
    public static final String DELETE_CONFIRM = """
            정말 삭제하시겠습니까?
            1. 확인       2. 취소
            """;

    private static final String CATEGORY_LIST = categoriesToString();

    /* 출력 */
    public void print(String s) {
        printer.print(s);
    }


    /* 사용자에게 카테고리 출력 */
    public void printCategoryProducts(Category category, List<ProductResponseDTO> list) {
        print(String.format("""
                [ %s 카테고리 ]
                %s0. 뒤로가기
                """, category.getName(), productsToString(list))
        );
    }

    /* 사용자가 선택한 상품 출력 */
    public void printProduct(ProductResponseDTO dto) {
        print(String.format("""
                선택한 상품: %s | %,d원 | %s | 재고: %d개
                """, dto.name(), dto.price(), dto.description(), dto.stock())
        );
    }



    /* 관리자의 상품 관리 메뉴 출력 */
    public void printManageMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ 관리자 모드 ]\n");

        Arrays.stream(ProductManageMenu.MENU_ARR).forEach((menu) -> {
            sb.append(String.format("%d. %s\n", menu.getMenuNum(), menu.getMenuName()));
        });
        print(sb.toString());
    }


    /* 상품 관리를 위해 카테고리 종류 출력 */
    public void printCategory() {
        print("카테고리를 선택해주세요.\n" + CATEGORY_LIST );
    }


    /* 상품 관리 확인을 위해 선택한 카테고리 출력 */
    public void printSelectedCategory(Category category) {
        print(String.format("[ %s 카테고리 선택 ]\n", category.getName()));
    }


    /* Create: 상품 추가를 위한 최종 확인 출력 */
    public void printCreateConfirm(String name, int price, String description, int stock) {
        print(String.format("""
                %s | %,d원 | %s | 재고: %d개
                위 정보로 상품을 추가하시겠습니까?
                1. 확인       2. 취소
                """, name, price, description, stock));
    }

    /* Update: 수정할 항목 현재 정보 출력 */
    public void printCurrentInfo(ProductResponseDTO dto) {
        print(String.format("현재 상품 정보: %s | %,d원 | %s | 재고: %d개\n",
                dto.name(), dto.price(), dto.description(), dto.stock()));
    }

    /* Update: 변경 사항 출력 */
    public void printUpdateSuccess(ProductResponseDTO before, ProductResponseDTO after) {
        StringBuilder sb = new StringBuilder();

        if (before.price() != after.price()) { sb.append(String.format("%s의 가격이 %,d원 -> %,d원으로 수정되었습니다.\n",
                before.name(), before.price(), after.price())); }

        if (!before.description().equals(after.description())) { sb.append(
                String.format("%s의 설명이\n변경 전 : %s\n변경 후 : %s\n로 수정되었습니다.\n"
                        , before.name(), before.description(), after.description())); }

        if (before.stock() != after.stock()) { sb.append(String.format("%s의 재고가 %d개 -> %d개로 수정되었습니다.\n",
                before.name(), before.stock(), after.stock())); }

        print(sb.toString());
    }


    private static String categoriesToString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= Category.categories.length; i++) {
            sb.append(String.format("%d. %s\n", i, Category.categories[i-1].getName()));
        }
        return sb.toString();
    }


    // 상품 리스트 출력문 생성  ex) {1. 갤럭시 S25 | 1,500,000원 | 삼성 스마트폰 ... }
    private String productsToString(List<ProductResponseDTO> list) {
        StringBuilder sb = new StringBuilder();
        int idx = 0;

        for (ProductResponseDTO dto : list) {
            sb.append(String.format("%d. %-14s | %,10d원 | %-14s\n",
                    ++idx, dto.name(), dto.price(), dto.description()));
        }
        return sb.toString();
    }
}