package com.solux.greenish.service;

import com.solux.greenish.domain.Member;
import com.solux.greenish.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 아이디로 회원 정보 조회
        Member member = (Member) memberRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        // Member 엔티티에 userId, password, email 필드가 있다고 가정
        String email = member.getEmail(); // Member 엔티티에서 이메일 가져오기

        // Spring Security User 객체 생성 및 반환
        return new org.springframework.security.core.userdetails.User(
                member.getUserId(), // 사용자명 설정
                "", // 패스워드 필드에 빈 문자열 설정 (패스워드 없음)
                Collections.emptyList() // 역할이 없음을 나타내는 빈 리스트 설정
        );
    }
}
