package purchaseorder.itemservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import purchaseorder.itemservice.dto.ItemRequest;
import purchaseorder.itemservice.dto.ItemResponse;
import purchaseorder.itemservice.service.ItemService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void addItem_ShouldReturn201() throws Exception {
        ItemRequest itemRequest = new ItemRequest("Laptop", "Gaming Laptop", 12000, 8000);

        mockMvc.perform(post("/api/items/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("Item successfully added"));

        verify(itemService, times(1)).addItem(any(ItemRequest.class));
    }

    @Test
    void updateItem_ShouldReturn200() throws Exception {
        int itemId = 1;
        ItemRequest itemRequest = new ItemRequest("Laptop", "Gaming Laptop Updated", 15000, 10000);

        mockMvc.perform(patch("/api/items/update/{itemId}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("Item successfully updated"));

        verify(itemService, times(1)).updateItem(any(ItemRequest.class), eq(itemId));
    }

    @Test
    void deleteItem_ShouldReturn200() throws Exception {
        int itemId = 1;

        mockMvc.perform(delete("/api/items/delete/{itemId}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("Item successfully deleted"));

        verify(itemService, times(1)).deleteItem(eq(itemId));
    }

    @Test
    void getAllItems_ShouldReturnList() throws Exception {
        List<ItemResponse> itemResponses = Collections.singletonList(new ItemResponse(1, "Laptop", "Gaming Laptop", 12000, 8000));
        when(itemService.getAllItems()).thenReturn(itemResponses);

        mockMvc.perform(get("/api/items/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("All successfully retrieved"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Laptop"));

        verify(itemService, times(1)).getAllItems();
    }

    @Test
    void getItem_ShouldReturnItem() throws Exception {
        int itemId = 1;
        ItemResponse itemResponse = new ItemResponse(itemId, "Laptop", "Gaming Laptop", 12000, 8000);
        when(itemService.getItemById(itemId)).thenReturn(itemResponse);

        mockMvc.perform(get("/api/items/get/{itemId}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("Item successfully retrieved"))
                .andExpect(jsonPath("$.data.id").value(itemId))
                .andExpect(jsonPath("$.data.name").value("Laptop"));

        verify(itemService, times(1)).getItemById(eq(itemId));
    }
}
