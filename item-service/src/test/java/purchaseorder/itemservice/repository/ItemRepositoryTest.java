package purchaseorder.itemservice.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import purchaseorder.itemservice.entity.Item;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void testSaveItem() {
        Item item = new Item();
        item.setName("Laptop");
        item.setDescription("Gaming Laptop");
        item.setPrice(20000000);
        item.setCost(15000000);
        item.setCreatedBy("admin");
        item.setUpdatedBy("admin");

        Item savedItem = itemRepository.save(item);

        assertEquals("Laptop", savedItem.getName());
    }

    @Test
    void testFindItemById() {
        Item item = new Item();
        item.setName("Laptop");
        item.setDescription("Gaming Laptop");
        item.setPrice(20000000);
        item.setCost(15000000);
        item.setCreatedBy("admin");
        item.setUpdatedBy("admin");

        Item savedItem = itemRepository.save(item);
        Optional<Item> foundItem = itemRepository.findById(savedItem.getId());

        assertTrue(foundItem.isPresent());
        assertEquals("Laptop", foundItem.get().getName());
    }

    @Test
    void testUpdateItem() {
        Item item = new Item();
        item.setName("Laptop");
        item.setDescription("Gaming Laptop");
        item.setPrice(20000000);
        item.setCost(15000000);
        item.setCreatedBy("admin");
        item.setUpdatedBy("admin");

        Item savedItem = itemRepository.save(item);
        savedItem.setPrice(18000000);
        Item updatedItem = itemRepository.save(savedItem);

        assertEquals(18000000, updatedItem.getPrice());
    }

    @Test
    void testDeleteItem() {
        Item item = new Item();
        item.setName("Laptop");
        item.setDescription("Gaming Laptop");
        item.setPrice(20000000);
        item.setCost(15000000);
        item.setCreatedBy("admin");
        item.setUpdatedBy("admin");

        Item savedItem = itemRepository.save(item);
        itemRepository.deleteById(savedItem.getId());

        Optional<Item> deletedItem = itemRepository.findById(savedItem.getId());

        assertFalse(deletedItem.isPresent());
    }

    @Test
    void testFindAllItems() {
        Item item1 = new Item();
        item1.setName("Laptop");
        item1.setDescription("Gaming Laptop");
        item1.setPrice(20000000);
        item1.setCost(15000000);
        item1.setCreatedBy("admin");
        item1.setUpdatedBy("admin");

        Item item2 = new Item();
        item2.setName("Smartphone");
        item2.setDescription("Gaming Smartphone");
        item2.setPrice(10000000);
        item2.setCost(7000000);
        item2.setCreatedBy("admin");
        item2.setUpdatedBy("admin");

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> items = itemRepository.findAll();

        assertFalse(items.isEmpty());
        assertEquals(2, items.size());
    }
}
