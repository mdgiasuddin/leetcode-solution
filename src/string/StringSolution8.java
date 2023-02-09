package string;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringSolution8 {

    public static void main(String[] args) {
        StringSolution8 stringSolution8 = new StringSolution8();

        System.out.println(stringSolution8.validIPAddress("172.16.254.1"));
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

    // Leetcode problem: 1930
    /*
     * Unique Length-3 Palindromic Subsequences.
     * */
    public int countPalindromicSubsequence(String s) {
        int n = s.length();
        int[] left = new int[26];
        int[] right = new int[26];
        Arrays.fill(left, -1);
        Arrays.fill(right, -1);

        for (int i = 0; i < n; i++) {
            if (left[s.charAt(i) - 'a'] == -1) {
                left[s.charAt(i) - 'a'] = i;
            }

            if (right[s.charAt(n - 1 - i) - 'a'] == -1) {
                right[s.charAt(n - 1 - i) - 'a'] = n - 1 - i;
            }
        }

        Set<String> result = new HashSet<>();
        for (int i = 1; i < n - 1; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (left[ch - 'a'] != -1 && left[ch - 'a'] < i && right[ch - 'a'] > i) {
                    result.add(ch + "" + s.charAt(i) + "" + ch);
                }
            }
        }

        return result.size();
    }

    // Leetcode problem: 38
    /*
     * Count and Say.
     * */
    public String countAndSay(int n) {
        String result = "1";

        for (int i = 2; i <= n; i++) {
            result = countAndSay(result);
        }

        return result;
    }

    private String countAndSay(String str) {
        StringBuilder builder = new StringBuilder();
        int count = 1;
        int n = str.length();
        for (int i = 1; i < n; i++) {
            if (str.charAt(i) != str.charAt(i - 1)) {
                builder.append(count);
                builder.append(str.charAt(i - 1));
                count = 1;
            } else {
                count += 1;
            }
        }

        builder.append(count);
        builder.append(str.charAt(n - 1));

        return builder.toString();
    }

    // Leetcode problem: 468
    /*
     * Validate IP Address.
     * \\d => [0-9]
     * Explanation: https://www.youtube.com/watch?v=EB5FAwHqpm4&list=PLEJXowNB4kPxxaPCDVrZhSvW3NSD6ATaS&index=16
     * */
    public String validIPAddress(String queryIP) {
        String regexIPv4 = "((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}" +
                "(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])";

        String regexIPv6 = "(([\\da-fA-F]{1,4}):){7}([\\da-fA-F]{1,4})";

        if (Pattern.matches(regexIPv4, queryIP)) {
            return "IPv4";
        } else if (Pattern.matches(regexIPv6, queryIP)) {
            return "IPv6";
        }

        return "Neither";
    }

    // Leetcode problem: 2512
    /*
     * Reward Top K Students.
     * */
    public List<Integer> topStudents(String[] positive_feedback, String[] negative_feedback, String[] report, int[] student_id, int k) {
        PriorityQueue<int[]> scores = new PriorityQueue<>((a, b) -> a[1] == b[1] ? a[0] - b[0] : b[1] - a[1]);

        Set<String> positiveSet = Arrays.stream(positive_feedback).collect(Collectors.toSet());
        Set<String> negativeSet = Arrays.stream(negative_feedback).collect(Collectors.toSet());

        for (int i = 0; i < report.length; i++) {
            int score = getScore(report[i], positiveSet, negativeSet);
            scores.add(new int[]{student_id[i], score});
        }

        List<Integer> result = new ArrayList<>();
        while (k-- > 0) {
            result.add(scores.poll()[0]);
        }

        return result;
    }

    private int getScore(String report, Set<String> positiveSet, Set<String> negativeSet) {
        int score = 0;

        String[] reportArray = report.split(" ");
        for (String word : reportArray) {
            if (positiveSet.contains(word)) {
                score += 3;
            } else if (negativeSet.contains(word)) {
                score -= 1;
            }
        }

        return score;
    }

    // Leetcode problem: 2446
    /*
     * Determine if Two Events Have Conflict.
     * */
    public boolean haveConflict(String[] event1, String[] event2) {
        // This logic is important.
        return getMin(event1[1], event2[1]).compareTo(getMax(event1[0], event2[0])) >= 0;
    }

    private String getMin(String str1, String str2) {
        return str1.compareTo(str2) < 0 ? str1 : str2;
    }

    private String getMax(String str1, String str2) {
        return str1.compareTo(str2) > 0 ? str1 : str2;
    }


    // Leetcode problem: 1071
    /*
     * Greatest Common Divisor of Strings.
     * */
    public String gcdOfStrings(String str1, String str2) {

        int n1 = str1.length();
        int n2 = str2.length();

        String res = "";
        for (int i = 0; i < Math.min(n1, n2); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                break;
            }

            if (n1 % (i + 1) == 0 && (n2 % (i + 1) == 0)) {
                String substr = str1.substring(0, i + 1);
                if (substr.repeat(n1 / (i + 1)).equals(str1) && substr.repeat(n2 / (i + 1)).equals(str2)) {
                    res = substr;
                }
            }
        }

        return res;
    }

    // Leetcode problem: 2306
    /*
     * Naming a Company.
     * Explanation: https://www.youtube.com/watch?v=NrHpgTScOcY.
     * */
    public long distinctNames(String[] ideas) {
        Map<Character, Set<String>> suffixMap = new HashMap<>();

        for (String idea : ideas) {
            Set<String> suffixes = suffixMap.getOrDefault(idea.charAt(0), new HashSet<>());
            suffixes.add(idea.substring(1));
            suffixMap.put(idea.charAt(0), suffixes);
        }

        long res = 0;
        Set<String> visited = new HashSet<>();
        for (Map.Entry<Character, Set<String>> entry1 : suffixMap.entrySet()) {
            for (Map.Entry<Character, Set<String>> entry2 : suffixMap.entrySet()) {
                if (Objects.equals(entry1.getKey(), entry2.getKey()) || visited.contains(entry1.getKey() + "" + entry2.getKey())) {
                    continue;
                }

                visited.add(entry1.getKey() + "" + entry2.getKey());
                visited.add(entry2.getKey() + "" + entry1.getKey());

                int intersect = 0;
                for (String suffix : entry1.getValue()) {
                    if (entry2.getValue().contains(suffix)) {
                        intersect += 1;
                    }
                }

                res += 2L * (entry1.getValue().size() - intersect) * (entry2.getValue().size() - intersect);
            }
        }

        return res;
    }

}
