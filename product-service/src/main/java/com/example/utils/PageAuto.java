package com.example.utils;

import com.example.dto.PageRequestDto;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageAuto {
    public <T> Page<T> Page(PageRequestDto dto, List<T> list) {
        boolean ascending = dto.getOrder().isAscending();
        PropertyComparator.sort(list, new MutableSortDefinition(dto.getSort(), true, ascending));
        PagedListHolder<T> pageListHolder = new PagedListHolder<>(list);
        pageListHolder.setPage(dto.getPageIndex());
        pageListHolder.setPageSize(dto.getPageSize());
        List<T> pageSlice = pageListHolder.getPageList();
        return new PageImpl<>(pageSlice, new PageRequestDto().getPageable(dto), list.size());
    }
}
