package kr.ac.cnu.controller;

import io.swagger.annotations.ApiImplicitParam;
import kr.ac.cnu.annotation.CnuLogin;
import kr.ac.cnu.configuration.UserContext;
import kr.ac.cnu.domain.Board;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.dto.BoardDTO;
import kr.ac.cnu.exception.BadRequestException;
import kr.ac.cnu.repository.BoardRepository;
import kr.ac.cnu.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by rokim on 2017. 5. 30..
 */
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createBoard(@RequestBody BoardDTO boardDTO) {
        CnuUser cnuUser = UserContext.getUser();
        boardService.insertBoard(cnuUser, boardDTO);
    }

    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateBoard(@PathVariable int idx, @RequestBody String contents) {
        CnuUser cnuUser = UserContext.getUser();
        boardService.updateBoard(cnuUser, idx, contents);
    }

    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "/{idx}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteBoard(@PathVariable int idx) {
        CnuUser cnuUser = UserContext.getUser();
        Board board = boardService.deleteBoard(cnuUser, idx);
        if (board == null) {
            throw new BadRequestException();
        }
    }


    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "/like/{idx}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public void likeBoard(@PathVariable int idx) {
        CnuUser cnuUser = UserContext.getUser();
        boolean can = boardService.likeBoard(cnuUser, idx);
        if (can == false) {
            throw new BadRequestException();
        }

    }
    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "/dis/{idx}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public void disLikeBoard(@PathVariable int idx) {
        CnuUser cnuUser = UserContext.getUser();
        boolean can = boardService.disLikeBoard(cnuUser, idx);
        if (can == false) {
            throw new BadRequestException();
        }

    }


    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Board> findBoardList(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage;
    }

    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "/{idx}", method = RequestMethod.GET)
    public Board findBoard(@PathVariable int idx) {
        Board board = boardRepository.findByIdx(idx);
        if (board == null) {
            throw new BadRequestException();
        }
        return board;
    }
    
    public void editBoard(){
    	
    }

}
