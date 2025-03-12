package purchaseorder.orderservice.service;

import purchaseorder.orderservice.dto.POHRequest;
import purchaseorder.orderservice.dto.POHResponse;

import java.util.List;

public interface OrderService {

    void addOrder(POHRequest pohRequest);

    void updateOrder(POHRequest pohRequest, int pohId);

    void deleteOrder(int pohId);

    List<POHResponse> getAllOrders();

    POHResponse getOrderId(int pohId);
}
