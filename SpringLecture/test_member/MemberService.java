package com.sparta.blogpostspring.test1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

//    id로 회원 조회 메서드
    public MemberResponseDto findMember(Long id){
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new NullPointerException("회원 상세 조회 실패")
        );
        return new MemberResponseDto(member);
    }

//    회원 전체 조회 메서드
    public List<MemberResponseDto> findAllMember() {
        List<MemberResponseDto> mrdList = new ArrayList<>();
        List<Member> mbList = memberRepository.findAllByOrderByIdIdAsc();
        for (Member member : mbList) {
            mrdList.add(new MemberResponseDto(member));
        }
        return mrdList;
    }

//    회원 생성 메서드
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        Member member = new Member(memberRequestDto);
        memberRepository.save(member);
        return new MemberResponseDto(member);
    }
}
