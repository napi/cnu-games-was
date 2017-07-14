package kr.ac.cnu.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by rokim on 2017. 5. 31..
 */
@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idx;

    @ManyToOne(targetEntity = CnuUser.class)
    @JoinColumn(name="user_idx")
    private CnuUser cnuUser;

    private String title;
    private String contents;

    @OneToMany(mappedBy = "parentBoard")
    @JsonIgnore
    private List<Comment> commentList;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private boolean isDel;
    private int countLike;
    private int countDisLike;

    @Override
    public String toString() {
        return String.format("Board : %s", this.idx);
    }

    public int getCommentCount() {
        return commentList.size();
    }
}
