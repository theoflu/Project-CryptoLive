package com.theoflu.Project.CryptoLive.user.request;

import com.theoflu.Project.CryptoLive.user.entity.ERole;
import com.theoflu.Project.CryptoLive.user.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AssignRoleReq {
    private String username ;
    private List<ERole> roles;
}