package utils;


import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean deleteDirectory(String path) {
        File[] array = new File(path).listFiles();
        assert array != null;
        for (File file : array) {
            if (file.isDirectory()) {
                deleteDirectory(file.getPath());
            } else if (!file.delete()) {
                return false;
            }
        }
        return true;
    }


    public static String regexReplace(String input, String regex, String replacement) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.replaceAll(replacement);
    }

    public static String regularExpressionsReplaceNull(String input, String... replaceValue) {
        for (String value : replaceValue) {
            input = regexReplace(input, value, "");
        }
        return input;
    }

    public static String[] regexMatching(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String[] StringArray = new String[10000];
        int c = 0;
        while (matcher.find()) {
            StringArray[c] = matcher.group();
            c++;
        }
        return Utils.removeNull(StringArray);
    }

    public static String[] removeNull(String[] array){
        int c = 0;
        for (String a : array) {
            if (a != null) c++;
        }
        String[] newArray = new String[c];
        System.arraycopy(array, 0, newArray, 0, c);
        return newArray;
    }

    public static StringBuilder deleteSymbol(String s, String symbol){
        StringBuilder newString = new StringBuilder();
        for (String i:s.split("")){
            if (i.equals(symbol)){
                continue;
            }
            newString.append(i);
        }
        return newString;
    }

    public static String fileSha1(File file) throws NoSuchAlgorithmException, IOException {
        if (!file.exists() && !file.isDirectory()) {
            return null;
        }else {
            if (!file.canWrite())
                if (!file.setWritable(true))
                    return null;
            byte[] input = Stream.readToString(String.valueOf(file));
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input);
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 40) hashText.insert(0, "0");
            return hashText.toString();
        }
    }

    public enum xboxLiveType {
        XBL, XSTS
    }
}
