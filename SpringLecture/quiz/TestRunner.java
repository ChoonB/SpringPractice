package com.sparta.quiz;


import com.sparta.quiz.entity.BookStore;
import com.sparta.quiz.entity.Member;
import com.sparta.quiz.repository.BookRepository;
import com.sparta.quiz.repository.BookStoreRepository;
import com.sparta.quiz.repository.MemberRepository;
import com.sparta.quiz.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class TestRunner implements ApplicationRunner {

    private final BookRepository bookRepository;
    private final BookStoreRepository bookStoreRepository;
    private final MemberRepository memberRepository;
    private final PurchaseRepository purchaseRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member member = new Member("1", "1", "2", "3", "5");
        Member member2 = new Member("1", "1", "2", "3", "5");
        memberRepository.save(member);
        memberRepository.save(member2);
        BookStore bookStore = new BookStore("2", "2");

        List<Member> memberList= new ArrayList<>();
        memberList.add(member);
        memberList.add(member2);
        bookStore.setMemberList(memberList);
        bookStoreRepository.save(bookStore);

        List<BookStore> all = bookStoreRepository.findAll();
        System.out.println(all.toString());

    }
}
