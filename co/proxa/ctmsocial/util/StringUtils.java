package co.proxa.ctmsocial.util;

public class StringUtils {

    public static String getArgs(String[] args) {
        StringBuilder sb = new StringBuilder();
        args[0] = "$";
        args[1] = "$";
        for (String x : args) {
            if (!x.equals("$")) {
                sb.append(x).append(" ");
            }
        }
        return sb.toString().trim();
    }

}
