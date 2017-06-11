package kr.ac.cnu.service;

import kr.ac.cnu.CnuGamesWasApplication;
import kr.ac.cnu.controller.DBController;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by rokim on 2017. 6. 11..
 */

@RunWith(SpringRunner.class)
@SpringBootTest(value = "server.port:0", classes = CnuGamesWasApplication.class)
@WebAppConfiguration
public class ExampleAopServiceTest {
    @Autowired private ExampleAopService exampleAopService;
    @Autowired private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();

        CnuUser cnuUser = new CnuUser();
        cnuUser.setUserId("rokim");
        cnuUser.setName("김경석");
        cnuUser.setEmail("rokim@riotgames.com");
        cnuUser.setPoint(10000);
        userRepository.save(cnuUser);

        CnuUser cnuUser2 = new CnuUser();
        cnuUser2.setUserId("iye");
        cnuUser2.setName("예재형");
        cnuUser2.setEmail("iye@riotgames.com");
        cnuUser2.setPoint(5000);
        userRepository.save(cnuUser2);
    }

    @Test
    public void test() throws Exception {
        CnuUser robin = userRepository.findByUserId("rokim");
        CnuUser ian = userRepository.findByUserId("iye");

        exampleAopService.dealUserPoint(robin, ian, 500);

        CnuUser afterRobin = userRepository.findByUserId("rokim");
        CnuUser afterIan = userRepository.findByUserId("iye");

        System.out.println(afterRobin);
        System.out.println(afterIan);

//        getUser("rokim");
    }

//    private void getUser(String userId) throws Exception {
//        Connection conn = DriverManager.getConnection("jdbc:h2:mem:cnu_2017", "sa", "");
//        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM CNU_USER WHERE user_id = ?");
//        pstmt.setString(1, userId);
//        ResultSet rs = pstmt.executeQuery();
//
//        while(rs.next()) {
//            System.out.println(rs.getString("email"));
//        }
//
//    }
}