package kr.ac.cnu.service;

import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.facebook.FacebookUser;
import kr.ac.cnu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by rokim on 2017. 5. 18..
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public synchronized CnuUser findAndCreateCnuUser(FacebookUser facebookUser) {
        CnuUser cnuUser = userRepository.findByUserId(facebookUser.getUserId());
        if (cnuUser == null) {
            cnuUser = new CnuUser();
            cnuUser.setUserId(facebookUser.getUserId());
            cnuUser.setEmail(facebookUser.getEmail());
            cnuUser.setPictureUrl(facebookUser.getPicture());
            cnuUser.setName(facebookUser.getName());
            cnuUser.setGender(facebookUser.getGender());
            cnuUser.setLikeAt(new Date());
            cnuUser.setCountDisLike(0);
            cnuUser.setCountLike(0);
            cnuUser = userRepository.save(cnuUser);
        }
        return cnuUser;

    }
}
