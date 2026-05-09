package com.returntaxi.returntaxibe.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginReqDto implements Serializable {

    private static final long serialVersionUID = -5741001894734447746L;

    private String userName;
    private String password;

}
