package order;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class CartRepository {
    private final Map<Long, CartItem> map;

    public void saveItem(CartItem item) {
        map.put(item.getProductId(), item);
    }

    public List<CartItem> findAll() {
        return new ArrayList<>(map.values());
    }

    public Optional<CartItem> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    public void clear() {
        map.clear();
    }
}
