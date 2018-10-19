package shuo.laoma.concurrent.c71;

import java.util.Random;

public class AccountMgr {
    public static class NoEnoughMoneyException extends Exception {}

    /**
     *
     * @param from 来账户
     * @param to 去账户
     * @param money 转账金额
     * @throws NoEnoughMoneyException
     */
    public static void transfer_deadlock(Account from, Account to, double money)
            throws NoEnoughMoneyException {
        //如果相反的反向也在转,都拿到了第一个锁...都是在等另外一个锁就完了 死锁
        from.lock();
        try {
            to.lock();
            try {
                if (from.getMoney() >= money) {
                    from.reduce(money);
                    to.add(money);
                } else {
                    throw new NoEnoughMoneyException();
                }
            } finally {
                to.unlock();
            }
        } finally {
            from.unlock();
        }
    }
    
    public static void simulateDeadLock() {
        final int accountNum = 10;
        final Account[] accounts = new Account[accountNum];
        final Random rnd = new Random();
        for (int i = 0; i < accountNum; i++) {
            accounts[i] = new Account(rnd.nextInt(10000));
        }

        int threadNum = 100;
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread() {
                public void run() {
                    int loopNum = 100;
                    for (int k = 0; k < loopNum; k++) {
                        int i = rnd.nextInt(accountNum);
                        int j = rnd.nextInt(accountNum);
                        int money = rnd.nextInt(10);
                        if (i != j) {
                            try {
                                transfer(accounts[i], accounts[j], money);
                            } catch (NoEnoughMoneyException e) {
                            }
                        }
                    }
                }
            };
            threads[i].start();
        }
    }


    public static boolean tryTransfer(Account from, Account to, double money)
            throws NoEnoughMoneyException {
        if (from.tryLock()) {
            try {
                //拿不到就算了 放弃 把之前拿到的锁也放掉 不会死锁
                if (to.tryLock()) {
                    try {
                        if (from.getMoney() >= money) {
                            from.reduce(money);
                            to.add(money);
                        } else {
                            throw new NoEnoughMoneyException();
                        }
                        return true;
                    } finally {
                        to.unlock();
                    }
                }
            } finally {
                from.unlock();
            }
        }
        return false;
    }
    
    public static void transfer(Account from, Account to, double money)
            throws NoEnoughMoneyException {
        boolean success = false;
        do {
            success = tryTransfer(from, to, money);
            if (!success) {
                Thread.yield();
            }
        } while (!success);
    }

}
