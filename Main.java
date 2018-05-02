import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Main {


    int l = 2000;
    private static ArrayList<String> passwords = new ArrayList<String>();
    private static String init = "0000000";
    private static char[] chars = new char[]{'0', '1', '2', '3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x', 'y', 'z'};


    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        System.out.println("Hello World!");
        System.out.println(getMD5("0000000"));
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
    
    private static String getMD5(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytesOfMessage = s.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        return new BigInteger(1,md.digest(bytesOfMessage)).toString(16);
    }
}
