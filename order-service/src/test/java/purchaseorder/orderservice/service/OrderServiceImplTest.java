package purchaseorder.orderservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import purchaseorder.orderservice.dto.*;
import purchaseorder.orderservice.entity.PurchaseOrderHeader;
import purchaseorder.orderservice.repository.PurchaseOrderDetailRepository;
import purchaseorder.orderservice.repository.PurchaseOrderHeaderRepository;
import purchaseorder.orderservice.service.impl.OrderServiceImpl;
import purchaseorder.orderservice.validation.ValidationRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private PurchaseOrderHeaderRepository purchaseOrderHeaderRepository;

    @Mock
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Mock
    private ValidationRequest validationRequest;

    private POHRequest pohRequest;
    private PurchaseOrderHeader purchaseOrderHeader;

    @BeforeEach
    void setUp() {
        pohRequest = new POHRequest("Test Order", Collections.emptyList());
        purchaseOrderHeader = new PurchaseOrderHeader();
        purchaseOrderHeader.setId(1);
        purchaseOrderHeader.setDescription("Test Order");
    }

    @Test
    void testAddOrder() {
        when(purchaseOrderHeaderRepository.save(any())).thenReturn(purchaseOrderHeader);

        orderService.addOrder(pohRequest);

        verify(validationRequest, times(1)).validationRequest(pohRequest);
        verify(purchaseOrderHeaderRepository, times(1)).save(any());
        verify(purchaseOrderDetailRepository, times(1)).saveAll(any());
    }

    @Test
    void testUpdateOrder() {
        when(purchaseOrderHeaderRepository.findById(1)).thenReturn(Optional.of(purchaseOrderHeader));
        when(purchaseOrderDetailRepository.findByPohId(1)).thenReturn(Collections.emptyList());

        orderService.updateOrder(pohRequest, 1);

        verify(purchaseOrderHeaderRepository, times(1)).save(purchaseOrderHeader);
        verify(purchaseOrderDetailRepository, times(1)).saveAll(any());
    }

    @Test
    void testUpdateOrder_NotFound() {
        when(purchaseOrderHeaderRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.updateOrder(pohRequest, 1))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND");
    }

    @Test
    void testDeleteOrder() {
        when(purchaseOrderHeaderRepository.findById(1)).thenReturn(Optional.of(purchaseOrderHeader));

        orderService.deleteOrder(1);

        verify(purchaseOrderDetailRepository, times(1)).deleteByPohId(1);
        verify(purchaseOrderHeaderRepository, times(1)).delete(purchaseOrderHeader);
    }

    @Test
    void testDeleteOrder_NotFound() {
        when(purchaseOrderHeaderRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.deleteOrder(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Purchase order header not found");
    }

    @Test
    void testGetAllOrders() {
        when(purchaseOrderHeaderRepository.findAll()).thenReturn(List.of(purchaseOrderHeader));
        when(purchaseOrderDetailRepository.findByPohId(1)).thenReturn(Collections.emptyList());

        List<POHResponse> responses = orderService.getAllOrders();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getDescription()).isEqualTo("Test Order");
    }

    @Test
    void testGetOrderById() {
        when(purchaseOrderHeaderRepository.findById(1)).thenReturn(Optional.of(purchaseOrderHeader));
        when(purchaseOrderDetailRepository.findByPohId(1)).thenReturn(Collections.emptyList());

        POHResponse response = orderService.getOrderId(1);

        assertThat(response.getDescription()).isEqualTo("Test Order");
    }
}
