package com.shopApp.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopApp.inventoryservice.dto.InventoryResponse;
import com.shopApp.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    // @Transactional(Transactional.TxType.)
    // public Boolean isInStock(String skuCode) {
    // return inventoryRepository.findBySkuCode(skuCode).isPresent();
    // }

    // @Transactional(read)
    public List<InventoryResponse> isInStockMultiple(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory -> InventoryResponse
                        .builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .build())
                .toList();
    }
}
