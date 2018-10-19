package shuo.laoma.concurrent.c70;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: panda
 * @Date: 2018/9/30 0030 16:42
 */
public class AtomicStampedReferenceDemo {
    public static void main(String[] arg){
        Pair pair = new Pair(100, 200);
        int stamp = 1;
        AtomicStampedReference<Pair> pairRef = new AtomicStampedReference<>(pair, stamp);

        int newStamp = 2;
        //AtomicStampedReference.compareAndSet(原值,新值,原戳,新戳)
        pairRef.compareAndSet(pair, new Pair(200, 200), stamp, newStamp);
    }
}
