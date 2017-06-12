package kr.ac.cnu.repository;

import kr.ac.cnu.domain.Board;

import java.util.List;

/**
 * Created by rokim on 2017. 6. 9..
 */
public interface MybatisRepositoryXml {
    List<Board> selectBoardList(int size);
}
