package kr.ac.cnu.controller;

import kr.ac.cnu.domain.Board;
import kr.ac.cnu.repository.BoardRepository;
import kr.ac.cnu.repository.MybatisRepository;
import kr.ac.cnu.repository.MybatisRepositoryXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rokim on 2017. 6. 9..
 */
@RestController
@RequestMapping("/db")
public class DBController {
    /**
     * <JDBC를 직접 이용한다.>
     * 직접 Connection 을 열고,
     * SQL 쿼리를 이용하여 PreparedStatement를 만들고,
     * executeQuery 메서드를 호출해서 결과를 받아온다.
     *
     * 결과는 ResultSet 에 저장되고,
     * 개발자는 DB의 구조를 파악해서 Domain 객체에 직접 매핑하여준다.
     *
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/old", method = RequestMethod.GET)
    public List<Board> old(@RequestParam int size) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:cnu_2017", "sa", "");
        PreparedStatement pstmt = conn.prepareStatement("SELECT idx, title, contents FROM BOARD LIMIT ?");
        pstmt.setInt(1, size);
        ResultSet rs = pstmt.executeQuery();

        List<Board> boardList = new ArrayList<>();
        while (rs.next()) {
            Board board = new Board();
            board.setIdx(rs.getInt("idx"));
            board.setTitle(rs.getString("title"));
            board.setContents(rs.getString("contents"));
            boardList.add(board);
        }

        return boardList;
    }


    @Autowired private JdbcTemplate jdbcTemplate;

    /**
     * <Spring JDBC 추상화 : jdbcTemplate 사용>
     * 복잡하고 반복적인 JDBC의 저수준 API를 Spring 이 관리하여 준다
     * 전형적인 Spring의 JDBC 접근방법이다.
     *
     * @param size
     * @return
     */
    @RequestMapping(value = "/jdbctemplate", method = RequestMethod.GET)
    public List<Board> resttemplate(@RequestParam int size) {
        RowMapper<Board> mapper = new BeanPropertyRowMapper<>(Board.class);
        Object[] params = new Object[] {size};

        List<Board> boardList = jdbcTemplate.query("SELECT idx, title, contents FROM BOARD LIMIT ?", params, mapper);

        return boardList;
    }

    @Autowired private MybatisRepositoryXml mybatisRepositoryXml;

    /**
     * <Mybatis : persistence(영속성) 프레임워크> : MVC 패턴을 이해 하였는가?
     * 마이바티스는 JDBC로 처리하는 상당부분의 코드와 파라미터 설정및 결과 매핑을 대신해준다.
     *
     * @param size
     * @return
     */
    @RequestMapping(value = "/mybatis-xml", method = RequestMethod.GET)
    public List<Board> mybatisXml(@RequestParam int size) {

        List<Board> boardList = mybatisRepositoryXml.selectBoardList(size);

        return boardList;
    }


    @Autowired private MybatisRepository mybatisRepository;

    /**
     * <Mybatis> Spring boot 가 부러워지는..
     *
     * 쿼리 설정을 XML 이 아니라 Annotation 으로 한다.
     * 점점 설정파일을 없애는 최근 추세를 반영한다.
     *
     * @param size
     * @return
     */
    @RequestMapping(value = "/mybatis", method = RequestMethod.GET)
    public List<Board> mybatis(@RequestParam int size) {

        List<Board> boardList = mybatisRepository.findBoards(size);

        return boardList;
    }

    @Autowired private BoardRepository boardRepository;

    /**
     * <JPA with Hibernate!> : ORM 매핑 프레임워크
     *
     * @param size
     * @return
     */
    @RequestMapping(value = "/jpa", method = RequestMethod.GET)
    public List<Board> jpa(@RequestParam int size) {
        PageRequest pageRequest = new PageRequest(0, size);

        Page<Board> boardPage = boardRepository.findAll(pageRequest);

        return boardPage.getContent();
    }

}
