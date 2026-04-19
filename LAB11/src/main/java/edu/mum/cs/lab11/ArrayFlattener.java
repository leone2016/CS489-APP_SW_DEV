package edu.mum.cs.lab11;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * ArrayFlattener component that provides a method to flatten a 2D array.
 */
public class ArrayFlattener {

    /**
     * Flattens a two-dimensional (2-D) nested array into a one-dimensional (1-D) array.
     *
     * @param a_in The input 2-D nested array.
     * @return A flattened 1-D array containing all elements of the input array.
     *         Returns null if the input is null.
     */
    public int[] flattenArray(int[][] a_in) {
        if (a_in == null) {
            return null;
        }

        return Arrays.stream(a_in)
                .filter(Objects::nonNull) // Handle potential null inner arrays safely
                .flatMapToInt(Arrays::stream)
                .toArray();
    }

    public static void main(String[] args) {
        // Simple manual test
        ArrayFlattener flattener = new ArrayFlattener();
        int[][] input = {{1, 3}, {0}, {4, 5, 9}};
        int[] output = flattener.flattenArray(input);
        System.out.println("Input: " + Arrays.deepToString(input));
        System.out.println("Output: " + Arrays.toString(output));
    }
}
