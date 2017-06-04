package kr.ac.cnu.study.sort;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rokim on 2017. 5. 30..
 */
public class Step5 {
    public static <T> List<T> sort(List<T> list,  boolean isAsc, Comp<T> comp) {
        T[] items = list.toArray((T[]) new Object[list.size()]);

        for (int i = 0 ; i < items.length ; i++) {
            T base = items[i];

            for (int j = i + 1 ; j < items.length ; j++) {
                T target = items[j];


                if (comp.isSwap(base, target, isAsc)) {
                    // swap
                    items[i] = target;
                    items[j] = base;

                    base = target;
                }
            }
        }

        return Arrays.asList(items);
    }

    public interface Comp<T> {
        public boolean isSwap(T o1, T o2, boolean isAsc);
    }

}
