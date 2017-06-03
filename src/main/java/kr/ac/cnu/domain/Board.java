package kr.ac.cnu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

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

    private Date createdAt;
    private Date updatedAt;
}
