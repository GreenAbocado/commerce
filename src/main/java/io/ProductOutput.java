package io;

import product.*;
import product.dto.ProductResponseDTO;
import java.util.*;

/* 상품 관련 출력 포맷팅 및 출력 */
public class ProductOutput {

    /* 출력 */
    private static void print(String s) {
        System.out.print(s);
    }

    /* 상품 출력문 포맷팅 */


    // 특정 카테고리 상품 리스트 포맷팅
    public static void printProducts(Category category, List<ProductResponseDTO> list) {
        print(String.format("""
                [ %s 카테고리 ]
                %s0. 뒤로가기
                """, category.getName(), productsToStr(list))
        );
    }

    // 특정 상품 포맷팅
    public static void printProduct(ProductResponseDTO dto) {
        print(String.format("""
                선택한 상품: %s | %,d원 | %s | 재고: %d개
                """, dto.name(), dto.price(), dto.description(), dto.stock())
        );
    }


    /* 동적 상품 출력문 생성 */

    // 카테고리 종류 출력문 생성  ex) {1. 전자제품  2. 의류 ... }
    public static String categoriesToStr() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= Category.categories.length; i++) {
            sb.append(String.format("%d. %s\n", i, Category.categories[i-1].getName()));
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }

    // 상품 리스트 출력문 생성  ex) {1. 갤럭시 S25 | 1,500,000원 | 삼성 스마트폰 ... }
    private static String productsToStr(List<ProductResponseDTO> list) {
        StringBuilder sb = new StringBuilder();
        int idx = 0;

        for (ProductResponseDTO dto : list) {
            sb.append(String.format("%d. %-14s | %,10d원 | %-14s\n",
                    ++idx, dto.name(), dto.price(), dto.description()));
        }
        return sb.toString();
    }

    private ProductOutput() {}
}