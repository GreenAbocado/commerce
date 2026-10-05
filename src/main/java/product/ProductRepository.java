package product;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* 상품 저장소 및 CRUD */
@RequiredArgsConstructor
public class ProductRepository {
    private final Map<Long, Product> store;
    private long idCounter = 0;

    public void saveProduct(Product product) {
        product.initId(idCounter);
        store.put(product.getId(), product);
        idCounter++;
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Product> findByCategoryAndName(Category category, String name) {
        return store.values().stream()
                .filter((product)-> (product.getCategory() == category) && (product.getName().equals(name)))
                .findFirst();
    }

    public List<Product> findAllByCategory(Category category) {
        return store.values().stream()
                .filter((product) -> product.getCategory() == category).toList();
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}