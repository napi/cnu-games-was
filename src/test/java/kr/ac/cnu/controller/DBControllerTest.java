package kr.ac.cnu.controller;

import kr.ac.cnu.CnuGamesWasApplication;
import kr.ac.cnu.domain.Board;
import kr.ac.cnu.repository.BoardRepository;
import kr.ac.cnu.repository.MybatisRepositoryXml;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by rokim on 2017. 6. 12..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(value = "server.port:0", classes = CnuGamesWasApplication.class)
@WebAppConfiguration
public class DBControllerTest {
    @Autowired private MybatisRepositoryXml mybatisRepositoryXml;
    @Autowired private BoardRepository boardRepository;

    @Test
    public void Mybatis_Cache_테스트() {
        Board board = new Board();
        board.setTitle("제목.");
        board.setContents("내용.");

        /**
         * 등록 후 조회
         */
        // 게시물 insert
        board = boardRepository.save(board);
        // 입력된 게시물을 select
        List<Board> resultList = mybatisRepositoryXml.selectBoardList(10);
        System.out.println(resultList.get(0).getTitle());


        /**
         * 수정 후 조회
         */
        // 게시물을 Update
        board.setTitle("제목이 바꼈슈");
        boardRepository.save(board);
        // 입력된 게시물을 select
        resultList = mybatisRepositoryXml.selectBoardList(10);
        System.out.println(resultList.get(0).getTitle());

    }
}