package kr.ac.cnu.repository;

import kr.ac.cnu.domain.CnuUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rokim on 2017. 5. 18..
 */
public interface UserRepository extends JpaRepository<CnuUser, Integer> {
}
