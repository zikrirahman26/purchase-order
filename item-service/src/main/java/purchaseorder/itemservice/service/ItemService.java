package purchaseorder.itemservice.service;

import purchaseorder.itemservice.dto.ItemRequest;
import purchaseorder.itemservice.dto.ItemResponse;

import java.util.List;

public interface ItemService {

    void addItem(ItemRequest itemRequest);

    void updateItem(ItemRequest itemRequest, int itemId);

    void deleteItem(int itemId);

    List<ItemResponse> getAllItems();

    ItemResponse getItemById(int itemId);
}
