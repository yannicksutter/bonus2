import java.util.ArrayList;

public class Main {


    int l = 2000;
    private static ArrayList<String> passwords = new ArrayList<String>();
    private static String init = "0000000";
    private static char[] chars = new char[]{'0', '1', '2', '3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x', 'y', 'z'};


    public static void main(String[] args) {

        System.out.println("Hello World!");
        for(int i = 7; i > 0; i--) {
            fillPasswords(7);
        }
        for(String s : passwords) {
            System.out.println(s);
        }
    }

    private void createPasswords() {
        String init = "0000000";
        passwords.add(init);
        for(int i = 0; i < 2000; i++) {
            for(int j = 7; j > 0; j--){

                for(char c : chars) {
                    init.substring(0, j-1).concat(String.valueOf(c));
                }
            }
        }
    }

    private static void fillPasswords(int idx) {
        for(char c : chars) {
            if(idx < 7) {
                init = init.substring(0, idx).concat(String.valueOf(c)).concat(init.substring(idx, init.length()));
                passwords.add(init);
                fillPasswords(idx+1);
            }
            init = init.substring(0, idx).concat(String.valueOf(c));
            passwords.add(init);
        }
    }
}
