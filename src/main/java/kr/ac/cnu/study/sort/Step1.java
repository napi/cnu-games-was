package kr.ac.cnu.study.sort;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rokim on 2017. 5. 30..
 */
public class Step1 {
    public static List<Integer> sort(List<Integer> list) {
        Integer[] items = list.toArray(new Integer[list.size()]);

        for (int i = 0 ; i < items.length ; i++) {
            Integer base = items[i];

            for (int j = i + 1 ; j < items.length ; j++) {
                Integer target = items[j];

                // 오름차순
                if (base > target) {
                    // swap
                    items[i] = target;
                    items[j] = base;

                    base = target;
                }
            }
        }

        return Arrays.asList(items);
    }
}
