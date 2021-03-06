package kr.ac.cnu.repository;

import kr.ac.cnu.domain.Board;
import kr.ac.cnu.domain.CnuUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rokim on 2017. 5. 31..
 */
public interface BoardRepository extends JpaRepository<Board, Integer>{
    Page<Board> findByIsDel(boolean isDel, Pageable pageable);
    Board findByIdx(int idx);
    Board findByIdxAndIsDel(int idx, boolean isDel);
    Board findByIdxAndCnuUser(int idx, CnuUser cnuUser);
}
