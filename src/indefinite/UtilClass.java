package indefinite;

public class UtilClass {
    public static void printIntArray(int[] array) {
        for (Object ob : array) {
            System.out.print(ob + " ");
        }
        System.out.println();
    }

    public static void printBooleanArray(boolean[] array) {
        for (Object ob : array) {
            System.out.print(ob + " ");
        }
        System.out.println();
    }

    public static void print2DArray(int[][] array) {
        System.out.println("[");
        for (int[] intArray : array) {
            System.out.print("\t[ ");
            for (int val : intArray) {
                System.out.print(val + " ");
            }
            System.out.println("]");
        }
        System.out.println("]");
    }

    public static void print2DArrayBoolean(boolean[][] array) {
        System.out.println("[");
        for (boolean[] intArray : array) {
            System.out.print("\t[ ");
            for (boolean val : intArray) {
                System.out.print(val + " ");
            }
            System.out.println("]");
        }
        System.out.println("]");
    }
}
