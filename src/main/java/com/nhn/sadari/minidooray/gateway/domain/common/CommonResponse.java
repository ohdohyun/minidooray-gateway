package com.nhn.sadari.minidooray.gateway.domain.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommonResponse<T> {
    private Header header;
    private List<T> result;
    private int totalCount;

    @Data
    @Builder
    public static class Header {
        private boolean isSuccessful;
        private int resultCode;
        private String resultMessage;
    }

    public int getTotalCount() {
        return result == null? 0 : result.size();
    }
}