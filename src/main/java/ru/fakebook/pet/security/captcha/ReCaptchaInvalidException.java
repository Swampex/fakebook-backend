package ru.fakebook.pet.security.captcha;

import org.springframework.security.authentication.BadCredentialsException;

public class ReCaptchaInvalidException extends BadCredentialsException {

    public ReCaptchaInvalidException(String message) {
        super(message);
    }

}
