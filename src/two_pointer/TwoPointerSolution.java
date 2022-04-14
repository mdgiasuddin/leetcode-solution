package two_pointer;

import java.util.Arrays;

public class TwoPointerSolution {

    public static void main(String[] args) {
        TwoPointerSolution twoPointerSolution = new TwoPointerSolution();
    }

    public int numRescueBoats(int[] people, int limit) {

        Arrays.sort(people);
        int left = 0;
        int right = people.length - 1;
        int current = 0;
//        int result = 1; for unlimited person
        int result = 0;

        while (left <= right) {

            // If a boat can carry unlimited number of person then this is the logic
            /*if (current + people[right] <= limit) {
                current += people[right];
                right--;
            } else if (current + people[left] <= limit) {
                current += people[left];
                left++;
            } else {
                result++;
                current = 0;
            }*/

            // Since at most two person can be carried, we have to change the logic
            // Try to take 2 person first. If not possible then take only right
            if (people[left] + people[right] <= limit) {
                left++;
            }
            right--;
            result++;

        }

        return result;
    }
}
