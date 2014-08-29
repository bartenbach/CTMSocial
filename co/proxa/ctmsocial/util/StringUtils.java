package co.proxa.ctmsocial.util;

public class StringUtils {

    public static String getArgsOverOne(String[] args) {
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

    public static String getArgsOverZero(String[] args) {
        StringBuilder sb = new StringBuilder();
        args[0] = "$";
        for (String x : args) {
            if (!x.equals("$")) {
                sb.append(x).append(" ");
            }
        }
        return sb.toString().trim();
    }

    public static String getArgsAll(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String x : args) {
            if (!x.equals("$")) {
                sb.append(x).append(" ");
            }
        }
        return sb.toString().trim();
    }

}
