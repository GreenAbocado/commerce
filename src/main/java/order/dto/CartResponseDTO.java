package order.dto;

public record CartResponseDTO(long productId, String name, int price, int quantity) {
}