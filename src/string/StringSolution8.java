package string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringSolution8 {

    public static void main(String[] args) {
        StringSolution8 stringSolution8 = new StringSolution8();

        System.out.println(stringSolution8.findStrobogrammatic(3));
    }

    // Leetcode problem: 246
    // Lintcode problem: 644
    /*
     * Strobogrammatic Number.
     * */
    public boolean isStrobogrammatic(String num) {
        Map<Character, Character> mirrors = new HashMap<>();
        mirrors.put('0', '0');
        mirrors.put('1', '1');
        mirrors.put('6', '9');
        mirrors.put('8', '8');
        mirrors.put('9', '6');

        int len = num.length();
        for (int i = 0; i <= len / 2; i++) {
            if (!mirrors.containsKey(num.charAt(i)) || mirrors.get(num.charAt(i)) != num.charAt(len - 1 - i)) {
                return false;
            }
        }

        return true;
    }

    // Leetcode problem: 247
    // Lintcode problem: 776
    /*
     * Strobogrammatic Number II.
     * Backtracking solution.
     * */
    public List<String> findStrobogrammatic(int n) {
        Map<Character, Character> mirrors = new HashMap<>();
        mirrors.put('0', '0');
        mirrors.put('1', '1');
        mirrors.put('6', '9');
        mirrors.put('8', '8');
        mirrors.put('9', '6');

        List<String> result = new ArrayList<>();
        if (n % 2 == 1) {
            findStrobogrammatic(n - 1, "0", result, mirrors);
            findStrobogrammatic(n - 1, "1", result, mirrors);
            findStrobogrammatic(n - 1, "8", result, mirrors);
        } else {
            findStrobogrammatic(n, "", result, mirrors);
        }

        return result;
    }

    public void findStrobogrammatic(int n, String current, List<String> result, Map<Character, Character> mirrors) {
        if (n == 0) {
            result.add(current);
            return;
        }

        for (Map.Entry<Character, Character> entry : mirrors.entrySet()) {
            // Skip leading 0.
            if (n == 2 && entry.getKey() == '0') {
                continue;
            }
            findStrobogrammatic(n - 2, entry.getKey() + current + entry.getValue()
                    , result, mirrors);

        }
    }
}
