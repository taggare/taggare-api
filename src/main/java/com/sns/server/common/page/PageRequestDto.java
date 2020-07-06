package com.sns.server.common.page;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
public final class PageRequestDto<T> {

    private int page;
    private int size;
    private Sort.Direction direction;

    /**
     * 0과 같거나 작으면 1페이지로 설정한다.
     * @param page
     */
    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 50;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public PageRequest of() {
        return PageRequest.of(page - 1, size, direction, "created");
    }
}