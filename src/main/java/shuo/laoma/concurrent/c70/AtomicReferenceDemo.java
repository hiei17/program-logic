package shuo.laoma.concurrent.c70;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {



    public static void main(String[] args) {
        Pair p = new Pair(100, 200);
        AtomicReference<Pair> pairRef = new AtomicReference<>(p);

        pairRef.compareAndSet(p, new Pair(200, 200));

        System.out.println(pairRef.get().getFirst());
    }

}
