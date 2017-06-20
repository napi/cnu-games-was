package kr.ac.cnu.controller;

import io.swagger.annotations.ApiImplicitParam;
import kr.ac.cnu.annotation.CnuLogin;
import kr.ac.cnu.configuration.UserContext;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.dto.CommentDTO;
import kr.ac.cnu.exception.BadRequestException;
import kr.ac.cnu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ApiImplicitParam(name = "token", value = " client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = {"/idx", "/isRecommend"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void recommendComment(@PathVariable int idx, @PathVariable boolean isRecommend) {
        CnuUser cnuUser = UserContext.getUser();

        if(isRecommend ) {
            commentService.recommendComment(idx, cnuUser);
        }else {
            throw new BadRequestException();
        }
    }

    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = {"/idx", "/isNotRecommend"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void noRecommendComment(@PathVariable int idx, @PathVariable boolean isNotRecommend) {
        CnuUser cnuUser = UserContext.getUser();

        if(isNotRecommend) {
            commentService.noRecommendComment(idx, cnuUser);
        }else {
            throw new BadRequestException();
        }
    }

    @CnuLogin
    @ApiImplicitParam(name = "token", value = "Facebook client access token", required = true, dataType = "string", paramType = "header", defaultValue = "")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@RequestBody CommentDTO commentDTO) {

        commentService.deleteComment(commentDTO.getIdx());
    }
}
