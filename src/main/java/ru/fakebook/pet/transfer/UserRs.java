package ru.fakebook.pet.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserRs {

    Integer resultCode;
    List<String> messages;

    public static UserRs getOkRs() {
        return UserRs.builder()
                .resultCode(0)
                .messages(Collections.singletonList("Ok"))
                .build();
    }

    public static UserRs getNotAuthorizedRs() {
        return UserRs.builder()
                .resultCode(1)
                .messages(Collections.singletonList("You are not authorized"))
                .build();
    }
}
