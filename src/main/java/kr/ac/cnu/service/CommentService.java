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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
        if (!isCommentValidationRight(commentDTO)) {
            throw new BadRequestException();
        }

        Comment comment = new Comment();
        comment.setCnuUser(cnuUser);
        comment.setParentBoard(boardRepository.findByIdxAndIsDel(commentDTO.getBoardIdx(), false));
        Comment parentComment = commentRepository.findByIdx(commentDTO.getParentIdx());

        int depth = 1;
        if (parentComment != null) {
            depth += parentComment.getDepth();
            comment.setParentComment(parentComment);
        }

        comment.setDepth(depth);
        comment.setComment(commentDTO.getComment());
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());
        comment.setGoodCount(0);
        comment.setBadCount(0);

        return commentRepository.save(comment);
    }

    public void deleteComment(int idx, CnuUser cnuUser) {
        Comment comment = commentRepository.findByIdxAndCnuUser(idx, cnuUser);
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
        if (commentDTO.getParentIdx() == 0) {
            return false;
        }

        Comment parentComment = commentRepository.findByIdx(commentDTO.getParentIdx());
        if (parentComment == null || parentComment.getParentBoard().getIdx() != commentDTO.getBoardIdx()) {
            return true;
        }

        return false;
    }

    public boolean isRecommendAndNoRecommendServiceRight(Comment comment, CnuUser cnuUser) {
        Calendar calendar = Calendar.getInstance();
        Calendar latestGoodAndBadCalendar = Calendar.getInstance();
        latestGoodAndBadCalendar.setTime(cnuUser.getLastestGoodAndBadAt());

        if(isCnuUserDayLowerThanNowDay(calendar, latestGoodAndBadCalendar)) {
            cnuUser.setLastestGoodAndBadAt(calendar.getTime());
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

    //코멘트 보기
    public List<Comment> viewComment(Board parentBoard) {
        List<Comment> commentList = commentRepository.findAllByParentBoard(parentBoard);
        Comparator<Comment> comparator = new Comparator<Comment>(){

            @Override
            public int compare(Comment o1, Comment o2) {
                return o1.getCreatedAt().before(o2.getCreatedAt()) ? -1 : o1.getCreatedAt().after(o2.getCreatedAt()) ? 1:0;
            }
        };

        if(!commentList.isEmpty()) {
            Collections.sort(commentList, comparator);
        }
        return commentList;
    }
}