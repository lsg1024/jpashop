package jpabook.jpashop.service;

import jpabook.jpashop.domin.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 트랜잭션 내에서 처리해야된다 readOnly 의 경우 읽기 경우에 사용
@RequiredArgsConstructor // 생성자 자동 생성 주입
public class MemberService {

    // 생성자 인젝션
    private final MemberRepository memberRepository;

//    @Autowired // 단일일 경우 필요 없음
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 회원 검증 로직 - 중복 체크
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 단일 회훤 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
