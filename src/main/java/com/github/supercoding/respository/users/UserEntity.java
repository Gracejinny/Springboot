package com.github.supercoding.respository.users;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "userId")
@Builder
public class UserEntity {
    private Integer userId;
    private String userName;
    private String likeTravelPlace;
    private String phoneNum;
}
