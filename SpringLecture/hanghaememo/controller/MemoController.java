package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

//  ModelAndView 객체를 생성해서 "/"로 기본주소로 들어오면 templates폴더의 index.html로 연결
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }


//  Post요청으로 들어와서 유저가 메모를 작성하고 버튼을 누르면 메모를 작성. dto를 매개변수로 받아 서비스에게 전달.
    @PostMapping("/api/memos")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto){
        return memoService.createMemo(requestDto);
    }

//    get으로 들어와서 메모를 조회(read)
    @GetMapping("/api/memos")
    public List<Memo> getMemos(){
        return memoService.getMemos();
    }

//    put으로 들어온 메모 수정.
    @PutMapping("/api/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        return memoService.update(id, requestDto);
    }

//  Delete로 들어온 메모 삭제 메서드
    @DeleteMapping("/api/memos/{id}")
    public Long deleteMemo(@PathVariable Long id){
        return memoService.deleteMemo(id);
    }

}
