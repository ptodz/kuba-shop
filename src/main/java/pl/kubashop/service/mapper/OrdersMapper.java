package pl.kubashop.service.mapper;

import pl.kubashop.domain.*;
import pl.kubashop.service.dto.OrdersDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Orders and its DTO OrdersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdersMapper {

    @Mapping(source = "customer.id", target = "customerId")
    OrdersDTO ordersToOrdersDTO(Orders orders);

    List<OrdersDTO> ordersToOrdersDTOs(List<Orders> orders);

    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(source = "customerId", target = "customer")
    Orders ordersDTOToOrders(OrdersDTO ordersDTO);

    List<Orders> ordersDTOsToOrders(List<OrdersDTO> ordersDTOs);

    default Customer customerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
