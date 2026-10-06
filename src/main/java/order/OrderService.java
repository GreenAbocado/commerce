package order;

import lombok.RequiredArgsConstructor;
import order.dto.OrderResultDTO;
import product.ProductService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final ProductService productService;

    public List<OrderResultDTO> order() {
        List<OrderResultDTO> list = new ArrayList<>();
        cartService.getAll()
                .forEach((item)-> {
                    int before = productService.getProductById(item.productId()).stock();
                    productService.decreaseStock(item.productId(), item.quantity());
                    list.add(new OrderResultDTO(item.name(), before, before - item.quantity()));
                });

        cartService.clear();
        return list;
    }
}
