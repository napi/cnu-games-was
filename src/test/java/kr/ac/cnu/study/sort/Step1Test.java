package kr.ac.cnu.study.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by rokim on 2017. 5. 30..
 */
@Slf4j
public class Step1Test {
    @Test
    public void test() {
        List<Integer> list = Arrays.asList(3, 5, 2, 4, 1);

        List<Integer> sortedList = Step1.sort(list);
        System.out.println(sortedList);
    }
}