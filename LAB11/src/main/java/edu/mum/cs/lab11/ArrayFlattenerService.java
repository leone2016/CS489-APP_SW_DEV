package edu.mum.cs.lab11;

/**
 * Service interface for flattening arrays.
 */
public interface ArrayFlattenerService {
    /**
     * Flattens a 2-D nested array into a 1-D array.
     * @param a_in The 2-D nested array.
     * @return A flattened 1-D array.
     */
    int[] flattenArray(int[][] a_in);
}
