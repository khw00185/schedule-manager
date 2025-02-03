package com.example.schedulemanager.schedule.dto.request;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Getter
public class AllSchedulesRequestDto {
    private String modifiedDateStart;
    private String modifiedDateEnd;
    private String authorId;
    private int size;

    private int page;
    private Pageable pageable;

    public AllSchedulesRequestDto(String modifiedDateStart, String modifiedDateEnd, String authorId, int page, int size) {
        this.modifiedDateStart = modifiedDateStart;
        this.modifiedDateEnd = modifiedDateEnd;
        this.authorId = authorId;
        this.size = size;
        this.page = page;
        this.pageable = PageRequest.of(page-1, size);
    }
}
