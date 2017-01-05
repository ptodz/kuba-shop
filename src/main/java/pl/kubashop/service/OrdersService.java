package pl.kubashop.service;

import pl.kubashop.domain.Orders;
import pl.kubashop.repository.OrdersRepository;
import pl.kubashop.service.dto.OrdersDTO;
import pl.kubashop.service.mapper.OrdersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Orders.
 */
@Service
@Transactional
public class OrdersService {

    private final Logger log = LoggerFactory.getLogger(OrdersService.class);
    
    @Inject
    private OrdersRepository ordersRepository;

    @Inject
    private OrdersMapper ordersMapper;

    /**
     * Save a orders.
     *
     * @param ordersDTO the entity to save
     * @return the persisted entity
     */
    public OrdersDTO save(OrdersDTO ordersDTO) {
        log.debug("Request to save Orders : {}", ordersDTO);
        Orders orders = ordersMapper.ordersDTOToOrders(ordersDTO);
        orders = ordersRepository.save(orders);
        OrdersDTO result = ordersMapper.ordersToOrdersDTO(orders);
        return result;
    }

    /**
     *  Get all the orders.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<OrdersDTO> findAll() {
        log.debug("Request to get all Orders");
        List<OrdersDTO> result = ordersRepository.findAll().stream()
            .map(ordersMapper::ordersToOrdersDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one orders by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OrdersDTO findOne(Long id) {
        log.debug("Request to get Orders : {}", id);
        Orders orders = ordersRepository.findOne(id);
        OrdersDTO ordersDTO = ordersMapper.ordersToOrdersDTO(orders);
        return ordersDTO;
    }

    /**
     *  Delete the  orders by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Orders : {}", id);
        ordersRepository.delete(id);
    }
}
