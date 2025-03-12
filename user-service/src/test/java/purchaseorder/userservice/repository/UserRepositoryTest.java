package purchaseorder.userservice.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import purchaseorder.userservice.entity.UserApp;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testSaveUser() {
        UserApp user = new UserApp();
        user.setFirstName("Zikri");
        user.setLastName("Rahman");
        user.setEmail("zikri.rahman@gmail.com");
        user.setPhone("081122334455");
        user.setCreatedBy("admin");
        user.setUpdatedBy("admin");

        UserApp savedUser = userRepository.save(user);

        assertEquals("Zikri", savedUser.getFirstName());
    }

    @Test
    void testFindUserById() {
        UserApp user = new UserApp();
        user.setFirstName("Zikri");
        user.setLastName("Rahman");
        user.setEmail("zikri.rahman@gmail.com");
        user.setPhone("081122334455");
        user.setCreatedBy("admin");
        user.setUpdatedBy("admin");

        UserApp savedUser = userRepository.save(user);
        Optional<UserApp> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("Zikri", foundUser.get().getFirstName());
    }

    @Test
    void testUpdateUser() {
        UserApp user = new UserApp();
        user.setFirstName("Zikri");
        user.setLastName("Rahman");
        user.setEmail("zikri.rahman@gmail.com");
        user.setPhone("081122334455");
        user.setCreatedBy("admin");
        user.setUpdatedBy("admin");

        UserApp savedUser = userRepository.save(user);
        savedUser.setPhone("081111111111");
        UserApp updatedUser = userRepository.save(savedUser);

        assertEquals("081111111111", updatedUser.getPhone());
    }

    @Test
    void testDeleteUser() {
        UserApp user = new UserApp();
        user.setFirstName("Zikri");
        user.setLastName("Rahman");
        user.setEmail("zikri.rahman@gmail.com");
        user.setPhone("081122334455");
        user.setCreatedBy("admin");
        user.setUpdatedBy("admin");

        UserApp savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getId());

        Optional<UserApp> deletedUser = userRepository.findById(savedUser.getId());

        assertFalse(deletedUser.isPresent());
    }

    @Test
    void testFindAllUsers() {
        UserApp user1 = new UserApp();
        user1.setFirstName("Zikri");
        user1.setLastName("Rahman");
        user1.setEmail("zikri.rahman@gmail.com");
        user1.setPhone("081122334455");
        user1.setCreatedBy("admin");
        user1.setUpdatedBy("admin");

        UserApp user2 = new UserApp();
        user2.setFirstName("Rahman");
        user2.setLastName("Zikri");
        user2.setEmail("rahman.zikri@gmail.com");
        user2.setPhone("082211334455");
        user2.setCreatedBy("admin");
        user2.setUpdatedBy("admin");

        userRepository.save(user1);
        userRepository.save(user2);

        List<UserApp> users = userRepository.findAll();

        assertFalse(users.isEmpty());
        assertEquals(2, users.size());
    }
}
