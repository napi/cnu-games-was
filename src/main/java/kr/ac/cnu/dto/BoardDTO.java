package kr.ac.cnu.dto;

import kr.ac.cnu.domain.CnuUser;
import lombok.Data;

import javax.persistence.CascadeType;
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
public class BoardDTO {
    private int idx;

    private String title;
    private String contents;
}
