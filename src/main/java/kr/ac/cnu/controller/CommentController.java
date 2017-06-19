package kr.ac.cnu.controller;

import io.swagger.annotations.ApiImplicitParam;
import kr.ac.cnu.annotation.CnuLogin;
import kr.ac.cnu.configuration.UserContext;
import kr.ac.cnu.domain.Board;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.Comment;
import kr.ac.cnu.dto.CommentDTO;
import kr.ac.cnu.repository.BoardRepository;
import kr.ac.cnu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by rokim on 2017. 6. 5..
 */
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired private CommentService commentService;


    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createComment(@RequestBody CommentDTO commentDTO) {
        CnuUser cnuUser = UserContext.getUser();

        commentService.insertComment(cnuUser, commentDTO);
    }

   @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@RequestBody CommentDTO commentDTO) {

        commentService.deleteComment(commentDTO.getIdx());
    }

    //전체보기
    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "/{boardIdx}", method = RequestMethod.GET)
    public List<Comment> viewEntireComment(@PathVariable("boardIdx") int boardIdx) {
        BoardController boardController = new BoardController();
        Board board = boardController.findBoard(boardIdx);

        return commentService.viewComment(board.getIdx());
    }

}
