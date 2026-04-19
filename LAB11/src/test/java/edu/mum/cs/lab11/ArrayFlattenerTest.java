package edu.mum.cs.lab11;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayFlattenerTest {
    private ArrayFlattener flattener;

    @Before
    public void setUp() throws Exception {
        this.flattener = new ArrayFlattener();
    }

    @After
    public void tearDown() throws Exception {
        this.flattener = null;
    }

    /**
     * Test case for legitimate 2-D nested array.
     */
    @Test
    public void testFlattenArrayLegit() {
        int[][] input = {{1, 3}, {0}, {4, 5, 9}};
        int[] expected = {1, 3, 0, 4, 5, 9};
        int[] actual = flattener.flattenArray(input);
        assertArrayEquals(expected, actual);
    }

    /**
     * Test case for null input.
     */
    @Test
    public void testFlattenArrayNull() {
        int[][] input = null;
        int[] actual = flattener.flattenArray(input);
        assertNull(actual);
    }
}
