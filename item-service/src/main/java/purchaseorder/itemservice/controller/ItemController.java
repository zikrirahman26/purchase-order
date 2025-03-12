package purchaseorder.itemservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import purchaseorder.itemservice.dto.ApiResponse;
import purchaseorder.itemservice.dto.ItemRequest;
import purchaseorder.itemservice.dto.ItemResponse;
import purchaseorder.itemservice.service.ItemService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addItem(@RequestBody ItemRequest itemRequest) {
        itemService.addItem(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<String>builder()
                        .status("Success")
                        .message("Item successfully added")
                        .data(null)
                .build());
    }

    @PatchMapping("/update/{itemId}")
    public ResponseEntity<ApiResponse<String>> updateItem(@RequestBody ItemRequest itemRequest, @PathVariable int itemId) {
        itemService.updateItem(itemRequest, itemId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<String>builder()
                        .status("Success")
                        .message("Item successfully updated")
                        .data(null)
                .build());
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<ApiResponse<String>> deleteItem(@PathVariable int itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<String>builder()
                        .status("Success")
                        .message("Item successfully deleted")
                        .data(null)
                .build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ItemResponse>>> getAllItems() {
        List<ItemResponse> itemResponseList = itemService.getAllItems();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<ItemResponse>>builder()
                        .status("Success")
                        .message("All successfully retrieved")
                        .data(itemResponseList)
                .build());
    }

    @GetMapping("/get/{itemId}")
    public ResponseEntity<ApiResponse<ItemResponse>> getItem(@PathVariable int itemId) {
        ItemResponse itemResponse = itemService.getItemById(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<ItemResponse>builder()
                        .status("Success")
                        .message("Item successfully retrieved")
                        .data(itemResponse)
                .build());
    }
}
