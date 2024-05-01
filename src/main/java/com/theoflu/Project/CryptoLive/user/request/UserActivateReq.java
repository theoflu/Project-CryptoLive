package com.theoflu.Project.CryptoLive.user.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserActivateReq {
    private String email;
    private String password;
}