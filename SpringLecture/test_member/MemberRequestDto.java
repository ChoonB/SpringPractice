package com.sparta.blogpostspring.test1;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String name;
    private String email;
    private String pw;
}
