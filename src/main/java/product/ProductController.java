package product;

import lombok.RequiredArgsConstructor;
import product.dto.ProductCreateDTO;
import product.dto.ProductResponseDTO;
import product.dto.ProductSearchDTO;
import product.dto.ProductUpdateDTO;
import product.io.ProductConsole;
import java.util.*;

/* 상품 관련 요청 처리 수행 및 콘솔, 서비스에 위임 */
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConsole console;

    /* 사용자 상품 리스트 확인 및 선택 흐름 */
    public Optional<ProductResponseDTO> userFlow(Category category) {
        List<ProductResponseDTO> list = productService.getAllByCategory(category);

        /* 종료일 경우 빈 Optional, 선택일 경우 제공된 리스트에서 상품이 담긴 Optional 반환 */
        Optional<ProductResponseDTO> product = console.userSelectProduct(category, list);

        product.ifPresent(console::printUserSelectProduct);

        /* 그대로 반환하여, 종료일 경우 빈 Optional, 선택일 경우 상품이 담긴 Optional을 외부에서 사용 */
        return product;
    }


    /* 관리자 상품 CRUD 흐름 */

    public void manageFlow() {
        while (true) {
            switch(console.adminSelectMenu()) {
                case ADD_PRODUCT: addProduct(); break;
                case UPDATE_PRODUCT: updateProduct(); break;
                case DELETE_PRODUCT: deleteProduct(); break;
                case FIND_PRODUCTS: getAllProducts(); break;
                case BACK_MAIN: return;
            }
        }
    }

    /* 콘솔 Optional 확인 후 서비스로 생성 요청 위임 */
    private void addProduct() {
        Optional<ProductCreateDTO> dto = console.adminAddProduct();
        if (dto.isEmpty()) { return; }

        productService.addProduct(dto.get());
        console.printAdminAddSuccess();
    }


    private void updateProduct() {
        ProductSearchDTO dto = console.adminSearchProduct();
        ProductResponseDTO findProduct = productService.getByCategoryAndName(dto.category(), dto.name());

        ProductUpdateDTO updateProduct = console.adminUpdateProduct(findProduct);
        productService.updateProductInfo(updateProduct);

        /* 이전 상품과 변경된 상품 비교하여 출력 */
        console.printAdminUpdateSuccess(findProduct, productService.getByCategoryAndName(dto.category(), dto.name()));
    }

    private void deleteProduct() {
        ProductSearchDTO dto = console.adminSearchProduct();
        ProductResponseDTO findProduct = productService.getByCategoryAndName(dto.category(), dto.name());
        if (console.adminDeleteConfirm(findProduct)) { productService.removeProduct(findProduct.id()); }
        console.printAdminDeleteSuccess();
    }

    private void getAllProducts() {
        Arrays.stream(Category.categories).forEach((category)->
                console.printCategoryProducts(category, productService.getAllByCategory(category)));
    }
}
