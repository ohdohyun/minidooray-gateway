package com.nhn.sadari.minidooray.gateway.domain.common;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommonResponse<T> {
    private Header header;
    private List<T> result;
    private int totalCount;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
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