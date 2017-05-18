package kr.ac.cnu.service;

import kr.ac.cnu.domain.CnuMember;
import kr.ac.cnu.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by rokim on 2017. 5. 18..
 */
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public void insertMember(CnuMember cnuMember) {
        System.out.println(cnuMember);
        CnuMember result = memberRepository.save(cnuMember);
        System.out.println(result);
    }
}
