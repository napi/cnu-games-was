package kr.ac.cnu.study.sort;

import kr.ac.cnu.study.model.Student;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rokim on 2017. 6. 4..
 */
public class Step3Test {
    private List<Student> list;

    @Before
    public void before() {
        list = Arrays.asList(
                new Student("Robin", 100),
                new Student("David", 50),
                new Student("Ian", 80),
                new Student("Tony", 20),
                new Student("Sam", 40)
        );
    }

    @Test
    public void 오름차순_테스트() {
    }

    @Test
    public void 내림차순_테스트() {

    }

}