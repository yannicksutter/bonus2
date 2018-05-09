import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static String hashwert = "1d56a37fb6b08aa709fe90e12ca59e12";
    private static int l = 7;
    private static ArrayList<String> passwords = new ArrayList<String>();
    private static String init = "0000000";
    private static int pwLength = init.length();
    private static char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static Map<String, String> rainbowTable = new HashMap<>();

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        recursiveFillPasswords(init, 1);
        /*for (String s : passwords) {
            System.out.println(passwords.indexOf(s)+ ": " + s);
        }*/
        System.out.println("Number of passwords: " + passwords.size());

        //generate rainbow table
        for(String password : passwords) {
            String pw = password;
            for(int i = 0; i < 2000; i++) {
                pw = getMD5(pw);
                pw = getReduction(pw, i);
            }
            rainbowTable.put(password, pw);
        }

        for(int i = 1999; i >=0; i--) {
            String h = makeReductionStep(hashwert, i);
            if(rainbowTable.containsValue(h)){
                System.out.println("Password: " + getKeyFromValue(rainbowTable, h));
                break;
            }
        }


    }


    private static String makeReductionStep(String hash, int step) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(step % 2 == 0) {
            hash = getReduction(hash, step);
            hash = getMD5(hash);
        } else {
            hash = getReduction(hash, step);
        }
        if(step < 1999) {
            hash = makeReductionStep(hash, step+1);
        }
        return hash;
    }




    private static boolean hasHash(String hash) {
        return rainbowTable.containsValue(hash);
    }


    //Creates the first 2000 passwords using lowercase letters and numbers 0...9
    private static void recursiveFillPasswords(String init, int index) {
        for (char c : chars) {
            if(passwords.size() >= 2000) {
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


    //returns md5 hash
    private static String getMD5(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytesOfMessage = s.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        return new BigInteger(1, md.digest(bytesOfMessage)).toString(16);
    }

    //returns reduction on given hash and step gemäss folie 3.27
    private static String getReduction(String hash, int step) {
        BigInteger bi = new BigInteger(hash, 16);
        bi.add(BigInteger.valueOf(step));
        int anzahlZeichen = chars.length;
        BigInteger[] bis = new BigInteger[anzahlZeichen];
        for (int i = 0; i < l; i++) {
            bis[i] = bi.mod(BigInteger.valueOf(anzahlZeichen));
            bi = bi.divide(BigInteger.valueOf(anzahlZeichen));
        }
        StringBuilder sb = new StringBuilder();
        for (int x = l-1; x >= 0; x--) {
            sb.append(chars[bis[x].intValue()]);
        }
        return sb.toString();
    }

    //Gets the key from a value in a hash map
    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }


}
