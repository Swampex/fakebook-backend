package ru.fakebook.pet.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.fakebook.pet.model.User;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AuthMeDto {

    private Long id;
    private String email;
    private String login;
    private Integer resultCode;
    private List messages;

    public static AuthMeDto from(User user) {
        return AuthMeDto.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .id(user.getId())
                .resultCode(0)
                .build();
    }

    public static AuthMeDto from(Integer resultCode, List messages) {
        return AuthMeDto.builder()
                .resultCode(resultCode)
                .messages(messages)
                .build();
    }

}
