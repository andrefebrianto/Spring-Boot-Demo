package com.example.demo.model.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class _PageResponse<T> {

    private List<T> items;

    private Long totalItems;

    private int currentPage;

    private int totalPages;

    public _PageResponse(Page<T> pageItems) {
        items = pageItems.getContent();
        currentPage = pageItems.getNumber() + 1;
        totalItems = pageItems.getTotalElements();
        totalPages = pageItems.getTotalPages();
    }
}
