package proj.inue.posis.utils;

public class Helper {
    public static String stringsToJson(String[] keys, String[] values) {
        StringBuilder out = new StringBuilder("{");

        for (int i = 0; i < keys.length; i++) {
            out.append(String.format("'%s':'%s'", keys[i], values[i]));
            if (i < keys.length - 1) out.append(",");
        }
        out.append("}");

        return out.toString();
    }
}
