package kr.ac.cnu.study.sort;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rokim on 2017. 5. 30..
 */
public class Step4 {
    public static List<Integer> sort(List<Integer> list, boolean isAsc) {
        Integer[] items = list.toArray(new Integer[list.size()]);

        for (int i = 0 ; i < items.length ; i++) {
            Integer base = items[i];

            for (int j = i + 1 ; j < items.length ; j++) {
                Integer target = items[j];


                if (Step4.isSwap(base, target, isAsc)) {
                    // swap
                    items[i] = target;
                    items[j] = base;

                    base = target;
                }
            }
        }

        return Arrays.asList(items);
    }

    public static boolean isSwap(Integer o1, Integer o2, boolean isAsc) {
        if (isAsc && o1 > o2) {
            return true;
        }

        if (!isAsc && o1 < o2) {
            return true;
        }

        return false;
    }

}
