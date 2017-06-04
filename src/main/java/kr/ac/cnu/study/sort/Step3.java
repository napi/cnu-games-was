package kr.ac.cnu.study.sort;


import kr.ac.cnu.study.model.Student;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rokim on 2017. 5. 30..
 */
public class Step3 {
    public static List<Student> sort(List<Student> list, boolean isAsc) {
        Student[] items = list.toArray(new Student[list.size()]);

        for (int i = 0 ; i < items.length ; i++) {
            Student base = items[i];

            for (int j = i + 1 ; j < items.length ; j++) {
                Student target = items[j];


                if (isAsc) { // 오름차순
                    if (base.getGrade() > target.getGrade()) {
                        // swap
                        items[i] = target;
                        items[j] = base;

                        base = target;
                    }
                } else {    // 내림차순
                    if (base.getGrade() < target.getGrade()) {
                        // swap
                        items[i] = target;
                        items[j] = base;

                        base = target;
                    }
                }
            }
        }

        return Arrays.asList(items);
    }
}
