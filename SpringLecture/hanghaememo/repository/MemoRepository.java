package com.sparta.hanghaememo.repository;


import com.sparta.hanghaememo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

//    메모를 수정내림차순(수정된시간이 가장 최근인 순서대로) 찾으라는 메서드 생성
    List<Memo> findAllByOrderByModifiedAtDesc();
}