package kr.ac.cnu.service;

import kr.ac.cnu.configuration.UserContext;
import kr.ac.cnu.configuration.WebConfig;
import kr.ac.cnu.domain.Board;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.dto.BoardDTO;
import kr.ac.cnu.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by rokim on 2017. 5. 31..
 */
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Board insertBoard(CnuUser cnuUser, BoardDTO boardDTO) {
        Board board = new Board();
        board.setCnuUser(cnuUser);
        board.setTitle(boardDTO.getTitle());
        board.setContents(boardDTO.getContents());
        board.setCreatedAt(new Date());
        board.setUpdatedAt(new Date());
        board.setDel(false);

        return boardRepository.save(board);
    }

    public Board deleteBoard(CnuUser cnuUser, int idx) {
        Board board = boardRepository.findByIdxAndCnuUser(idx, cnuUser);
        if(board == null)
            return null;
        board.setDel(true);
        return boardRepository.save(board);
    }
}
