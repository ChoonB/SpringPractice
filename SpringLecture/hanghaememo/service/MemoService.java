package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

//    메모 생성 메서드. 컨트롤러에게 dto를 받아서 유저명과 content를 받아 메모 객체를 만들고
//    Repository에 저장한 뒤 반환.
    @Transactional
    public Memo createMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);
        memoRepository.save(memo);
        return memo;
    }

//    memoRepository에 해당 메서드 실행
    @Transactional(readOnly = true)     //@Transactional 은 스프링프레임워크꺼 선택해야 readOnly가능
    public List<Memo> getMemos() {
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }

//    Repository에 해당 id에 맞는 메모가 존재하는지 확인하고 없으면 예외처리
//    있으면 memo 객체가 만들어지고 memo의 update 메서드 실행
    @Transactional
    public Long update(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        memo.update(requestDto);
        return memo.getId();
    }

//  메모 삭제 메서드. id값으로 삭제
    @Transactional
    public Long deleteMemo(Long id) {
        memoRepository.deleteById(id);
        return id;
    }
}
