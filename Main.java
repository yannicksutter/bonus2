import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Main {
    private static int l = 7;
    private static ArrayList<String> passwords = new ArrayList<String>();
    private static String init = "0000000";
    private static int pwLength = init.length();
    private static char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};


    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        System.out.println("Hello World!");
        /*System.out.println(getMD5("0000000"));
        System.out.println(getReduction(getMD5("0000000"), 0));*/
        recursiveFillPasswords(init, 1);
        for (String s : passwords) {
            System.out.println(passwords.indexOf(s)+ ": " + s);
        }
        System.out.println(passwords.size());
    }

    //Creates the first 2000 passwords using lowercase letters and numbers 0...9
    private static void recursiveFillPasswords(String init, int index) {
        for (char c : chars) {
            if(passwords.size() > 2000) {
                break;
            }
            init = init.substring(0, index - 1).concat(String.valueOf(c));
            if(index != 7){
                recursiveFillPasswords(init, index+1);
            }
            if(init.length() == 7) {
                passwords.add(init);
            }
        }


    }


    private static void fillPasswords(int idx) {
        for (char c : chars) {
            if (idx < 7) {
                init = init.substring(0, idx).concat(String.valueOf(c)).concat(init.substring(idx, init.length()));
                passwords.add(init);
                fillPasswords(idx + 1);
            }
            init = init.substring(0, idx).concat(String.valueOf(c));
            passwords.add(init);
        }
    }

    private static String getMD5(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytesOfMessage = s.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        return new BigInteger(1, md.digest(bytesOfMessage)).toString(16);
    }

    private static String getReduction(String hash, int step) {
        BigInteger bi = new BigInteger(hash, 16);
        bi.add(BigInteger.valueOf(step));
        BigInteger[] bis = new BigInteger[chars.length];
        for (int i = 0; i < l; i++) {
            bis[i] = bi.mod(BigInteger.valueOf(chars.length));
            bi = bi.divide(BigInteger.valueOf(chars.length));
        }


        StringBuilder sb = new StringBuilder();
        for (int x = bis.length - 1; x >= 0; x--) {
            sb.append(chars[bis[x].intValue()]);
        }
        return sb.toString();
    }


}
