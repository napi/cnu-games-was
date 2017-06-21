package kr.ac.cnu.service;

import kr.ac.cnu.domain.Board;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.Comment;
import kr.ac.cnu.dto.CommentDTO;
import kr.ac.cnu.exception.BadRequestException;
import kr.ac.cnu.repository.BoardRepository;
import kr.ac.cnu.repository.CommentRepository;
import kr.ac.cnu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by rokim on 2017. 6. 5..
 */
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    public Comment insertComment(CnuUser cnuUser, CommentDTO commentDTO) {
        // TODO Validation 체크 하는 부분이 빠져 있다
        // board 가 현재 존재하는지,
        // parent comment 가 같은 board의 Idx 값을 가지는지,
        // depth 는 parentComment 의 depth +1 로 되어야 하는지 등등.

        if (isCommentValidationRight(commentDTO)) {
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
            comment.setGoodCount(0);
            comment.setBadCount(0);

            return commentRepository.save(comment);
        } else {
            return null;
        }
    }

    public void deleteComment(int idx) {
        Comment comment = commentRepository.findByIdx(idx);
        commentRepository.delete(comment);
    }

    public void recommendComment(int idx, CnuUser cnuUser){
        Comment comment = commentRepository.findByIdx(idx);

        if(isRecommendAndNoRecommendServiceRight(comment, cnuUser)) {
            comment.setBadCount(comment.getGoodCount() + 1);
            cnuUser.setOneDayGoodAndBadCount(cnuUser.getOneDayGoodAndBadCount()+1);
            commentRepository.save(comment);
            userRepository.save(cnuUser);
        }else {
            throw new BadRequestException();
        }
    }

    public void noRecommendComment(int idx, CnuUser cnuUser){
        Comment comment = commentRepository.findByIdx(idx);

        if(isRecommendAndNoRecommendServiceRight(comment, cnuUser)) {
            comment.setBadCount(comment.getBadCount() + 1);
            cnuUser.setOneDayGoodAndBadCount(cnuUser.getOneDayGoodAndBadCount()+1);
            commentRepository.save(comment);
            userRepository.save(cnuUser);
        }else {
            throw new BadRequestException();
        }
    }

    public boolean isCommentValidationRight(CommentDTO commentDTO) {
        if (isBoardNotExist(commentDTO)) {
            return false;
        } else if (isChildAndParentCommentHasDifferentBoardIdx(commentDTO)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isBoardNotExist(CommentDTO commentDTO) {
        return !boardRepository.exists(commentDTO.getBoardIdx());
    }

    public boolean isChildAndParentCommentHasDifferentBoardIdx(CommentDTO commentDTO) {
        if (commentRepository.findByIdx(commentDTO.getParentIdx()).getBoardIdx()
                != commentDTO.getBoardIdx()) {
            return true;
        } else {
            return false;
        }
    }

    public int getParentDepth(CommentDTO commentDTO) {
        return commentRepository.findByIdx(commentDTO.getParentIdx()).getDepth();
    }

    public boolean isRecommendAndNoRecommendServiceRight(Comment comment, CnuUser cnuUser) {
        Calendar calendar = Calendar.getInstance();

        if(isCnuUserDayLowerThanNowDay(calendar, cnuUser.getLastestGoodAndBadAt())) {
            cnuUser.setLastestGoodAndBadAt(calendar);
            cnuUser.setOneDayGoodAndBadCount(0);
            userRepository.save(cnuUser);
        }

        if(comment != null && cnuUser.getOneDayGoodAndBadCount() < 5) {
            return true;
        }else {
            return false;
        }
    }

    public boolean isCnuUserDayLowerThanNowDay(Calendar nowCalendar, Calendar cnuUserCalendar) {
        if(nowCalendar.get(Calendar.YEAR) > cnuUserCalendar.get(Calendar.YEAR) ) {
            return true;
        }

        if(nowCalendar.get(Calendar.MONTH) > cnuUserCalendar.get(Calendar.MONTH)) {
            return true;
        }

        if(nowCalendar.get(Calendar.DATE) > cnuUserCalendar.get(Calendar.DATE)) {
            return true;
        }

        return false;
    }
}
