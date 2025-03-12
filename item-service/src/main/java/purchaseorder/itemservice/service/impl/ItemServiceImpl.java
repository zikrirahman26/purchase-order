package purchaseorder.itemservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import purchaseorder.itemservice.dto.ItemRequest;
import purchaseorder.itemservice.dto.ItemResponse;
import purchaseorder.itemservice.entity.Item;
import purchaseorder.itemservice.repository.ItemRepository;
import purchaseorder.itemservice.service.ItemService;
import purchaseorder.itemservice.validation.ValidationRequest;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ValidationRequest validationRequest;

    @Transactional
    @Override
    public void addItem(ItemRequest itemRequest) {

        validationRequest.validationRequest(itemRequest);

        Item item = new Item();
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setPrice(itemRequest.getPrice());
        item.setCost(itemRequest.getCost());
        itemRepository.save(item);
    }

    @Transactional
    @Override
    public void updateItem(ItemRequest itemRequest, int itemId) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        validationRequest.validationRequest(itemRequest);

        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setPrice(itemRequest.getPrice());
        item.setCost(itemRequest.getCost());
        itemRepository.save(item);
    }

    @Transactional
    @Override
    public void deleteItem(int itemId) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        itemRepository.delete(item);
    }

    @Transactional
    @Override
    public List<ItemResponse> getAllItems() {

        return itemRepository.findAll().stream()
                .map(this::itemResponse)
                .toList();
    }

    @Transactional
    @Override
    public ItemResponse getItemById(int itemId) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        return itemResponse(item);
    }

    private ItemResponse itemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .cost(item.getCost())
                .build();
    }
}
