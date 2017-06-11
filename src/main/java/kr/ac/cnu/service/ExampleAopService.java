package kr.ac.cnu.service;

import kr.ac.cnu.aspect.AesAspect;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * Created by rokim on 2017. 6. 11..
 */
@Service
public class ExampleAopService {
    @Autowired private UserRepository userRepository;

//    @Transactional
    public void dealUserPoint(CnuUser from, CnuUser to, long count) {
        from.setPoint(from.getPoint() - count);
        to.setPoint(to.getPoint() + count);

        userRepository.save(from);
        userRepository.save(to);
    }

//    @PostConstruct
//    public void temp() {
//        CnuUser cnuUser = new CnuUser();
//        cnuUser.setUserId("rokim");
//        cnuUser.setName("김경석");
//        cnuUser.setEmail("rokim@riotgames.com");
//        cnuUser.setPoint(10000);
//        userRepository.save(cnuUser);
//    }
}
