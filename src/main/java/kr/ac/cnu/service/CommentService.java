package kr.ac.cnu.service;

import kr.ac.cnu.domain.Board;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.Comment;
import kr.ac.cnu.dto.CommentDTO;
import kr.ac.cnu.exception.BadRequestException;
import kr.ac.cnu.repository.BoardRepository;
import kr.ac.cnu.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by rokim on 2017. 6. 5..
 */
@Service
public class CommentService {
    @Autowired private CommentRepository commentRepository;
    @Autowired private BoardRepository boardRepository;

    public Comment insertComment(CnuUser cnuUser, CommentDTO commentDTO) {
        // TODO Validation 체크 하는 부분이 빠져 있다
        // board 가 현재 존재하는지,
        // parent comment 가 같은 board의 Idx 값을 가지는지,
        // depth 는 parentComment 의 depth +1 로 되어야 하는지 등등.

        if(isCommentValidationRight(commentDTO)) {
            Comment comment = new Comment();
            comment.setCnuUser(cnuUser);
            comment.setBoardIdx(commentDTO.getBoardIdx());
            comment.setParentIdx(commentDTO.getParentIdx());
            comment.setDepth(
                    getParentDepth(commentDTO) + 1
            );
            comment.setComment(commentDTO.getComment());
            comment.setCreatedAt(new Date());
            comment.setUpdatedAt(new Date());

            return commentRepository.save(comment);
        }else {
            return null;
        }
    }

    public void deleteComment(int idx){
        Comment comment = commentRepository.findByIdx(idx);
        commentRepository.delete(comment);
    }

    public boolean isCommentValidationRight(CommentDTO commentDTO) {
        if(isBoardNotExist(commentDTO)) {
            return false;
        }else if(isChildAndParentCommentHasDifferentBoardIdx(commentDTO)) {
            return false;
        }else {
            return true;
        }
    }

    public boolean isBoardNotExist(CommentDTO commentDTO) {
        return !boardRepository.exists(commentDTO.getBoardIdx());
    }

    public boolean isChildAndParentCommentHasDifferentBoardIdx(CommentDTO commentDTO) {
        if(commentRepository.findByIdx(commentDTO.getParentIdx()).getBoardIdx()
                != commentDTO.getBoardIdx()  ) {
            return true;
        }else {
            return false;
        }
    }

    public int getParentDepth(CommentDTO commentDTO) {
        return commentRepository.findByIdx(commentDTO.getParentIdx()).getDepth();
    }

    //코멘트 보기
    public List<Comment> viewComment() {
        List<Comment> commentList;
        Comparator<Comment> comparator = new Comparator<Comment>() {

            @Override
            public int compare(Comment o1, Comment o2) {
                return o1.getIdx() - o2.getIdx();
            }
        };
        commentList = commentRepository.findAll();

        if(!commentList.isEmpty()) {
            Collections.sort(commentList, comparator);
            return commentList;
        }else {
            throw new BadRequestException();
        }
    }
}