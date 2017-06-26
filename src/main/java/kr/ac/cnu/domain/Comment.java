package kr.ac.cnu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by rokim on 2017. 6. 5..
 */
@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idx;

    @ManyToOne(targetEntity = CnuUser.class)
    @JoinColumn(name="user_idx")
    private CnuUser cnuUser;

    @ManyToOne(targetEntity = Board.class)
    @JoinColumn(name="board_idx")
    @JsonIgnore
    private Board parentBoard;

    @ManyToOne(targetEntity = Comment.class)
    @JoinColumn(name="parent_idx")
    @JsonIgnore
    private Comment parentComment;
    private int depth;
    private int goodCount;
    private int badCount;

    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
