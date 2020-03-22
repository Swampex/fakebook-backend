package ru.fakebook.pet.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.postgresql.util.PSQLException;
import ru.fakebook.pet.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserRs {

    Integer resultCode;
    List<String> messages;
    private Long id;
    private String email;
    private String login;

    public static UserRs getOkRs() {
        return UserRs.builder()
                .resultCode(0)
                .messages(Collections.singletonList("Ok"))
                .build();
    }

    public static UserRs getExceptionRs(Exception e) {
        System.out.println(Arrays.toString(e.getStackTrace()));
        return UserRs.builder()
                .resultCode(1)
                .messages(Collections.singletonList(e.getMessage()))
                .build();
    }

    public static UserRs getNotAuthorizedRs() {
        return UserRs.builder()
                .resultCode(1)
                .messages(Collections.singletonList("You are not authorized"))
                .build();
    }

    public static UserRs getUserInfoRs(User user) {
        return UserRs.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .id(user.getId())
                .resultCode(0)
                .messages(Collections.singletonList("Ok"))
                .build();
    }
}
