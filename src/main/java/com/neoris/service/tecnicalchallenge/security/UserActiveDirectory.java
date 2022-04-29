/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoris.service.tecnicalchallenge.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class UserActiveDirectory {

    @JsonProperty("name_role")
    private String nameRole;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("iss")
    private String iss;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("exp")
    private String exp;
    @JsonProperty("name_application")
    private String nameApplication;
    @JsonProperty("iat")
    private String iat;
    @JsonProperty("email")
    private String email;
    @JsonProperty("enabled")
    private boolean enabled;


}
