package org.skypro.skyshop.service;

import org.skypro.skyshop.model.product.*;
import org.skypro.skyshop.model.article.Article;
import java.util.*;

import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

@Service
public class StorageService {
    private final Map<UUID, Product> productStorage;
    private final Map<UUID, Article> articleStorage;

    public StorageService(){
        this.productStorage = new HashMap<>();
        this.articleStorage = new HashMap<>();

        initData();
    }

    public Collection<Product> getAllProducts(){
        return productStorage.values();
    }

    public Collection<Article> getAllArticle(){
        return articleStorage.values();
    }

    public Collection<Searchable> getAllSearchable() {
        Collection<Searchable> all = new ArrayList<>();
        all.addAll(productStorage.values());
        all.addAll(articleStorage.values());

        return all;
    }

    private void initData(){
        Product apple = new SimpleProduct(UUID.randomUUID(),"яблоко ", 50);
        Product banana = new DiscountedProduct(UUID.randomUUID(),"Банан ", 80, 50);
        Product milk = new SimpleProduct(UUID.randomUUID(),"Молоко ", 120);
        Product bread = new FixPriceProduct(UUID.randomUUID(),"Хлеб");
        Product cheese = new SimpleProduct(UUID.randomUUID(),"Сыр", 350);
        Product chocolate = new SimpleProduct(UUID.randomUUID(),"Шоколад", 150);

        productStorage.put(apple.getId(), apple);
        productStorage.put(banana.getId(), banana);
        productStorage.put(milk.getId(), milk);
        productStorage.put(bread.getId(), bread);
        productStorage.put(cheese.getId(), cheese);
        productStorage.put(chocolate.getId(), chocolate);

        Article appleArticle = new Article(UUID.randomUUID(), "Яблоко", "Купите мне яблоко");
        Article bananaArticle = new Article(UUID.randomUUID(), "Банан", "Хочу банан");
        Article milkArticle = new Article(UUID.randomUUID(), "Молоко", "Зачем корове молоко?");

        articleStorage.put(appleArticle.getId(), appleArticle);
        articleStorage.put(bananaArticle.getId(), bananaArticle);
        articleStorage.put(milkArticle.getId(), milkArticle);
    }
}
