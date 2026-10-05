package customer;

import customer.dto.CustomerCreateDTO;
import customer.dto.CustomerResponseDTO;
import lombok.RequiredArgsConstructor;

/* 고객 관리 비즈니스 로직 수행 */
@RequiredArgsConstructor
public class CustomerService {

    /*
     * 현재 요구사항은 고객 확인 및 Grade 확인이므로,
     * 더미 고객 회원가입 및 사용자 일치 확인이 가능할 정도로만 구현
     * 이후 필요하다면 다른 로직 추가 예정
     */

    private final CustomerRepository customerRepository;

    public void register(CustomerCreateDTO dto) {
        /* 존재하는 사용자 아이디일 경우 예외 */
        if (customerRepository.findByUserName(dto.userName()).isPresent()) {
            throw new IllegalArgumentException("아이디 중복");
        }

        customerRepository.saveCustomer(Customer.builder()
                                            .userName(dto.userName())
                                            .password(dto.password())     /* 해시함수 따로 적용 X */
                                            .name(dto.name()).build());
    }

    /* 회원 일치 여부 확인 */
    public CustomerResponseDTO login(String userName, String password) {

        Customer customer = customerRepository.findByUserName(userName)
                    .orElseThrow(()-> new IllegalArgumentException("아이디나 비번 일치X"));

        if (!customer.getPassword().equals(password)) {
            throw new IllegalArgumentException("아이디나 비번 일치 X");
        }
        return new CustomerResponseDTO(customer.getId(), customer.getGrade());
    }

    public void increaseTotalUsedPrice(Long id, int price) {
        Customer customer = customerRepository.findById(id)
                                .orElseThrow(()-> new IllegalArgumentException("일치하는 회원 X"));
        customer.increaseTotalUsedPrice(price);
    }
}
