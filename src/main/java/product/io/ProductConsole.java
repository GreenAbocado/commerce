package product.io;

import io.CommonInput;
import lombok.RequiredArgsConstructor;
import product.Category;
import product.dto.*;
import java.util.*;
import static product.io.ProductOutput.*;


/* 상품과 관련된 입출력 처리 위임 및 입력 데이터 가공 수행, ProductController 복잡도로 분리 */
/* Optional을 반환하여 사용자 의도 전달 (빈 Optional : 취소) */
@RequiredArgsConstructor
public class ProductConsole {
    private final CommonInput input;
    private final ProductOutput output;

    /* 사용자 상품 선택 입출력 */

    public Optional<ProductResponseDTO> userSelectProduct(Category category, List<ProductResponseDTO> list) {
        printCategoryProducts(category, list);

        /* input에서 정상 범위 입력까지 반복 */
        int productNum = input.readNumValidate(0, list.size());

        if (productNum == 0) { return Optional.empty(); }

        return Optional.of(list.get(productNum-1));
    }

    public void printUserSelectProduct(ProductResponseDTO product) {
        output.printProduct(product);
    }


    /* 관리자 상품 관리 입출력 */

    public ProductManageMenu adminSelectMenu() {
        output.printManageMenu();
        return ProductManageMenu.by(input.readNumValidate(0, ProductManageMenu.MENU_ARR.length - 1));
    }

    /* 상품 추가: 입출력 처리 후 Optional 반환 (취소 시 빈 Optional) */
    public Optional<ProductCreateDTO> adminAddProduct() {
        Category category = userSelectCategory();
        output.printSelectedCategory(category);

        String name = readName(); int price = readPrice();
        String description = readDescription(); int stock = readStock();

        output.printCreateConfirm(name, price, description, stock);

        if (input.readYesOrNo()) {
            return Optional.of(ProductCreateDTO.builder()
                    .name(name).category(category).price(price).description(description).stock(stock).build());
        }
        return Optional.empty();
    }

    public void printAdminAddSuccess() {
        output.print(CREATE_SUCCESS);
    }


    /* 특정 상품 조회(수정, 삭제 시): 입출력 처리 후 ProductSearchDTO 반환 */
    public ProductSearchDTO adminSearchProduct() {
        Category category = userSelectCategory();
        String name = readName();
        return new ProductSearchDTO(category, name);
    }

    /* 상품 수정 입출력 처리 */
    public ProductUpdateDTO adminUpdateProduct(ProductResponseDTO dto) {
        output.printCurrentInfo(dto);
        /* 나중에 공백 -> null 처리 필요 */
        int price = readPrice(); String description = readDescription(); int stock = readStock();
        return ProductUpdateDTO.builder().id(dto.id())
                .price(price).description(description).stock(stock).build();
    }

    public void printAdminUpdateSuccess(ProductResponseDTO before, ProductResponseDTO after) {
        output.printUpdateSuccess(before, after);
    }

    public boolean adminDeleteConfirm(ProductResponseDTO dto) {
        output.printProduct(dto);
        output.print(DELETE_CONFIRM);

        return input.readYesOrNo();
    }

    public void printAdminDeleteSuccess() {
        output.print(DELETE_SUCCESS);
    }

    private Category userSelectCategory() {
        output.printCategory();
        return Category.categories[input.readNumValidate(1, Category.categories.length)-1];
    }

    public void printCategoryProducts(Category category, List<ProductResponseDTO> list) {
        output.printCategoryProducts(category, list);
    }


    /* 관리 상품 단순 입력 */

    private String readName() {
        output.print(INPUT_NAME);
        return input.readString();
    }

    private String readDescription() {
        output.print(INPUT_DESCRIPTION);
        return input.readString();
    }

    private int readPrice() {
       output.print(INPUT_PRICE);
        return input.readNum();
    }

    private int readStock() {
        output.print(INPUT_STOCK);
        return input.readNum();
    }
}
