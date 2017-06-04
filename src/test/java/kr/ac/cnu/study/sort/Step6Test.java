package kr.ac.cnu.study.sort;

import kr.ac.cnu.study.model.Student;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by rokim on 2017. 5. 30..
 */
public class Step6Test {
    private List<Integer> integerList;
    private List<Student> studentList;

    @Before
    public void before() {
        integerList = Arrays.asList(3, 5, 2, 4, 1);
        studentList = Arrays.asList(
                new Student("Robin", 100),
                new Student("David", 50),
                new Student("Ian", 80),
                new Student("Tony", 20),
                new Student("Sam", 40)
        );
    }

    @Test
    public void Integer_오름차순_테스트() {
    }

    @Test
    public void Student_Grade로_오름차순_테스트() {
        // TODO Lamda로.
    }


    @Test
    public void String_이름으로_오름차순() {
        // TODO 직접 하기
    }

}