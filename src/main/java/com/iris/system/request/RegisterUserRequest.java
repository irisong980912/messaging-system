package com.iris.system.request;

import com.iris.system.enums.Gender;
import lombok.Data;


/*
Lombook automatically add functions to the class
@EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
Can overrride the method
*/

@Data
public class RegisterUserRequest {
    private String username; // alphanumeric
    private String email;
    private String nickname; // emoji
    private String password;
    private String repeatPassword;
    private String address;
    private Gender gender;
}
