package purchaseorder.orderservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import purchaseorder.orderservice.dto.*;
import purchaseorder.orderservice.entity.PurchaseOrderDetail;
import purchaseorder.orderservice.entity.PurchaseOrderHeader;
import purchaseorder.orderservice.repository.PurchaseOrderDetailRepository;
import purchaseorder.orderservice.repository.PurchaseOrderHeaderRepository;
import purchaseorder.orderservice.service.OrderService;
import purchaseorder.orderservice.validation.ValidationRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    String itemUrl = "http://localhost:8081/api/items";

    private final PurchaseOrderHeaderRepository purchaseOrderHeaderRepository;
    private final PurchaseOrderDetailRepository purchaseOrderDetailRepository;
    private final ValidationRequest validationRequest;
    private final RestTemplate restTemplate;

    @Transactional
    @Override
    public void addOrder(POHRequest pohRequest) {

        validationRequest.validationRequest(pohRequest);

        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        purchaseOrderHeader.setDescription(pohRequest.getDescription());
        purchaseOrderHeaderRepository.save(purchaseOrderHeader);

        List<PurchaseOrderDetail> purchaseOrderDetailList = getCreatedPurchaseOrderDetails(pohRequest, purchaseOrderHeader);
        purchaseOrderDetailRepository.saveAll(purchaseOrderDetailList);
    }

    @Transactional
    @Override
    public void updateOrder(POHRequest pohRequest, int pohId) {

        validationRequest.validationRequest(pohRequest);

        PurchaseOrderHeader purchaseOrderHeader = purchaseOrderHeaderRepository.findById(pohId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase order header not found"));

        purchaseOrderHeader.setDescription(pohRequest.getDescription());

        List<PurchaseOrderDetail> updatedDetails = getUpdatedPurchaseOrderDetails(pohRequest, purchaseOrderHeader);

        purchaseOrderHeaderRepository.save(purchaseOrderHeader);
        purchaseOrderDetailRepository.saveAll(updatedDetails);
    }

    @Transactional
    @Override
    public void deleteOrder(int pohId) {

        PurchaseOrderHeader purchaseOrderHeader = purchaseOrderHeaderRepository.findById(pohId)
                .orElseThrow(() -> new RuntimeException("Purchase order header not found"));

        purchaseOrderDetailRepository.deleteByPohId(pohId);
        purchaseOrderHeaderRepository.delete(purchaseOrderHeader);
    }

    @Transactional
    @Override
    public List<POHResponse> getAllOrders() {
        List<PurchaseOrderHeader> purchaseOrderHeaders = purchaseOrderHeaderRepository.findAll();

        return purchaseOrderHeaders.stream()
                .map(poh -> {
                    List<PurchaseOrderDetail> details = purchaseOrderDetailRepository.findByPohId(poh.getId());
                    return getPohResponse(poh, details);
                })
                .collect(Collectors.toList());    }

    @Transactional
    @Override
    public POHResponse getOrderId(int pohId) {

        PurchaseOrderHeader purchaseOrderHeader = purchaseOrderHeaderRepository.findById(pohId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase order header not found"));

        List<PurchaseOrderDetail> details = purchaseOrderDetailRepository.findByPohId(pohId);
        return getPohResponse(purchaseOrderHeader, details);
    }

    private List<PurchaseOrderDetail> getCreatedPurchaseOrderDetails(POHRequest pohRequest, PurchaseOrderHeader purchaseOrderHeader) {
        return processPurchaseOrderDetails(pohRequest, purchaseOrderHeader, false);
    }

    private List<PurchaseOrderDetail> getUpdatedPurchaseOrderDetails(POHRequest pohRequest, PurchaseOrderHeader purchaseOrderHeader) {
        return processPurchaseOrderDetails(pohRequest, purchaseOrderHeader, true);
    }

    private List<PurchaseOrderDetail> processPurchaseOrderDetails(
            POHRequest pohRequest, PurchaseOrderHeader purchaseOrderHeader, boolean isUpdate) {

        int totalPrice = 0;
        int totalCost = 0;

        Map<Integer, PurchaseOrderDetail> existingDetails = new HashMap<>();
        if (isUpdate) {
            existingDetails = purchaseOrderDetailRepository.findByPohId(purchaseOrderHeader.getId()).stream()
                    .collect(Collectors.toMap(PurchaseOrderDetail::getItemId, Function.identity()));
        }

        List<PurchaseOrderDetail> processedDetails = new ArrayList<>();
        for (PODRequest podRequest : pohRequest.getOrdersDetails()) {
            ItemResponse itemResponse = fetchItemData(podRequest.getItemId());

            PurchaseOrderDetail purchaseOrderDetail = existingDetails.getOrDefault(podRequest.getItemId(), new PurchaseOrderDetail());
            purchaseOrderDetail.setPohId(purchaseOrderHeader.getId());
            purchaseOrderDetail.setItemId(itemResponse.getId());
            purchaseOrderDetail.setItemQty(podRequest.getItemQty());
            purchaseOrderDetail.setItemCost(itemResponse.getCost());
            purchaseOrderDetail.setItemPrice(itemResponse.getPrice());

            processedDetails.add(purchaseOrderDetail);

            totalPrice += podRequest.getItemQty() * itemResponse.getPrice();
            totalCost += podRequest.getItemQty() * itemResponse.getCost();
        }

        purchaseOrderHeader.setTotalPrice(totalPrice);
        purchaseOrderHeader.setTotalCost(totalCost);

        return processedDetails;
    }

    private ItemResponse fetchItemData(int itemId) {
        String url = itemUrl + "/get/" + itemId;
        ItemApiResponse response = restTemplate.getForObject(url, ItemApiResponse.class);

        if (response != null && "Success".equals(response.getStatus())) {
            return response.getData();
        } else {
            throw new RuntimeException("Failed to fetch item data");
        }
    }

    private POHResponse getPohResponse(PurchaseOrderHeader purchaseOrderHeader, List<PurchaseOrderDetail> purchaseOrderDetailList) {
        return POHResponse.builder()
                .id(purchaseOrderHeader.getId())
                .datetime(purchaseOrderHeader.getDatetime())
                .description(purchaseOrderHeader.getDescription())
                .totalPrice(purchaseOrderHeader.getTotalPrice())
                .totalCost(purchaseOrderHeader.getTotalCost())
                .ordersDetails(purchaseOrderDetailList.stream()
                        .map(this::getPodResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private PODResponse getPodResponse(PurchaseOrderDetail purchaseOrderDetail) {
        return PODResponse.builder()
                .id(purchaseOrderDetail.getId())
                .pohId(purchaseOrderDetail.getPohId())
                .itemId(purchaseOrderDetail.getItemId())
                .itemQty(purchaseOrderDetail.getItemQty())
                .itemCost(purchaseOrderDetail.getItemCost())
                .itemPrice(purchaseOrderDetail.getItemPrice())
                .build();
    }
}
