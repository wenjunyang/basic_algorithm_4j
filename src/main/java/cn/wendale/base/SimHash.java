package cn.wendale.base;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.SIZE;
import static java.lang.Long.bitCount;
import static java.lang.String.format;

/**
 * Created with IntelliJ IDEA.
 * User: yang.wenjun
 * Date: 2017/5/11
 * Time: 下午5:07
 * Description:
 */


public class SimHash<T> {

    // 汉明距离阈值，一般认为3以内为相近
    private int simThreshold;

    // 对hashCode进行hash，分成几块
    private int segNum;

    private int itemBit;

    private static final int MAX_THRESHOLD = 3;

    private static final int[] LEGAL_SEG_NUMS = new int[]{4, 8, 16};

    private List<Long>[] values;

    public SimHash() {
        this(MAX_THRESHOLD, LEGAL_SEG_NUMS[0]);
    }

    public SimHash(int simThreshold) {
        this(simThreshold, LEGAL_SEG_NUMS[0]);
    }

    public SimHash(int simThreshold, int segNum) {
        if (simThreshold > MAX_THRESHOLD) {
            throw new IllegalArgumentException(format("sim threshold can not be greater than %d", MAX_THRESHOLD));
        }
        if (!Ints.contains(LEGAL_SEG_NUMS, segNum)) {
            throw new IllegalArgumentException("hash segment can only be: 4, 8 16");
        }
        this.simThreshold = simThreshold;
        this.segNum = segNum;
        this.itemBit = SIZE / this.segNum;
        values = new List[this.segNum * (1 << itemBit)];
    }

    private int[] makeIndex(long hashCode) {
        int[] indexs = new int[this.segNum];
        for (int i = 0; i < segNum; i++) {
            indexs[i] = (int)hashCode & (1 << itemBit);
            hashCode >>>= itemBit;
        }
        return indexs;
    }

    /**
     * 最近距离小于阈值时，不保证返回最近
     * @return
     */
    private int nearest(int[] indexs, long hashCode) {
        int distance = SIZE;
        for (int i = 0; i < this.segNum; i++) {
            List<Long> list = values[indexs[i]];
            if (list != null) {
                for (Long hc : list) {
                    distance = Ints.min(distance, bitCount(hashCode ^ hc));
                }
                if (distance <= this.simThreshold) {
                    return distance;
                }
            }
        }
        return distance;
    }

    private void add(int[] indexes, long value) {
        for (int idx : indexes) {
            List<Long> lists = values[idx];
            if (lists == null) {
                lists = new ArrayList<>();
            }
            lists.add(value);
        }
    }

    public int nearest(Iterable<T> ts) {
        long hashCode = fingerprint(ts);
        int[] indexs = makeIndex(hashCode);
        return nearest(indexs, hashCode);
    }

    public boolean check(Iterable<T> ts) {
        return nearest(ts) <= this.simThreshold;
    }

    /**
     * 检查是否在存在，如果不存在则添加进来，并且
     * 返回是否存在相似的
     * @param ts
     * @return
     */
    public boolean checkAndAdd(Iterable<T> ts) {
        long hashCode =fingerprint(ts);
        int[] indexs = makeIndex(hashCode);
        int dist = nearest(indexs, hashCode);
        if (dist > 0) {
            add(indexs, hashCode);
        }
        return dist <= this.simThreshold;
    }

    public long fingerprint(Iterable<T> doc1) {
        int[] values = new int[SIZE];
        for (T t : doc1) {
            long hashCode = MurmurHash.hash64(t.toString());
            for (int i = 0; i < SIZE; i++) {
                if ((hashCode & (1 << i)) != 0) {
                    values[i]++;
                } else {
                    values[i]--;
                }
            }
        }
        long result = 0;
        for (int i = 0; i < SIZE; i++) {
            if (values[i] > 0) {
                result |= (2 << (SIZE - 1 - i));
            }
        }
        return result;
    }

    public int hmDistance(Iterable<T> doc1, Iterable<T> doc2) {
        return bitCount(fingerprint(doc1) ^ fingerprint(doc2));
    }
}
