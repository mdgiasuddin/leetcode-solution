package string;

import java.util.ArrayList;
import java.util.List;

// Leetcode problem: 271
// Lintcode problem: 659
// Explanation: https://www.youtube.com/watch?v=B1k_sxOSgv8&list=PLot-Xpze53leF0FeHz2X0aG3zd0mr1AW_&index=172
public class EncodeDecode {

    public String encode(List<String> strs) {
        StringBuilder encoded = new StringBuilder();

        for (String str : strs) {
            encoded.append(str.length()).append("#").append(str);
        }

        return encoded.toString();
    }

    public List<String> decode(String str) {
        List<String> decoded = new ArrayList<>();
        int i = 0;
        while (i < str.length()) {
            int j = i;
            int len = 0;
            while (str.charAt(j) != '#') {
                len = len * 10 + str.charAt(j) - '0';
                j += 1;
            }

            decoded.add(str.substring(j + 1, j + 1 + len));
            i = j + 1 + len;
        }

        return decoded;
    }
}
