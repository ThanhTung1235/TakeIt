package com.takeIt.dto;

import com.takeIt.entity.Credential;
import com.takeIt.util.DateTimeUtil;
import com.takeIt.util.ObjectUtil;

public class CredentialDTO {
    private String accessToken;
    private String tokenExpiredAt;
    private String createdAt;

    public CredentialDTO(Credential credential) {
        ObjectUtil.cloneObject(this, credential);
        this.accessToken = credential.getAccessToken();
        this.tokenExpiredAt = DateTimeUtil.formatDateFromLong(credential.getTokenExpiredAt());
        this.createdAt = DateTimeUtil.formatDateFromLong(credential.getTokenExpiredAt());
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenExpiredAt() {
        return tokenExpiredAt;
    }

    public void setTokenExpiredAt(String tokenExpiredAt) {
        this.tokenExpiredAt = tokenExpiredAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
