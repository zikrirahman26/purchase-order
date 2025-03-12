package purchaseorder.itemservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import purchaseorder.itemservice.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
