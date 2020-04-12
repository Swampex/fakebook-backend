package ru.fakebook.pet.form;

import lombok.Data;

@Data
public class UserForm {
    private String login;
    private String password;
    private String email;
    private Boolean rememberMe;
    private String captchaValue;
}