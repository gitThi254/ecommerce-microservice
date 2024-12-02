package com.example.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequestDto {
    private Integer pageIndex=0;
    private Integer pageSize=10;
    private Sort.Direction order= Sort.Direction.ASC;
    private String sort="id";


    public Pageable getPageable(PageRequestDto dto) {
        Integer page = Objects.nonNull(dto.getPageIndex()) ? dto.getPageIndex() : this.pageIndex;
        Integer size = Objects.nonNull(dto.getPageSize()) ? dto.getPageSize() : this.pageSize;
        Sort.Direction order= Objects.nonNull(dto.getOrder()) ? dto.getOrder() : this.order;
        String sort = Objects.nonNull(dto.getSort()) ? dto.getSort() : this.sort;
        return PageRequest.of(page, size, order, sort);
    }

}
