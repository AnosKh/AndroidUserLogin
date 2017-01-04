package com.hrd.tolapheng.knongdai_app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tolapheng on 12/27/16.
 */
public class User {

    @SerializedName("USER_ID")
    private int userId;
    @SerializedName("USERNAME")
    private String username;
    @SerializedName("EMAIL")
    private String email;
    @SerializedName("GENDER")
    private String gender;
    @SerializedName("DATE_OF_BIRTH")
    private String dateOfBirth;
    @SerializedName("PHONENUMBER")
    private String phoneNumber;
    @SerializedName("PASSWORD")
    private String password;
    @SerializedName("POINT")
    private int point;
    @SerializedName("USER_IMAGE_URL")
    private String userImageUrl;
    @SerializedName("REGISTERED_DATE")
    private String registeredDate;
    @SerializedName("USER_HASH")
    private String userHash;
    @SerializedName("SIGN_UP_WITH")
    private String signUpWith;
    @SerializedName("SOCIAL_TYPE")
    private String scoialType;
    @SerializedName("SOCIAL_ID")
    private String socialId;
    @SerializedName("STATUS")
    private String status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(String userHash) {
        this.userHash = userHash;
    }

    public String getSignUpWith() {
        return signUpWith;
    }

    public void setSignUpWith(String signUpWith) {
        this.signUpWith = signUpWith;
    }

    public String getScoialType() {
        return scoialType;
    }

    public void setScoialType(String scoialType) {
        this.scoialType = scoialType;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
