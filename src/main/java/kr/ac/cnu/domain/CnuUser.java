package kr.ac.cnu.domain;

import kr.ac.cnu.annotation.AesEncrypt;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rokim on 2017. 5. 18..
 */
@Data
@Entity
public class CnuUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idx;

    @Column(unique = true)
    private String userId;

    private String name;
    private String email;
    private String gender;
    private String pictureUrl;

    private int oneDayGoodAndBadCount;
    private Calendar lastestGoodAndBadAt;

    private long point;

    private int countLike;

    private int countDisLike;

    @Temporal(TemporalType.TIMESTAMP)
    private Date likeAt;

}
