package kr.ac.cnu.service;

import kr.ac.cnu.CnuGamesWasApplication;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by rokim on 2017. 6. 11..
 */

@RunWith(SpringRunner.class)
@SpringBootTest(value = "server.port:0", classes = CnuGamesWasApplication.class)
@WebAppConfiguration
public class ExampleTransactionServiceTest {
    @Autowired private ExampleTransactionService exampleTransactionService;
    @Autowired private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();

        CnuUser cnuUser = new CnuUser();
        cnuUser.setUserId("rokim");
        cnuUser.setName("김경석");
        cnuUser.setPoint(10000);
        userRepository.save(cnuUser);

        CnuUser cnuUser2 = new CnuUser();
        cnuUser2.setUserId("iye");
        cnuUser2.setName("예재형");
        cnuUser2.setPoint(5000);
        userRepository.save(cnuUser2);
    }

    @Test
    public void test() {
        CnuUser robin = userRepository.findByUserId("rokim");
        CnuUser ian = userRepository.findByUserId("iye");

        exampleTransactionService.dealUserPoint(robin, ian, 500);

        CnuUser afterRobin = userRepository.findByUserId("rokim");
        CnuUser afterIan = userRepository.findByUserId("iye");

        System.out.println(afterRobin);
        System.out.println(afterIan);
    }


}