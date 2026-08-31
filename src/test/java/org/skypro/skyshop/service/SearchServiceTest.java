package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_ShouldReturnEmptyList_WhenNoSearchableObjects() {
        when(storageService.getAllSearchable()).thenReturn(List.of());
        Collection<SearchResult> results = searchService.search("яблоко");
        assertThat(results).isEmpty();
    }

    @Test
    void search_ShouldReturnEmptyList_WhenNoMatches() {
        Searchable product = new SimpleProduct(UUID.randomUUID(), "Банан", 80);
        Searchable article = new Article(UUID.randomUUID(), "Как выбрать банан", "Текст...");
        when(storageService.getAllSearchable()).thenReturn(List.of(product, article));
        Collection<SearchResult> results = searchService.search("яблоко");
        assertThat(results).isEmpty();
    }

    @Test
    void search_ShouldReturnMatchingResult_WhenMatchExists() {
        Searchable appleProduct = new SimpleProduct(UUID.randomUUID(), "Яблоко", 50);
        Searchable bananaArticle = new Article(UUID.randomUUID(), "Банан", "Текст про банан");
        when(storageService.getAllSearchable()).thenReturn(List.of(appleProduct, bananaArticle));
        Collection<SearchResult> results = searchService.search("яблоко");
        assertThat(results).hasSize(1);
        SearchResult result = results.iterator().next();
        assertThat(result.getName()).isEqualTo("Яблоко");
        assertThat(result.getContentType()).isEqualTo("PRODUCT");
    }
}