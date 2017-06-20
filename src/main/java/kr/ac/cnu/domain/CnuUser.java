package kr.ac.cnu.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;
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
}
