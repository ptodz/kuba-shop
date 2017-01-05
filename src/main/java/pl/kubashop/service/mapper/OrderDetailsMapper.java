package pl.kubashop.service.mapper;

import pl.kubashop.domain.*;
import pl.kubashop.service.dto.OrderDetailsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity OrderDetails and its DTO OrderDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderDetailsMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "orders.id", target = "ordersId")
    OrderDetailsDTO orderDetailsToOrderDetailsDTO(OrderDetails orderDetails);

    List<OrderDetailsDTO> orderDetailsToOrderDetailsDTOs(List<OrderDetails> orderDetails);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "ordersId", target = "orders")
    OrderDetails orderDetailsDTOToOrderDetails(OrderDetailsDTO orderDetailsDTO);

    List<OrderDetails> orderDetailsDTOsToOrderDetails(List<OrderDetailsDTO> orderDetailsDTOs);

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }

    default Orders ordersFromId(Long id) {
        if (id == null) {
            return null;
        }
        Orders orders = new Orders();
        orders.setId(id);
        return orders;
    }
}
