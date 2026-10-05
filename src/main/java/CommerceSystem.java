import io.*;
import lombok.RequiredArgsConstructor;
import product.*;
import product.dto.ProductResponseDTO;
import java.util.*;
import static exception.ExceptionMessage.NUM_NOT_EXIST;
import static exception.GlobalExceptionHandler.handleException;
import static io.MenuOption.EXIT;

/* 상품 조회 로직 분기 */
@RequiredArgsConstructor
public class CommerceSystem {

    private final ProductService productService;
    private boolean isRun = true;
    private final Input input;

    public void start() {
        while (isRun) {
            handleException(() -> {
                // 메뉴 출력
                Output.printMenu();

                Category category = input.readMenuNum();
                if (category == null) { stopRun(); return; }

                // 특정 카테고리 상품들 출력
                List<ProductResponseDTO> list = productService.getAllByCategory(category);
                Output.printProducts(category, list);

                int productNum = input.readNum();
                if (productNum == EXIT) { return; }
                if ((productNum < 1 || productNum > list.size())) { throw new IllegalArgumentException(NUM_NOT_EXIST); }

                // 특정 상품 출력
                Output.printProduct(list.get(productNum-1));
            });
        }
        Output.printExit();
    }

    private void stopRun() {
        this.isRun = false;
    }
}