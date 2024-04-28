package proj.inue.posis.utils;

public class Helper {
    public static String stringsToJson(String[] keys, String[] values) {
        StringBuilder out = new StringBuilder("{");

        for (int i = 0; i < keys.length; i++) {
            if (values[i].charAt(0) == '[') out.append(String.format("'%s':%s", keys[i], values[i]));
            else out.append(String.format("'%s':'%s'", keys[i], values[i]));
            if (i < keys.length - 1) out.append(",");
        }
        out.append("}");

        return out.toString();
    }

    public static String getArrayToString(String[] array, char separator, char prefix, char  suffix) {
        StringBuilder out = new StringBuilder();
        for (String item : array) {
            out.append(String.format("%c%s%c%c",prefix, item, separator, suffix));
        }
        return out.toString();
    }
}
