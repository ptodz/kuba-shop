package pl.kubashop.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.kubashop.service.OrderDetailsService;
import pl.kubashop.web.rest.util.HeaderUtil;
import pl.kubashop.service.dto.OrderDetailsDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing OrderDetails.
 */
@RestController
@RequestMapping("/api")
public class OrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(OrderDetailsResource.class);
        
    @Inject
    private OrderDetailsService orderDetailsService;

    /**
     * POST  /order-details : Create a new orderDetails.
     *
     * @param orderDetailsDTO the orderDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderDetailsDTO, or with status 400 (Bad Request) if the orderDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-details")
    @Timed
    public ResponseEntity<OrderDetailsDTO> createOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save OrderDetails : {}", orderDetailsDTO);
        if (orderDetailsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("orderDetails", "idexists", "A new orderDetails cannot already have an ID")).body(null);
        }
        OrderDetailsDTO result = orderDetailsService.save(orderDetailsDTO);
        return ResponseEntity.created(new URI("/api/order-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("orderDetails", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-details : Updates an existing orderDetails.
     *
     * @param orderDetailsDTO the orderDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderDetailsDTO,
     * or with status 400 (Bad Request) if the orderDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderDetailsDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-details")
    @Timed
    public ResponseEntity<OrderDetailsDTO> updateOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update OrderDetails : {}", orderDetailsDTO);
        if (orderDetailsDTO.getId() == null) {
            return createOrderDetails(orderDetailsDTO);
        }
        OrderDetailsDTO result = orderDetailsService.save(orderDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("orderDetails", orderDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-details : get all the orderDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderDetails in body
     */
    @GetMapping("/order-details")
    @Timed
    public List<OrderDetailsDTO> getAllOrderDetails() {
        log.debug("REST request to get all OrderDetails");
        return orderDetailsService.findAll();
    }

    /**
     * GET  /order-details/:id : get the "id" orderDetails.
     *
     * @param id the id of the orderDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-details/{id}")
    @Timed
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(@PathVariable Long id) {
        log.debug("REST request to get OrderDetails : {}", id);
        OrderDetailsDTO orderDetailsDTO = orderDetailsService.findOne(id);
        return Optional.ofNullable(orderDetailsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /order-details/:id : delete the "id" orderDetails.
     *
     * @param id the id of the orderDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long id) {
        log.debug("REST request to delete OrderDetails : {}", id);
        orderDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("orderDetails", id.toString())).build();
    }

}
