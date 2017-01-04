package com.hrd.tolapheng.knongdai_app.response;

import com.google.gson.annotations.SerializedName;
import com.hrd.tolapheng.knongdai_app.model.User;

/**
 * Created by tolapheng on 12/28/16.
 */
public class UserResponse {

    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("STATUS")
    private boolean status;
    @SerializedName("CODE")
    private String code;
    @SerializedName("DATA")
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUsers(User user) {
        this.user = user;
    }
}
