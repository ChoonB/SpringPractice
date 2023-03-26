package com.example.filetest;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class BoardRequestDto {

    private String title;
    private String content;
    private MultipartFile file;
}
