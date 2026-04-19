package edu.mum.cs.lab11;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ArrayReversorTest {
    private ArrayReversor reversor;
    private ArrayFlattenerService flattenerService;

    @Before
    public void setUp() throws Exception {
        this.flattenerService = mock(ArrayFlattenerService.class);
        this.reversor = new ArrayReversor(flattenerService);
    }

    @After
    public void tearDown() throws Exception {
        this.flattenerService = null;
        this.reversor = null;
    }

    /**
     * Test case for legitimate 2-D nested array.
     */
    @Test
    public void testReverseArrayLegit() {
        int[][] input = {{1, 3}, {0}, {4, 5, 9}};
        int[] flattened = {1, 3, 0, 4, 5, 9};
        int[] expected = {9, 5, 4, 0, 3, 1};

        // Define mock behavior
        when(flattenerService.flattenArray(input)).thenReturn(flattened);

        int[] actual = reversor.reverseArray(input);

        // Verify service invocation
        verify(flattenerService).flattenArray(input);
        
        assertArrayEquals(expected, actual);
    }

    /**
     * Test case for null input.
     */
    @Test
    public void testReverseArrayNull() {
        int[][] input = null;

        // Define mock behavior
        when(flattenerService.flattenArray(input)).thenReturn(null);

        int[] actual = reversor.reverseArray(input);

        // Verify service invocation
        verify(flattenerService).flattenArray(input);

        assertNull(actual);
    }
}
