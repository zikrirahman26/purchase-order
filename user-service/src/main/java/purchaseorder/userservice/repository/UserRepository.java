package purchaseorder.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import purchaseorder.userservice.entity.UserApp;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Integer> {
}
