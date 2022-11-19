package string;

import java.util.*;

public class StringSolution6 {

    public static void main(String[] args) {
        StringSolution6 stringSolution6 = new StringSolution6();

        System.out.println(stringSolution6.minFlipsMonoIncr("010110"));
    }

    // Leetcode problem: 816
    /*
     * Divide the string with left & right at every position and try to put '.' at every position of left and right
     * */
    public List<String> ambiguousCoordinates(String s) {
        List<String> result = new ArrayList<>();

        // Skip '(' & ')'
        for (int i = 2; i <= s.length() - 2; i++) {
            for (String left : placeDecimal(s, 1, i)) {
                for (String right : placeDecimal(s, i, s.length() - 1)) {
                    result.add("(" + left + ", " + right + ")");
                }
            }
        }

        return result;
    }

    List<String> placeDecimal(String s, int left, int right) {
        List<String> decimalPoints = new ArrayList<>();

        for (int d = 1; d <= right - left; d++) {
            String leftString = s.substring(left, left + d);
            String rightString = s.substring(left + d, right);

            // Skip leading 0 and 0 at rightmost position after '.'
            if ((!leftString.startsWith("0") || leftString.equals("0")) && !rightString.endsWith("0"))
                decimalPoints.add(leftString + (d < right - left ? "." : "") + rightString);
        }
        return decimalPoints;
    }

    // Leetcode problem: 859
    /*
     * Buddy string
     * */
    public boolean buddyStrings(String s, String goal) {

        // If length are different or length is 1 then there is no option to match by swap
        if (s.length() != goal.length() || s.length() == 1) {
            return false;
        }

        // If they are equal then at least 1 duplicate character two swap with one another
        if (s.equals(goal)) {
            // If length > 26 then at least one duplicate character
            if (s.length() > 26) {
                return true;
            }

            Set<Character> characterSet = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (characterSet.contains(ch)) {
                    return true;
                }
                characterSet.add(ch);
            }
            return false;
        } else {
            // There must be 2 different characters which can be swapped with one another to match with the goal
            int[] different = new int[2];

            int j = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != goal.charAt(i)) {
                    if (j < 2) {
                        different[j++] = i;
                    } else {
                        return false;
                    }
                }
            }

            return j == 2 && s.charAt(different[0]) == goal.charAt(different[1]) && s.charAt(different[1]) == goal.charAt(different[0]);
        }
    }

    // Leetcode problem: 880
    /*
     * First find out the total length of decoded string by scanning from left to right
     * Then remove character from the last one by one from the right to left and match with k
     * */
    public String decodeAtIndex(String s, int k) {
        long totalLength = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                totalLength *= (ch - '0');
            } else {
                totalLength++;
            }
        }

        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            k %= totalLength;

            if (k == 0 && Character.isLetter(ch)) {
                return String.valueOf(ch);
            }
            if (Character.isDigit(ch)) {
                totalLength /= (ch - '0');
            } else {
                totalLength--;
            }
        }

        return "";
    }

    // Leetcode problem: 899
    /*
     * If k = 1, just find the minimum of all rotations
     * Else sort the characters
     * It will work in the logic of bubble sort
     * If k > 1 then any 2 characters can be swapped. So sorting the characters will work.
     * */
    public String orderlyQueue(String s, int k) {

        if (k == 1) {
            String ans = s;

            for (int i = 1; i < s.length(); i++) {
                String temp = s.substring(i) + s.substring(0, i);
                if (temp.compareTo(ans) < 0) {
                    ans = temp;
                }
            }

            return ans;
        } else {
            char[] ans = s.toCharArray();
            Arrays.sort(ans);

            return String.copyValueOf(ans);
        }
    }

    // Leetcode problem: 916
    /*
     * Calculate maximum character count of each character of words2
     * Check which word of words1 contains all characters
     * */
    public List<String> wordSubsets(String[] words1, String[] words2) {
        List<String> result = new ArrayList<>();

        int[] maxChar = new int[26];

        for (String word : words2) {
            int[] charWord2 = new int[26];

            for (int i = 0; i < word.length(); i++) {
                charWord2[word.charAt(i) - 'a']++;
            }

            for (int i = 0; i < 26; i++) {
                maxChar[i] = Math.max(maxChar[i], charWord2[i]);
            }
        }

        for (String word : words1) {
            int[] charWord1 = new int[26];

            for (int i = 0; i < word.length(); i++) {
                charWord1[word.charAt(i) - 'a']++;
            }

            int i;
            for (i = 0; i < 26; i++) {
                if (charWord1[i] < maxChar[i]) break;
            }
            if (i == 26) {
                result.add(word);
            }
        }

        return result;
    }

    // Leetcode problem: 921
    /*
     * This problem is tricky & important.
     * */
    public int minAddToMakeValid(String s) {
        /*Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                stack.push(ch);
            } else {
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    stack.push(ch);
                }
            }
        }

        return stack.size();*/

        // Without extra memory
        int opening, closing;
        opening = closing = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                opening++;
            } else {
                if (opening > 0) {
                    opening--;
                } else {
                    closing++;
                }
            }
        }

        return opening + closing;

    }

    // Leetcode problem: 926
    /*
     * See Leetcode problem: 1653
     * */
    public int minFlipsMonoIncr(String s) {

        int extraOne = 0, flip = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                if (extraOne > 0) {
                    extraOne--;
                    flip++;
                }
            } else {
                extraOne++;
            }
        }

        return flip;
    }

    // Leetcode problem: 1041
    public boolean isRobotBounded(String instructions) {
        List<Map.Entry<Integer, Integer>> directions = Arrays.asList(
                Map.entry(0, 1), // North
                Map.entry(1, 0), // East
                Map.entry(0, -1), // South
                Map.entry(-1, 0) // West
        );

        int currentDir = 0, currentX = 0, currentY = 0;

        for (int i = 0; i < instructions.length(); i++) {
            char ch = instructions.charAt(i);

            if (ch == 'G') {
                Map.Entry<Integer, Integer> direction = directions.get(currentDir);
                currentX += direction.getKey();
                currentY += direction.getValue();
            } else if (ch == 'L') {
                currentDir = (currentDir + 3) % 4;
            } else {
                currentDir = (currentDir + 1) % 4;
            }
        }

        // If Current direction is not north then after some completion of instructions it will make a circle
        // Else it will continue to move on a specific directions
        return (currentX == 0 && currentY == 0) || currentDir != 0;
    }

    // Leetcode problem: 763
    /*
     * Partition Labels.
     * */
    public List<Integer> partitionLabels(String s) {
        Map<Character, Integer> map = new HashMap<>();

        // Find out the last index for every character.
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), i);
        }

        List<Integer> result = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {

            // Check any character inside the window lies outside. Then expand the current window.
            int currentMax = map.get(s.charAt(i));
            int j = i + 1;
            while (j < currentMax) {
                currentMax = Math.max(currentMax, map.get(s.charAt(j)));
                j += 1;
            }

            result.add(currentMax - i + 1);
            i = currentMax + 1;
        }

        return result;
    }

    // Leetcode problem: 819
    /*
     *  Most Common Word.
     * */
    public String mostCommonWord(String paragraph, String[] banned) {
        String[] splitted = paragraph.toLowerCase().split("[\\s!?',;.]+");

        Set<String> bannedSet = new HashSet<>(Arrays.asList(banned));

        Map<String, Integer> countMap = new HashMap<>();
        String ans = "";
        int max = 0;
        for (String str : splitted) {
            if (!bannedSet.contains(str)) {
                int count = countMap.getOrDefault(str, 0) + 1;
                countMap.put(str, count);

                if (count > max) {
                    max = count;
                    ans = str;
                }
            }
        }

        return ans;
    }

    // Leetcode problem:692
    /*
     *  Top K Frequent Words
     * */
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> countMap = new HashMap<>();

        for (String word : words) {
            int count = countMap.getOrDefault(word, 0) + 1;
            countMap.put(word, count);
        }

        List<WordCount> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            list.add(new WordCount(entry.getKey(), entry.getValue()));
        }

        PriorityQueue<WordCount> queue = new PriorityQueue<>(list);
        List<String> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            result.add(queue.poll().word);
        }

        return result;
    }

}

class WordCount implements Comparable<WordCount> {
    String word;
    int count;

    public WordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    @Override
    public int compareTo(WordCount wc) {
        if (this.count == wc.count) {
            return this.word.compareTo(wc.word);
        }

        return wc.count - this.count;
    }
}
