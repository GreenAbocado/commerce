package product;

import lombok.RequiredArgsConstructor;
import product.dto.*;
import java.util.List;
import static exception.ExceptionMessage.*;

/* 상품 관리 비즈니스 로직 */
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void addProduct(ProductCreateDTO dto) {
        if (productRepository.findByCategoryAndName(dto.category(), dto.name()).isPresent()) {
            throw new IllegalArgumentException(PRODUCT_ALREADY_EXIST);
        }
        productRepository.saveProduct(Product.builder()
                .name(dto.name())
                .category(dto.category())
                .price(dto.price())
                .description(dto.description())
                .stock(dto.stock()).build());
    }

    public List<ProductResponseDTO> getAllByCategory(Category category) {
        return productRepository.findAllByCategory(category).stream()
                .map(ProductService::convertToDTO).toList();
    }

    public ProductResponseDTO getByCategoryAndName(Category category, String name) {
        return convertToDTO(productRepository.findByCategoryAndName(category, name)
                                .orElseThrow(()-> new IllegalArgumentException(PRODUCT_NOT_EXIST)));
    }

    /* 도메인이 null이 아닌 필드만 변경 */
    public void updateProductInfo(ProductUpdateDTO dto) {
        Product product = productRepository.findById(dto.id())
                .orElseThrow(()-> new IllegalArgumentException(PRODUCT_NOT_EXIST));
        product.updateInfo(dto.price(),dto.description(), dto.stock());
    }

    public void removeProduct(Long id) {
        productRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(PRODUCT_NOT_EXIST));
        productRepository.deleteById(id);
    }

    /* Product -> DTO 변환 */
    private static ProductResponseDTO convertToDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .stock(product.getStock()).build();
    }
}
