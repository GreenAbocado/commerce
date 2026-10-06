package order;

import lombok.RequiredArgsConstructor;
import order.dto.CartResponseDTO;
import product.ProductService;
import product.dto.ProductResponseDTO;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public void addItem(long productId) {
        ProductResponseDTO product = productService.getProductById(productId);
        Optional<CartItem> item = cartRepository.findById(productId);
        int cartStock = item.isPresent() ? item.get().getQuantity() : 0;

        if (cartStock >= product.stock()) {
            throw new IllegalStateException("재고가 부족합니다");
        }

        if (item.isPresent()) {
            item.get().increaseQuantity();
        } else {
            cartRepository.saveItem(new CartItem(productId));
        }
    }

    public List<CartResponseDTO> getAll() {
        return cartRepository.findAll().stream()
                    .map((cartItem)-> {
                        ProductResponseDTO product = productService.getProductById(cartItem.getProductId());
                        return new CartResponseDTO(product.id(), product.name(), product.price(), cartItem.getQuantity());
                    }).toList();
    }

    public int getTotalPrice() {
        return cartRepository.findAll().stream()
                .mapToInt((cartItem)->
                        productService.getProductById(cartItem.getProductId()).price() * cartItem.getQuantity())
                .sum();
    }

    public void clear() {
        cartRepository.clear();
    }
}
