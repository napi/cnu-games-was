package kr.ac.cnu.service;

import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rokim on 2017. 5. 18..
 */
@Service
public class MemberService {
    @Autowired
    private UserRepository userRepository;

    public void insertMember(CnuUser cnuUser) {
        System.out.println(cnuUser);
        CnuUser result = userRepository.save(cnuUser);
        System.out.println(result);
    }
}
