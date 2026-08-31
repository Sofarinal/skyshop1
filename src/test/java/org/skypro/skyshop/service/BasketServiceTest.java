package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    void addProductToBasket_WhenProductNotFound_ShouldThrowException() {
        UUID id = UUID.randomUUID();
        when(storageService.getProductById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> basketService.addProductToBasket(id))
                .isInstanceOf(NoSuchProductException.class)
                .hasMessageContaining("не найден");
    }

    @Test
    void addProductToBasket_WhenProductExists_ShouldAddToBasket() {
        UUID id = UUID.randomUUID();
        Product product = new SimpleProduct(id, "Яблоко ", 50);
        when(storageService.getProductById(id)).thenReturn(Optional.of(product));
        basketService.addProductToBasket(id);
        verify(productBasket, times(1)).addProduct(id);
    }

    @Test
    void getUserBasket_WhenBasketEmpty_ShouldReturnEmptyBasket() {
        when(productBasket.getProducts()).thenReturn(Map.of());
        UserBasket result = basketService.getUserBasket();
        assertThat(result.getItems()).isEmpty();
        assertThat(result.getTotal()).isZero();
    }

    @Test
    void getUserBasket_WhenBasketHasItems_ShouldReturnFilledBasket() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        Product product1 = new SimpleProduct(productId1, "Яблоко", 50);
        Product product2 = new SimpleProduct(productId2, "Молоко", 120);

        when(productBasket.getProducts()).thenReturn(Map.of(
                productId1, 2,
                productId2, 1
        ));

        when(storageService.getProductById(productId1)).thenReturn(Optional.of(product1));
        when(storageService.getProductById(productId2)).thenReturn(Optional.of(product2));

        UserBasket result = basketService.getUserBasket();
        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getTotal()).isEqualTo(220);
    }
}