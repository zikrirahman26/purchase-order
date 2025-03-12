package purchaseorder.itemservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import purchaseorder.itemservice.dto.ItemRequest;
import purchaseorder.itemservice.dto.ItemResponse;
import purchaseorder.itemservice.entity.Item;
import purchaseorder.itemservice.repository.ItemRepository;
import purchaseorder.itemservice.service.impl.ItemServiceImpl;
import purchaseorder.itemservice.validation.ValidationRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ValidationRequest validationRequest;

    @InjectMocks
    private ItemServiceImpl itemService;

    private Item item;
    private ItemRequest itemRequest;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setId(1);
        item.setName("Laptop");
        item.setDescription("Gaming Laptop");
        item.setPrice(20000000);
        item.setCost(15000000);

        itemRequest = new ItemRequest();
        itemRequest.setName("Laptop");
        itemRequest.setDescription("Gaming Laptop");
        itemRequest.setPrice(20000000);
        itemRequest.setCost(15000000);
    }

    @Test
    void testAddItem() {
        doNothing().when(validationRequest).validationRequest(itemRequest);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        assertDoesNotThrow(() -> itemService.addItem(itemRequest));

        verify(validationRequest, times(1)).validationRequest(itemRequest);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void testUpdateItem_Success() {
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));
        doNothing().when(validationRequest).validationRequest(itemRequest);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        assertDoesNotThrow(() -> itemService.updateItem(itemRequest, 1));

        verify(itemRepository, times(1)).findById(1);
        verify(validationRequest, times(1)).validationRequest(itemRequest);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void testUpdateItem_NotFound() {
        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> itemService.updateItem(itemRequest, 1));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Item not found", exception.getReason());

        verify(itemRepository, times(1)).findById(1);
        verify(validationRequest, never()).validationRequest(any());
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void testDeleteItem_Success() {
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));
        doNothing().when(itemRepository).delete(item);

        assertDoesNotThrow(() -> itemService.deleteItem(1));

        verify(itemRepository, times(1)).findById(1);
        verify(itemRepository, times(1)).delete(item);
    }

    @Test
    void testDeleteItem_NotFound() {
        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> itemService.deleteItem(1));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Item not found", exception.getReason());

        verify(itemRepository, times(1)).findById(1);
        verify(itemRepository, never()).delete(any(Item.class));
    }

    @Test
    void testGetAllItems() {
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<ItemResponse> items = itemService.getAllItems();

        assertFalse(items.isEmpty());
        assertEquals(1, items.size());
        assertEquals("Laptop", items.get(0).getName());

        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void testGetItemById_Success() {
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        ItemResponse response = itemService.getItemById(1);

        assertNotNull(response);
        assertEquals("Laptop", response.getName());

        verify(itemRepository, times(1)).findById(1);
    }

    @Test
    void testGetItemById_NotFound() {
        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> itemService.getItemById(1));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Item not found", exception.getReason());

        verify(itemRepository, times(1)).findById(1);
    }
}
