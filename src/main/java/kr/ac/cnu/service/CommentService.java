package kr.ac.cnu.service;

import kr.ac.cnu.domain.Board;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.Comment;
import kr.ac.cnu.dto.CommentDTO;
import kr.ac.cnu.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by rokim on 2017. 6. 5..
 */
@Service
public class CommentService {
    @Autowired private CommentRepository commentRepository;

    public Comment insertComment(CnuUser cnuUser, CommentDTO commentDTO) {
        // TODO Validation 체크 하는 부분이 빠져 있다
        // board 가 현재 존재하는지,
        // parent comment 가 같은 board의 Idx 값을 가지는지,
        // depth 는 parentComment 의 depth +1 로 되어야 하는지 등등.

        Comment comment = new Comment();
        comment.setCnuUser(cnuUser);
        comment.setBoardIdx(commentDTO.getBoardIdx());
        comment.setParentIdx(commentDTO.getParentIdx());
        comment.setDepth(1);
        comment.setComment(commentDTO.getComment());
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());

        return commentRepository.save(comment);
    }

    public void deleteComment(int idx){
        Comment comment = commentRepository.findByIdx(idx);
        commentRepository.delete(comment);
    }
}
