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
public class Step5Test {
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
        List<Integer> sortedList = Step5.sort(integerList, true, new Step5.Comp<Integer>() {
            @Override
            public boolean isSwap(Integer o1, Integer o2, boolean isAsc) {
                if (isAsc && o1 > o2) {
                    return true;
                }
                return false;
            }
        });

        System.out.println(sortedList);
        for (int i = 0 ; i < sortedList.size() - 1 ; i++) {
            assertTrue("오름차순", sortedList.get(i) < sortedList.get(i + 1));
        }
    }

    @Test
    public void Integer_내림차순_테스트() {

    }

    @Test
    public void Student_오름차순_테스트() {
    }

    @Test
    public void Student_내림차순_테스트() {

    }

}