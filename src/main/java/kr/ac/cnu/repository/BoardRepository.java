package kr.ac.cnu.repository;

import kr.ac.cnu.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rokim on 2017. 5. 31..
 */
public interface BoardRepository extends JpaRepository<Board, Integer>{
    Board findByIdx(int idx);
}
