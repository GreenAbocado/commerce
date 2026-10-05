package customer;

import lombok.RequiredArgsConstructor;
import java.util.Map;
import java.util.Optional;

/* 고객 저장소 및 CRUD */
@RequiredArgsConstructor
public class CustomerRepository {

    /*
     * 현재 요구사항은 고객 확인 및 Grade 확인이므로,
     * 더미 고객 저장 및 userName 기반 조회가 가능할 정도로만 구현
     * 이후 필요하다면 다른 로직 추가 예정
     */

    private final Map<Long, Customer> store;
    private long idCounter = 0;

    public void saveCustomer(Customer customer) {
        customer.initId(idCounter);
        store.put(customer.getId(), customer);
        idCounter++;
    }

    public Optional<Customer> findByUserName(String userName) {
        return store.values().stream()
                .filter((customer)->customer.getUserName().equals(userName))
                .findFirst();
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}