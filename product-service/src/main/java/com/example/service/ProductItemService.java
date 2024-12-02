package com.example.service;


import com.example.dto.InventoryDto;
import com.example.dto.PageRequestDto;
import com.example.dto.ProductItemDto;
import com.example.exception.ForeignKeyException;
import com.example.exception.NotFoundException;
import com.example.model.ProductItem;
import com.example.mapper.ProductItemMapper;
import com.example.repository.ProductItemRep;
import com.example.req.ProductItemReq;
import com.example.utils.PageAuto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductItemService implements IProductItemService {
    private final ProductItemMapper productItemMapper;
    private final ProductItemRep productItemRep;
    private final PageAuto pageAuto;
    private final WebClient.Builder webClientBuilder;

    @Override
    public void save(ProductItemReq req, String id) {
        boolean checkSizeAndColor = productItemRep.existsBySizeAndColor(req.getSize(), req.getColor());
        if(checkSizeAndColor) {
            throw new DuplicateKeyException("Size and Color already exists");
        }
        String UUIDRandom = UUID.randomUUID().toString();
        req.setProductId(id);
        req.setSKU(UUIDRandom);
        ProductItem productItem = productItemMapper.mapTo(req);
        InventoryDto inventory = webClientBuilder.build().post()
                .uri("http://inventory-service/api/v1/inventory",
                        uriBuilder -> uriBuilder
                                .queryParam("skuCode", req.getSKU())
                                .queryParam("quantity", req.getQtyInStock())
                                .build())
                .retrieve()
                .bodyToMono(InventoryDto.class)
                .block();
        if(inventory.getQuantity() != 0) {
            productItemRep.save(productItem);
        } else {
            throw new IllegalArgumentException("Error Create Product when add inventory");
        }
    }

    @Override
    public Page<ProductItemDto> getAll(PageRequestDto dto, String keyword, String id) {
        List<ProductItemDto> listDto =  productItemRep.findAllByProductId(id).stream().map(productItemMapper::mapToDto).collect(Collectors.toList());
        return pageAuto.Page(dto, listDto);
    }

    @Override
    public ProductItemDto get(String productId, String id) {
        return productItemRep.findByIdAndProduct_Id(id, productId).map(productItemMapper::mapToDto).orElseThrow(() -> new NotFoundException("Product Item not found"));
    }

    @Override
    public ProductItemDto getSku(String sku) {
        return productItemRep.findBySKU(sku).map(productItemMapper::mapToDto).orElseThrow(() -> new NotFoundException("Product Item with sku not found"));

    }

    @Override
    public void delete(String productId, String id) {
        productItemRep.findByIdAndProduct_Id(id, productId).ifPresentOrElse(productItem -> {
            try {
                webClientBuilder.build().delete()
                        .uri("http://inventory-service/api/v1/inventory/" + productItem.getSKU(),
                                UriBuilder::build)

                        .retrieve()
                        .bodyToMono(InventoryDto.class)
                        .block();
                productItemRep.delete(productItem);
            } catch (DataIntegrityViolationException e) {
                throw new ForeignKeyException(String.format("Cannot delete product with id %s due to foreign key constraint", id));
            }
        }, () -> {
            throw new NotFoundException(String.format("Product with id %s not found", id));
        });
    }

    @Override
    public void update(String productId, String id, ProductItemReq req) {
        productItemRep.findByIdAndProduct_Id(id, productId)
                .map(oldItem -> {
                    oldItem.setColor(req.getColor());
                    oldItem.setSize(req.getSize());
                    oldItem.setSKU(req.getSKU());
                    oldItem.setPrice(req.getPrice());
                    InventoryDto inventory = webClientBuilder.build().put()
                            .uri("http://inventory-service/api/v1/inventory/" + req.getSKU(),
                                    uriBuilder -> uriBuilder
                                            .queryParam("quantity", req.getQtyInStock())
                                            .build())
                            .retrieve()
                            .bodyToMono(InventoryDto.class)
                            .block();
                    if(inventory.getQuantity() != 0) {
                        return productItemRep.save(oldItem);
                    } else {
                        throw new IllegalArgumentException("Error update Product when add inventory");
                    }

                })
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %s not found", id)));
    }
}
