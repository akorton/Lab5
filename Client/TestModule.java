package Lab5.Client;

public class TestModule {
    public static int num = 15;
    public static void main(String... args) {
        new Thread(()-> CM1.main(new String[]{})).start();
        new Thread(()-> CM2.main(new String[]{})).start();
        new Thread(()-> CM3.main(new String[]{})).start();
    }
}
