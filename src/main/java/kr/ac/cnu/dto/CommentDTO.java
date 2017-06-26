package kr.ac.cnu.dto;

import lombok.Data;

/**
 * Created by rokim on 2017. 6. 5..
 */
@Data
public class CommentDTO {
    private int boardIdx;
    private int parentIdx;

    private String comment;
}
