package org.skypro.skyshop.model.basket;

import java.util.Collections;
import java.util.List;

public final class UserBasket {
    private final List<BasketItem> items;
    private final int totol;

    public UserBasket(List<BasketItem> items){
        this.items = Collections.unmodifiableList(items);
        this.totol = items.stream().mapToInt(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public int getTotol(){
        return totol;
    }
}
