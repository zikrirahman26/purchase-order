package purchaseorder.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import purchaseorder.orderservice.dto.ApiResponse;
import purchaseorder.orderservice.dto.POHRequest;
import purchaseorder.orderservice.dto.POHResponse;
import purchaseorder.orderservice.service.OrderService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addOrder(@RequestBody POHRequest pohRequest) {
        orderService.addOrder(pohRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<String>builder()
                        .status("Success")
                        .message("Order successfully added")
                        .data(null)
                .build());
    }

    @PatchMapping("/update/{pohId}")
    public ResponseEntity<ApiResponse<String>> updateOrder(@RequestBody POHRequest pohRequest, @PathVariable int pohId) {
        orderService.updateOrder(pohRequest, pohId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<String>builder()
                        .status("Success")
                        .message("Order successfully updated")
                        .data(null)
                .build());
    }

    @DeleteMapping("/delete/{pohId}")
    public ResponseEntity<ApiResponse<String>> deleteOrder(@PathVariable int pohId) {
        orderService.deleteOrder(pohId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<String>builder()
                        .status("Success")
                        .message("Order successfully deleted")
                        .data(null)
                .build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<POHResponse>>> getAllOrders() {
        List<POHResponse> pohResponseList = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<POHResponse>>builder()
                        .status("Success")
                        .message("Orders successfully retrieved")
                        .data(pohResponseList)
                .build());
    }

    @GetMapping("/get/{pohId}")
    public ResponseEntity<ApiResponse<POHResponse>> getOrderById(@PathVariable int pohId) {
        POHResponse pohResponse = orderService.getOrderId(pohId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<POHResponse>builder()
                        .status("Success")
                        .message("Order successfully retrieved")
                        .data(pohResponse)
                .build());
    }
}
