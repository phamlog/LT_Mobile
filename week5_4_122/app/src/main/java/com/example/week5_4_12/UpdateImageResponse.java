package com.example.week5_4_12;

import java.util.List;

public class UpdateImageResponse {
    private boolean success;
    private String message;
    private List<UserModel> result;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<UserModel> getResult() {
        return result;
    }
}
