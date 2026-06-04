package moe.prashast.util;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

    public static String formatTo128Bit(String input) {

        if (input == null) return null;

        if (!input.matches("[01]+")) {
            throw new RuntimeException("Only 0 and 1 allowed");
        }

        if (input.length() > 128) {
            throw new RuntimeException("Max 128 bits allowed");
        }

        return String.format("%128s", input).replace(' ', '0');
    }


    public static List<Integer> getIndexesOfOnes(String value) {

        List<Integer> indexes = new ArrayList<>();

        if (value == null || value.isEmpty()) {
            return indexes;
        }

        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == '1') {
                indexes.add(i);
            }
        }

        return indexes;
    }

}
