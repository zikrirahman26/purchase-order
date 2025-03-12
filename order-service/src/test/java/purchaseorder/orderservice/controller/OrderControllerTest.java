package purchaseorder.orderservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import purchaseorder.orderservice.dto.*;
import purchaseorder.orderservice.service.OrderService;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private POHRequest pohRequest;
    private POHResponse pohResponse;

    @BeforeEach
    void setUp() {
        PODRequest podRequest = new PODRequest(1, 10, 100, 150);
        pohRequest = new POHRequest("Test Order", List.of(podRequest));
        pohResponse = new POHResponse(1, Timestamp.valueOf("2024-03-12 00:00:00"), "Test Order", 1500, 1000, Collections.emptyList());
    }

    @Test
    void addOrder() {
        ResponseEntity<ApiResponse<String>> response = orderController.addOrder(pohRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Success", Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Order successfully added", response.getBody().getMessage());
        verify(orderService, times(1)).addOrder(pohRequest);
    }

    @Test
    void updateOrder() {
        int pohId = 1;
        ResponseEntity<ApiResponse<String>> response = orderController.updateOrder(pohRequest, pohId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Order successfully updated", response.getBody().getMessage());
        verify(orderService, times(1)).updateOrder(pohRequest, pohId);
    }

    @Test
    void deleteOrder() {
        int pohId = 1;
        ResponseEntity<ApiResponse<String>> response = orderController.deleteOrder(pohId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Order successfully deleted", response.getBody().getMessage());
        verify(orderService, times(1)).deleteOrder(pohId);
    }

    @Test
    void getAllOrders() {
        when(orderService.getAllOrders()).thenReturn(List.of(pohResponse));

        ResponseEntity<ApiResponse<List<POHResponse>>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Orders successfully retrieved", response.getBody().getMessage());
        assertFalse(response.getBody().getData().isEmpty());
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void getOrderById() {
        int pohId = 1;
        when(orderService.getOrderId(pohId)).thenReturn(pohResponse);

        ResponseEntity<ApiResponse<POHResponse>> response = orderController.getOrderById(pohId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Order successfully retrieved", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        verify(orderService, times(1)).getOrderId(pohId);
    }
}