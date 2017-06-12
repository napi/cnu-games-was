package kr.ac.cnu.repository;


import kr.ac.cnu.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by rokim on 2017. 6. 9..
 */
@Mapper
public interface MybatisRepository {
    @Select("SELECT idx, title, contents FROM BOARD LIMIT #{size}")
    List<Board> findBoards(@Param("size") int size);
}
