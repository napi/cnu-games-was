package kr.ac.cnu.repository;

import kr.ac.cnu.domain.CnuMember;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rokim on 2017. 5. 18..
 */
public interface MemberRepository extends JpaRepository<CnuMember, Integer> {
}
