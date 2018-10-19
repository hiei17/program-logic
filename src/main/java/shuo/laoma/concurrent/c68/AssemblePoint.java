package shuo.laoma.concurrent.c68;

public class AssemblePoint {

    private int n;
    private  int parties;

    private final Runnable barrierCommand;


    public AssemblePoint(int parties) {
        this(parties,null);
    }
    public AssemblePoint(int parties, Runnable barrierAction){


        if (parties<1){
            throw new RuntimeException("必须大于0");
        }
        this.n = parties;
        this.parties = parties;
        this.barrierCommand = barrierAction;
    }

    public synchronized void await() throws InterruptedException {

        n--;
        while (n != 0) {
            wait();
        }

        //最后一个到达
        if(barrierCommand!=null){
            barrierCommand.run();//这边直接run 不start 执行完才去唤起所有线程
        }
        notifyAll();//唤醒所有wait

        //循环使用
        n=parties;
    }

}
