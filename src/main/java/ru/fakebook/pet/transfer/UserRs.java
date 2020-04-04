package ru.fakebook.pet.transfer;

import lombok.*;
import ru.fakebook.pet.model.User;

import java.util.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserRs {

    @NonNull
    Integer resultCode;
    @NonNull
    Map<String, String> messages;
    private Long id;
    private String email;
    private String login;

    public static UserRs getOkRs(Map ...messages) {
        Map<String, String> rsMessages = new HashMap<>();
        if (messages.length != 0) {
            rsMessages.putAll(messages[0]);
        }
        rsMessages.put("status", "Ok");

        return UserRs.builder()
                .resultCode(0)
                .messages(rsMessages)
                .build();
    }

    public static UserRs getExceptionRs(Exception e) {
        System.out.println(Arrays.toString(e.getStackTrace()));
        return UserRs.builder()
                .resultCode(1)
                .messages(Collections.singletonMap("error", e.getMessage()))
                .build();
    }

    public static UserRs getNotAuthorizedRs() {
        return UserRs.builder()
                .resultCode(1)
                .messages(Collections.singletonMap("error", "You are not authorized"))
                .build();
    }

    public static UserRs getUserInfoRs(User user) {
        return UserRs.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .id(user.getId())
                .resultCode(0)
                .messages(Collections.singletonMap("status", "Ok"))
                .build();
    }
}
