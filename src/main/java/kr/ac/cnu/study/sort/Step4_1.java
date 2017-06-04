package kr.ac.cnu.study.sort;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rokim on 2017. 5. 30..
 */
public class Step4_1<T> {
    public List<T> sort(List<T> list, boolean isAsc) {
        T[] items = list.toArray((T[])new Object[list.size()]);

        for (int i = 0 ; i < items.length ; i++) {
            T base = items[i];

            for (int j = i + 1 ; j < items.length ; j++) {
                T target = items[j];


                if (this.isSwap(base, target, isAsc)) {
                    // swap
                    items[i] = target;
                    items[j] = base;

                    base = target;
                }
            }
        }

        return Arrays.asList(items);
    }

    public boolean isSwap(T o1, T o2, boolean isAsc) {
        // TODO condition is depended on Type
//        if (isAsc && o1 > o2) {
//            return true;
//        }
//
//        if (!isAsc && o1 < o2) {
//            return true;
//        }

        return false;
    }

}
