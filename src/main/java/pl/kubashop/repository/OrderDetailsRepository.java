package pl.kubashop.repository;

import pl.kubashop.domain.OrderDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderDetails entity.
 */
@SuppressWarnings("unused")
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {

}
