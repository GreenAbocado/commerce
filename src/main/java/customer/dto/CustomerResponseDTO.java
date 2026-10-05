package customer.dto;

import customer.Grade;

/* userService -> payService 데이터 전달 */
public record CustomerResponseDTO(Long id, Grade grade) {
}