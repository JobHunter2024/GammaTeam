package com.jobhunter24.queryingapi.api.model;

import lombok.Builder;

@Builder
public class BaseResponse {
    public BaseResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean success;
    public String message;
}
