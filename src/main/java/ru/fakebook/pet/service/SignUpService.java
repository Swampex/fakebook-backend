package ru.fakebook.pet.service;


import org.postgresql.util.PSQLException;
import ru.fakebook.pet.form.UserForm;

public interface SignUpService {
    void signUp(UserForm userForm) throws PSQLException;
}