package Lab5.Client;

public class TestModule {

    public static void main(String... args){
        new Thread(()->ClientMaster.main(new String[]{})).start();
        new Thread(()->Cliem.main(new String[]{})).start();
    }
}
