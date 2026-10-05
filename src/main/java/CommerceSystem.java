import io.*;
import lombok.RequiredArgsConstructor;
import product.*;
import product.dto.ProductResponseDTO;
import java.util.*;
import static exception.ExceptionMessage.*;
import static exception.GlobalExceptionHandler.handleException;

/* 상품 조회 로직 분기 */
@RequiredArgsConstructor
public class CommerceSystem {

    private final ProductService productService;
    private boolean isRun = true;
    private final CommonInput input;

    public void start() {
        while (isRun) {
            handleException(() -> {
                // 메뉴 출력
                ProductOutput.printMenu();

                int categoryNum = input.readNum();
                if (categoryNum == 0) { stopRun(); return; }
                if (categoryNum < 1 || categoryNum > Category.categories.length) { throw new IllegalArgumentException(NONE_CATEGORY_INPUT); }
                Category category = Category.categories[categoryNum-1];

                // 특정 카테고리 상품들 출력
                List<ProductResponseDTO> list = productService.getAllByCategory(category);
                ProductOutput.printProducts(category, list);

                int productNum = input.readNum();
                if (productNum == 0) { return; }
                if ((productNum < 1 || productNum > list.size())) { throw new IllegalArgumentException(NUM_NOT_EXIST); }

                // 특정 상품 출력
                ProductOutput.printProduct(list.get(productNum-1));
            });
        }
        ProductOutput.printExit();
    }

    private void stopRun() {
        this.isRun = false;
    }
}