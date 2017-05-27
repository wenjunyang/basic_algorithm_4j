package cn.wendale.base;

/**
 * Created with IntelliJ IDEA.
 * User: yang.wenjun
 * Date: 2017/5/22
 * Time: 下午2:59
 * Description:
 */


import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MinHash<T> {

    private List<T> fullSet;

    public MinHash(Set<T> fullSet) {
        this.fullSet = new ArrayList<>(fullSet);
        Collections.shuffle(this.fullSet);
    }

    public Integer hash(Set<T> content) {
        if (CollectionUtils.isEmpty(content)) {
            throw new RuntimeException("empty set");
        }
        List<Integer> result = Lists.newArrayList();
        for (T t : fullSet) {
            if (content.contains(t)) {
                result.add(1);
            } else {
                result.add(0);
            }
        }
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) == 1) {
                return i;
            }
        }
        return -1;
    }

}
