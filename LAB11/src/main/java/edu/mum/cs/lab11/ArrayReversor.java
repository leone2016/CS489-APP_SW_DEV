package edu.mum.cs.lab11;

import java.util.Arrays;

/**
 * ArrayReversor component that uses ArrayFlattenerService to flatten and then reverse an array.
 */
public class ArrayReversor {
    private ArrayFlattenerService flattenerService;

    public ArrayReversor(ArrayFlattenerService flattenerService) {
        this.flattenerService = flattenerService;
    }

    /**
     * Flattens and reverses a two-dimensional (2-D) nested array.
     *
     * @param a_in The input 2-D nested array.
     * @return A flattened and reversed 1-D array.
     *         Returns null if the input is null.
     */
    public int[] reverseArray(int[][] a_in) {
        int[] flattened = flattenerService.flattenArray(a_in);
        if (flattened == null) {
            return null;
        }

        int[] reversed = new int[flattened.length];
        for (int i = 0; i < flattened.length; i++) {
            reversed[i] = flattened[flattened.length - 1 - i];
        }
        return reversed;
    }

    public static void main(String[] args) {
        // Implementation note: In a real app, flattenerService would be injected.
        // This main method is just for structure.
    }
}
