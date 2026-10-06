package product;

import io.CommonInput;
import lombok.RequiredArgsConstructor;
import product.dto.ProductResponseDTO;
import java.util.*;


/* 상품 관련 요청 흐름 수행 및 입출력, 서비스에 위임 */
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CommonInput input;


    /* 상품 선택 흐름 */
    /* 종료일 경우 null이 담긴 Optional, 상품 선택 완료일 경우 상품 DTO가 담긴 Optional 반환 */
    public Optional<ProductResponseDTO> getProduct(Category category) {
        List<ProductResponseDTO> list = productService.getAllByCategory(category);

        ProductOutput.printProducts(category, list);
        Optional<Integer> productIdx = getProductIdx(list.size()-1);

        if (productIdx.isEmpty()) { return Optional.empty(); }

        ProductResponseDTO product = list.get(productIdx.get());
        ProductOutput.printProduct(product);
        return Optional.of(product);
    }

    /* 상품 CRUD 흐름 */
    public void manageProducts() {

    }


    private Optional<Integer> getProductIdx(int maxIdx) {
        while(true) {
            /* 화면 번호와 실제 idx의 차이 해결 */
            int productIdx = input.readNum() - 1;

            /* 종료일 경우 null이 담긴 Optional 반환 */
            if (productIdx == -1) { return Optional.empty(); }

            if (0 <= productIdx && productIdx <= maxIdx) {
                return Optional.of(productIdx);
            }
            /* 재입력 요청 출력 후 반복 */
            ProductOutput.printRetryMenuNum();
        }
    }
}
