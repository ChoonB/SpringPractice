package com.sparta.quiz2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> findAllPost() {
        List<Post> postList = postRepository.findAll();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        if(!postList.isEmpty()){
            for (Post post : postList) {
                postResponseDtoList.add(new PostResponseDto(post));
            }
        }
        return postResponseDtoList;
    }
}