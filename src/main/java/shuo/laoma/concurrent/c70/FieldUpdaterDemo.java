package shuo.laoma.concurrent.c70;



import lombok.Getter;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
@Getter
public class FieldUpdaterDemo {

    static class DemoObject {

        private volatile int num;

        private volatile Object ref;

        private static final AtomicIntegerFieldUpdater<DemoObject> numUpdater
            = AtomicIntegerFieldUpdater.newUpdater(DemoObject.class, "num");

        private static final AtomicReferenceFieldUpdater<DemoObject, Object>
            refUpdater = AtomicReferenceFieldUpdater.newUpdater(
                    DemoObject.class, Object.class, "ref");


        public boolean compareAndSetNum(int expect, int update) {
            return numUpdater.compareAndSet(this, expect, update);
        }


        public Object compareAndSetRef(Object expect, Object update) {
            return refUpdater.compareAndSet(this, expect, update);
        }


    }

    public static void main(String[] args) {
        DemoObject obj = new DemoObject();
        obj.compareAndSetNum(0, 100);
        obj.compareAndSetRef(null, new String("hello"));
        System.out.println(obj.num);
        System.out.println(obj.ref);
    }
}
