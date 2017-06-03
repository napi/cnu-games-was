package kr.ac.cnu.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kr.ac.cnu.annotation.CnuLogin;
import kr.ac.cnu.configuration.UserContext;
import kr.ac.cnu.configuration.WebConfig;
import kr.ac.cnu.domain.Board;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.dto.BoardDTO;
import kr.ac.cnu.repository.BoardRepository;
import kr.ac.cnu.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

/**
 * Created by rokim on 2017. 5. 30..
 */
@RestController
@RequestMapping("/api/board")
public class BoardController {
    @Autowired private BoardRepository boardRepository;
    @Autowired private BoardService boardService;

    // TODO Delete This Method
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void insertBoard(@ApiIgnore CnuUser cnuUser, @RequestBody Board board) {
        board.setCnuUser(cnuUser);
        boardRepository.save(board);
    }

    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void insertBoard(@RequestBody BoardDTO boardDTO) {
        CnuUser cnuUser = UserContext.getUser();

        // TODO 삭제 예정
        Board board = new Board();
        board.setCnuUser(cnuUser);
        board.setTitle(boardDTO.getTitle());
        board.setContents(boardDTO.getContents());
        board.setCreatedAt(new Date());
        board.setUpdatedAt(new Date());
        boardRepository.save(board);

//        // TODO 이거 사용
//        boardService.insertBoard(cnuUser, boardDTO);
    }

    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Board> findBoardList() {
        List<Board> boardList = boardRepository.findAll();

        return boardList;
    }

    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "/{idx}", method = RequestMethod.GET)
    public Board seeBoard(@PathVariable int idx) {
        Board board = boardRepository.findByIdx(idx);

        return board;
    }

}
