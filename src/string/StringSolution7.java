package string;

import java.util.*;

public class StringSolution7 {
    public static void main(String[] args) {
        StringSolution7 stringSolution7 = new StringSolution7();

        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};

        System.out.println(stringSolution7.groupAnagrams(strs));

    }

    // Leetcode problem: 1106
    public boolean parseBoolExpr(String expression) {
        Stack<Character> stack = new Stack<>();

        stack.push(expression.charAt(0));
        for (int i = 1; i < expression.length(); i++) {
            if (expression.charAt(i) == ')') {
                char and = 't', or = 'f', not = 'f';
                while (stack.peek() != '(') {
                    char ch = stack.pop();

                    /*
                     * 1 True value will return True for '|' operation
                     * 1 False value will return False for '&' operation]
                     * '!' will be opposite for True or False
                     * */
                    if (ch == 't') {
                        or = 't';
                        not = 'f';
                    } else {
                        and = 'f';
                        not = 't';
                    }
                }
                stack.pop(); // pop '('

                char op = stack.pop(); // After '(' there will be operator

                // Push the value according to operator
                if (op == '|')
                    stack.push(or);
                else if (op == '&')
                    stack.push(and);
                else if (op == '!')
                    stack.push(not);

            } else if (expression.charAt(i) != ',') {
                stack.push(expression.charAt(i));
            }
        }

        return stack.peek() == 't';
    }

    // Leetcode problem: 2063
    /*
     * Count vowels of all substrings
     * */
    public long countVowels(String word) {
        /*
                        bcdefgh
                          ''
                          d
                         cd
                        bcd
                           e
                           ef
                           efg
                           efgh
                       (i+1)*(len-i)
        * */

        long count = 0;
        long len = word.length();

        for (int i = 0; i < len; i++) {
            char ch = word.charAt(i);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count += (i + 1) * (len - i);
            }
        }

        return count;
    }

    // Leetcode problem: 1963
    /*
     * Problem is tricky
     * Find out the maximum closing bracket with is unbalanced.
     * If closing brackets are balanced, opening brackets will be automatically balanced
     * 1 swap will balance 2 unbalanced closing bracket
     * If closing bracket found increment unbalanced and when opening bracket found decrement it
     * Update the maximum point of unbalanced closing bracket
     * */
    public int minSwaps(String s) {
        int unbalancedClosing = 0;
        int maxUnbalanced = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '[') {
                unbalancedClosing--;
            } else {
                unbalancedClosing++;
                maxUnbalanced = Math.max(maxUnbalanced, unbalancedClosing);
            }
        }

        // Return the ceiling value after divide
        return (maxUnbalanced + 1) / 2;
    }

    // Leetcode problem: 2116
    /*
     * Check if the parenthesis can be balanced after flipping certain position
     * We have to traverse from both left to right and right to left
     * While traversing left to right, check whether there is enough opening bracket to balance closing bracket. Flip + Open >= Close must be satisfied.
     * While traversing right to left, check whether there is enough closing bracket to balance opening bracket. Flip + Close >= Open must be satisfied.
     * If the condition is always satisfied then parenthesis can be balanced
     * Otherwise, return false
     * */
    public boolean canBeValid(String s, String locked) {
        int len = s.length();

        // If length is odd, then it never can be balanced
        if (len % 2 == 1) {
            return false;
        }

        int opening = 0, closing = 0, flipPossible = 0;

        // Traverse from left to right
        for (int i = 0; i < len; i++) {
            if (locked.charAt(i) == '0') {
                flipPossible++;
            } else if (s.charAt(i) == '(') {
                opening++;
            } else {
                closing++;
            }

            if (flipPossible + opening < closing) {
                return false;
            }
        }

        // Reset opening, closing and flip possible
        opening = closing = flipPossible = 0;

        // Traverse from right to left
        for (int i = len - 1; i >= 0; i--) {
            if (locked.charAt(i) == '0') {
                flipPossible++;
            } else if (s.charAt(i) == '(') {
                opening++;
            } else {
                closing++;
            }

            if (flipPossible + closing < opening) {
                return false;
            }
        }

        return true;
    }

    // Leetcode problem: 1985
    /*
     * Build up a priority queue.
     * Pop from the queue k times
     * */
    public String kthLargestNumber(String[] nums, int k) {
        PriorityQueue<String> queue = new PriorityQueue<>((a, b) -> b.length() == a.length()
                ? b.compareTo(a) : b.length() - a.length());

        queue.addAll(Arrays.asList(nums));

        for (int i = 1; i < k; i++) {
            queue.poll();
        }

        return queue.poll();
    }

    // Leetcode problem: 58
    public int lengthOfLastWord(String s) {
        int r = s.length() - 1;

        // Find index of last character.
        while (r >= 0 && s.charAt(r) == ' ')
            r--;

        // Find the index of space after the last character.
        int l = r;
        while (l >= 0 && s.charAt(l) != ' ')
            l--;

        return r - l;
    }

    // Leetcode problem: 929
    public int numUniqueEmails(String[] emails) {

        Set<String> emailSet = new HashSet<>();

        for (String email : emails) {

            // This using string library function.
            /*
            String[] splitted = email.split("@");
            String local = splitted[0].split("\\+")[0];
            local = local.replace(".", "");
            emailSet.add(local + '@' + splitted[1]);
            */

            boolean plusFound = false, domainFound = false;

            StringBuilder emailBuilder = new StringBuilder();
            for (int i = 0; i < email.length(); i++) {
                char ch = email.charAt(i);
                if (ch == '@') {
                    domainFound = true;
                } else if (ch == '+') {
                    plusFound = true;
                }
                // If domain found or before '+' then add all.
                if (domainFound || (!plusFound && ch != '.')) {
                    emailBuilder.append(ch);
                }

            }

            if (emailSet.add(emailBuilder.toString())) ;
        }

        return emailSet.size();
    }

    // Leetcode problem: 290
    /*
     * This problem is similar to Isomorphic String (Leetcode problem: 205).
     * */
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");

        if (words.length != pattern.length())
            return false;

        Map<Character, String> charStr = new HashMap<>();
        Map<String, Character> strChar = new HashMap<>();

        for (int i = 0; i < words.length; i++) {
            char ch = pattern.charAt(i);
            String word = words[i];

            if (charStr.containsKey(ch) && !charStr.get(ch).equals(word))
                return false;

            if (strChar.containsKey(word) && strChar.get(word) != ch)
                return false;

            charStr.put(ch, word);
            strChar.put(word, ch);
        }

        return true;
    }

    // Leetcode problem: 14
    public String longestCommonPrefix(String[] strs) {
        StringBuilder res = new StringBuilder();

        // Compare all strings with first string.
        for (int i = 0; i < strs[0].length(); i++) {
            for (String str : strs) {
                if (i == str.length() || str.charAt(i) != strs[0].charAt(i))
                    return res.toString();
            }

            res.append(strs[0].charAt(i));
        }

        return res.toString();
    }

    // Leetcode problem: 12
    public String intToRoman(int num) {
        StringBuilder result = new StringBuilder();
        while (num > 0) {
            if (num >= 1000) {
                result.append("M");
                num -= 1000;
            } else if (num >= 900) {
                result.append("CM");
                num -= 900;
            } else if (num >= 500) {
                result.append("D");
                num -= 500;
            } else if (num >= 400) {
                result.append("CD");
                num -= 400;
            } else if (num >= 100) {
                result.append("C");
                num -= 100;
            } else if (num >= 90) {
                result.append("XC");
                num -= 90;
            } else if (num >= 50) {
                result.append("L");
                num -= 50;
            } else if (num >= 40) {
                result.append("XL");
                num -= 40;
            } else if (num >= 10) {
                result.append("X");
                num -= 10;
            } else if (num >= 9) {
                result.append("IX");
                num -= 9;
            } else if (num >= 5) {
                result.append("V");
                num -= 5;
            } else if (num >= 4) {
                result.append("IV");
                num -= 4;
            } else {
                result.append("I");
                num -= 1;
            }
        }

        return result.toString();
    }

    // Leetcode problem: 13
    public int romanToInt(String s) {
        Map<Character, Integer> roman = new HashMap<>();
        roman.put('M', 1000);
        roman.put('D', 500);
        roman.put('C', 100);
        roman.put('L', 50);
        roman.put('X', 10);
        roman.put('V', 5);
        roman.put('I', 1);

        int number = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i + 1 < s.length() && roman.get(s.charAt(i)) < roman.get(s.charAt(i + 1))) {
                // Like: CM, CD, XC, XL, IX, IV
                number -= roman.get(s.charAt(i));
            } else {
                number += roman.get(s.charAt(i));
            }
        }
        return number;
    }

    // Leetcode problem: 392
    public boolean isSubsequence(String s, String t) {
        int i = 0, j = 0;

        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j))
                i++;
            j++;
        }

        return i == s.length();
    }

    // Leetcode problem: 49
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<Map<Character, Integer>, List<String>> map = new HashMap<>();

        for (String str : strs) {
            Map<Character, Integer> counter = new HashMap<>();

            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);

                int c = 1 + counter.getOrDefault(ch, 0);
                counter.put(ch, c);
            }

            List<String> list = map.getOrDefault(counter, new ArrayList<>());
            list.add(str);

            map.put(counter, list);
        }

        return new ArrayList<>(map.values());
    }
}
