import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] nums = {4, 6, 5, 3, 2, 1};
        Arrays.sort(nums, 3, 6);
        System.out.println(Arrays.toString(nums));
    }
}
