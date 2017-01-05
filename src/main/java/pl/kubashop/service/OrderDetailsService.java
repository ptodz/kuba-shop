package pl.kubashop.service;

import pl.kubashop.domain.OrderDetails;
import pl.kubashop.repository.OrderDetailsRepository;
import pl.kubashop.service.dto.OrderDetailsDTO;
import pl.kubashop.service.mapper.OrderDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing OrderDetails.
 */
@Service
@Transactional
public class OrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(OrderDetailsService.class);
    
    @Inject
    private OrderDetailsRepository orderDetailsRepository;

    @Inject
    private OrderDetailsMapper orderDetailsMapper;

    /**
     * Save a orderDetails.
     *
     * @param orderDetailsDTO the entity to save
     * @return the persisted entity
     */
    public OrderDetailsDTO save(OrderDetailsDTO orderDetailsDTO) {
        log.debug("Request to save OrderDetails : {}", orderDetailsDTO);
        OrderDetails orderDetails = orderDetailsMapper.orderDetailsDTOToOrderDetails(orderDetailsDTO);
        orderDetails = orderDetailsRepository.save(orderDetails);
        OrderDetailsDTO result = orderDetailsMapper.orderDetailsToOrderDetailsDTO(orderDetails);
        return result;
    }

    /**
     *  Get all the orderDetails.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<OrderDetailsDTO> findAll() {
        log.debug("Request to get all OrderDetails");
        List<OrderDetailsDTO> result = orderDetailsRepository.findAll().stream()
            .map(orderDetailsMapper::orderDetailsToOrderDetailsDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one orderDetails by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OrderDetailsDTO findOne(Long id) {
        log.debug("Request to get OrderDetails : {}", id);
        OrderDetails orderDetails = orderDetailsRepository.findOne(id);
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.orderDetailsToOrderDetailsDTO(orderDetails);
        return orderDetailsDTO;
    }

    /**
     *  Delete the  orderDetails by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderDetails : {}", id);
        orderDetailsRepository.delete(id);
    }
}
