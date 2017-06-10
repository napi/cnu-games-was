package kr.ac.cnu.controller;

import io.swagger.annotations.ApiImplicitParam;
import kr.ac.cnu.annotation.CnuLogin;
import kr.ac.cnu.configuration.UserContext;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.dto.CommentDTO;
import kr.ac.cnu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

}
