package com.theoflu.Project.CryptoLive.user.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String jwt;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    public JwtResponse(String jwt, Long id, String username, String email, List<String> roles) {
        this.id=id;
        this.jwt=jwt;
        this.username=username;
        this.roles=roles;
        this.email=email;
    }
}