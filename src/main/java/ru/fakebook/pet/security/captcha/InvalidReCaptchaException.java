package ru.fakebook.pet.security.captcha;

import org.springframework.security.authentication.BadCredentialsException;

public final class InvalidReCaptchaException extends BadCredentialsException {

    public InvalidReCaptchaException(String message) {
        super(message);
    }
}
