package ru.fakebook.pet.security.captcha;

public interface ICaptchaService {

    void processResponse(String response);
}
