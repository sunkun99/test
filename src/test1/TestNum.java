package test1;

/**
 * Created by sk on 16/1/7.
 */
public class TestNum {
    // ①通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
    private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
        }
    };

    // ②获取下一个序列值
    public int getNextNum() {
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }

    public static void main(String[] args) {
        TestNum sn = new TestNum();
        // ③ 3个线程共享sn，各自产生序列号
        TestClient t1 = new TestClient(sn);
        TestClient t2 = new TestClient(sn);
        TestClient t3 = new TestClient(sn);
        t1.start();
        t2.start();
        t3.start();
        System.out.println("total: " + sn.getNextNum());
    }

    private static class TestClient extends Thread {
        private TestNum sn;

        public TestClient(TestNum sn) {
            this.sn = sn;
        }

        public void run() {
            for (int i = 0; i < 10000; i++) {
                if(Thread.currentThread().getName().equals("Thread-1")) {
                    // ④每个线程打出3个序列值
                    /*System.out.println("thread[" + Thread.currentThread().getName() + "] --> sn["
                            + sn.getNextNum() + "]");*/
                    sn.getNextNum();
                }
            }
            if(Thread.currentThread().getName().equals("Thread-1")) {
                // ④每个线程打出3个序列值
                    System.out.println("thread[" + Thread.currentThread().getName() + "] --> sn["
                            + sn.getNextNum() + "]");
            }
        }
    }
}
