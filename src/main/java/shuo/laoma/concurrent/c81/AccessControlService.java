package shuo.laoma.concurrent.c81;

import java.util.concurrent.Semaphore;

public class AccessControlService {

    //自定义异常
    public static class ConcurrentLimitException extends RuntimeException {

        private static final long serialVersionUID = 1L;
    }

    //限制100个
    private static final int MAX_PERMITS = 100;
    private Semaphore permits = new Semaphore(MAX_PERMITS, true);

    public boolean login(String name, String password) {
        if (!permits.tryAcquire()) {
            // 同时登录用户数超过限制
            throw new ConcurrentLimitException();
        }
        // ..其他验证
        return true;
    }

    //释放一个
    public void logout(String name) {
        permits.release();
    }

}
