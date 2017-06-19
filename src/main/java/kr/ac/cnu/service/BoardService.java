package kr.ac.cnu.service;

import kr.ac.cnu.configuration.UserContext;
import kr.ac.cnu.configuration.WebConfig;
import kr.ac.cnu.domain.Board;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.dto.BoardDTO;
import kr.ac.cnu.repository.BoardRepository;
import kr.ac.cnu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rokim on 2017. 5. 31..
 */
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    public Board insertBoard(CnuUser cnuUser, BoardDTO boardDTO) {
        Board board = new Board();
        board.setCnuUser(cnuUser);
        board.setTitle(boardDTO.getTitle());
        board.setContents(boardDTO.getContents());
        board.setCreatedAt(new Date());
        board.setUpdatedAt(new Date());
        board.setDel(false);
        board.setCountLike(0);

        return boardRepository.save(board);
    }

    public Board deleteBoard(CnuUser cnuUser, int idx) {
        Board board = boardRepository.findByIdxAndCnuUser(idx, cnuUser);
        if(board == null)
            return null;
        board.setDel(true);
        return boardRepository.save(board);
    }

    public boolean likeBoard(CnuUser cnuUser, int idx) {
        Board board = initBoardCondition(cnuUser, idx);
        
        if (cnuUser.getCountLike()<3){
            board.setCountLike(board.getCountLike()+1);
            cnuUser.setCountLike(cnuUser.getCountLike()+1);
            boardRepository.save(board);
            userRepository.save(cnuUser);
            return true;
        }else{
            return false;
        }
    }

    public boolean disLikeBoard(CnuUser cnuUser, int idx) {
        Board board = initBoardCondition(cnuUser, idx);

        if (cnuUser.getCountDisLike()<3){
            board.setCountDisLike(board.getCountDisLike()+1);
            cnuUser.setCountDisLike(cnuUser.getCountDisLike()+1);
            boardRepository.save(board);
            userRepository.save(cnuUser);
            return true;
        }else{
            return false;
        }
    }

    public Board initBoardCondition(CnuUser cnuUser, int idx) {
        Board board = boardRepository.findByIdx(idx);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date userDate=null;
        Date currentDate=null;
        try {
            userDate = dateFormat.parse(dateFormat.format(cnuUser.getLikeAt()));
            currentDate = dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (userDate.compareTo(currentDate)<0){
            cnuUser.setLikeAt(new Date());
            cnuUser.setCountDisLike(0);
            cnuUser.setCountLike(0);
        }
        return board;
    }
}
