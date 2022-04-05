package sliding;

public class SlidingWindowSolution {
    public static void main(String[] args) {

    }

    // Leetcode problem: 978
    public int maxTurbulenceSize(int[] arr) {
        int start = 0, end, len = arr.length, maxLength = 1;


        while (start + 1 < len) {

            // Skip repeating number is start
            if (arr[start] == arr[start + 1]) {
                start++;
                continue;
            }

            end = start + 1;
            while (end + 1 < len && isTurbulent(arr, end))
                end++;

            maxLength = Math.max(maxLength, end - start + 1);

            // Slide start to end position
            start = end;

        }

        return maxLength;
    }

    private boolean isTurbulent(int[] arr, int k) {
        return (arr[k] > arr[k - 1] && arr[k] > arr[k + 1])
                || (arr[k] < arr[k - 1] && arr[k] < arr[k + 1]);
    }
}
