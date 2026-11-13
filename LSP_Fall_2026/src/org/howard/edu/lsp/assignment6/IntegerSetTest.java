package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Assignment 6: JUnit 5 tests for IntegerSet.
 *
 * Complete every TODO. Do not remove, rename, or disable test methods.
 * Each test must contain meaningful assertions and run independently.
 */
public class IntegerSetTest {

    // ===== clear() =====

    /**
     * Create a populated set; call clear(); assert that it is empty and its length is zero.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testClearClearsPopulatedSet() {
        IntegerSet set = set(1, 2, 3);

        set.clear();

        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
        assertEquals("[]", set.toString());
    }

    /**
     * Clear an already empty set; verify it remains empty.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testClearClearsEmptySet() {
        IntegerSet set = new IntegerSet();

        set.clear();

        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
        assertEquals("[]", set.toString());
    }

    // ===== length() =====

    /**
     * Add repeated and unique integers; assert the number of distinct elements.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLengthCountsUniqueElements() {
        IntegerSet set = new IntegerSet();
        set.add(3);
        set.add(3);
        set.add(2);
        set.add(1);
        set.add(1);

        int length = set.length();

        assertEquals(3, length);
        assertEquals("[1, 2, 3]", set.toString());
    }

    /**
     * Verify a new empty set has length zero.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLengthEmptySetLength() {
        IntegerSet set = new IntegerSet();

        int length = set.length();

        assertEquals(0, length);
        assertTrue(set.isEmpty());
    }

    // ===== equals() =====

    /**
     * Construct sets with identical elements in different insertion orders; assert equality.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsIgnoresInsertionOrder() {
        IntegerSet first = set(1, 2, 3);
        IntegerSet second = set(3, 1, 2);

        boolean equal = first.equals(second);

        assertTrue(equal);
        assertTrue(second.equals(first));
        assertNotSame(first, second);
    }

    /**
     * Construct sets of equal length with different elements; assert inequality.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsDetectsDifferentContents() {
        IntegerSet first = set(1, 2);
        IntegerSet second = set(1, 3);

        boolean equal = first.equals(second);

        assertFalse(equal);
        assertEquals(first.length(), second.length());
    }

    /**
     * Verify two separately constructed empty sets compare equal.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsEmptySetsEqual() {
        IntegerSet first = new IntegerSet();
        IntegerSet second = new IntegerSet();

        boolean equal = first.equals(second);

        assertTrue(equal);
        assertNotSame(first, second);
        assertEquals("[]", first.toString());
        assertEquals("[]", second.toString());
    }

    /**
     * Verify a set equals itself and differs from a set of another size.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsSameObjectAndDifferentSizes() {
        IntegerSet set = set(-1, 0);
        IntegerSet smaller = set(-1);

        assertTrue(set.equals(set));
        assertFalse(set.equals(smaller));
        assertEquals(2, set.length());
        assertEquals(1, smaller.length());
    }

    // ===== contains() =====

    /**
     * Verify contains() is true for a present element and false for an absent one.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testContainsPresentAndAbsent() {
        IntegerSet set = set(-3, 0, 9);

        assertTrue(set.contains(-3));
        assertFalse(set.contains(8));
        assertEquals(3, set.length());
    }

    /**
     * Verify an empty set does not contain an arbitrary integer.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testContainsEmptySetContainsNothing() {
        IntegerSet set = new IntegerSet();

        boolean containsZero = set.contains(0);

        assertFalse(containsZero);
        assertEquals(0, set.length());
    }

    // ===== largest() =====

    /**
     * Verify largest() returns the maximum across negative, zero, and positive values.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLargestNegativeAndPositiveValues() {
        IntegerSet set = set(-20, -5, 0, 13);

        int largest = set.largest();

        assertEquals(13, largest);
        assertEquals("[-20, -5, 0, 13]", set.toString());
    }

    /**
     * Use assertThrows() to verify IllegalStateException for an empty set.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLargestEmptySetThrowsException() {
        IntegerSet set = new IntegerSet();

        assertThrows(IllegalStateException.class, () -> set.largest());

        assertTrue(set.isEmpty());
    }

    // ===== smallest() =====

    /**
     * Verify smallest() returns the minimum across negative, zero, and positive values.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSmallestNegativeAndPositiveValues() {
        IntegerSet set = set(20, -5, 0, 13);

        int smallest = set.smallest();

        assertEquals(-5, smallest);
        assertEquals("[-5, 0, 13, 20]", set.toString());
    }

    /**
     * Use assertThrows() to verify IllegalStateException for an empty set.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSmallestEmptySetThrowsException() {
        IntegerSet set = new IntegerSet();

        assertThrows(IllegalStateException.class, () -> set.smallest());

        assertTrue(set.isEmpty());
    }

    // ===== add() =====

    /**
     * Add the same integer repeatedly; verify only one copy exists.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testAddIgnoresDuplicates() {
        IntegerSet set = new IntegerSet();

        set.add(4);
        set.add(4);
        set.add(4);

        assertEquals(1, set.length());
        assertTrue(set.contains(4));
        assertEquals("[4]", set.toString());
    }

    /**
     * Add Integer.MIN_VALUE, Integer.MAX_VALUE, and zero; verify all are represented.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testAddHandlesIntegerBoundaries() {
        IntegerSet set = new IntegerSet();

        set.add(Integer.MAX_VALUE);
        set.add(Integer.MIN_VALUE);
        set.add(0);

        assertEquals(3, set.length());
        assertTrue(set.contains(Integer.MIN_VALUE));
        assertTrue(set.contains(0));
        assertTrue(set.contains(Integer.MAX_VALUE));
        assertEquals("[-2147483648, 0, 2147483647]", set.toString());
        assertEquals(Integer.MIN_VALUE, set.smallest());
        assertEquals(Integer.MAX_VALUE, set.largest());
    }

    // ===== remove() =====

    /**
     * Remove a present element; verify it is absent and others remain.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testRemoveRemovesExistingElement() {
        IntegerSet set = set(1, 2, 3);

        set.remove(2);

        assertFalse(set.contains(2));
        assertTrue(set.contains(1));
        assertTrue(set.contains(3));
        assertEquals(2, set.length());
        assertEquals("[1, 3]", set.toString());
    }

    /**
     * Remove an absent element; verify the set is unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testRemoveIgnoresAbsentElement() {
        IntegerSet set = set(1);

        set.remove(9);

        assertEquals(1, set.length());
        assertTrue(set.contains(1));
        assertFalse(set.contains(9));
        assertEquals("[1]", set.toString());
    }

    // ===== union() =====

    /**
     * Verify union() contains all distinct elements from two overlapping sets.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testUnionOverlappingSets() {
        IntegerSet first = set(-3, 0, 8);
        IntegerSet second = set(8, 11);

        IntegerSet result = first.union(second);

        assertEquals("[-3, 0, 8, 11]", result.toString());
        assertEquals(4, result.length());
        assertNotSame(first, result);
        assertNotSame(second, result);
        assertEquals("[-3, 0, 8]", first.toString());
        assertEquals("[8, 11]", second.toString());
    }

    /**
     * Verify union with an empty set and with the same object.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testUnionEmptyAndSameOperand() {
        IntegerSet set = set(1, 2);

        IntegerSet withEmpty = set.union(new IntegerSet());
        IntegerSet withSelf = set.union(set);

        assertEquals("[1, 2]", withEmpty.toString());
        assertEquals("[1, 2]", withSelf.toString());
        assertNotSame(set, withEmpty);
        assertNotSame(set, withSelf);
        assertEquals("[1, 2]", set.toString());
    }

    /**
     * Verify both inputs remain unchanged; modify the result and verify the inputs are still unchanged.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testUnionDoesNotMutateInputs() {
        IntegerSet first = set(1, 2);
        IntegerSet second = set(2, 3);

        IntegerSet result = first.union(second);

        assertEquals("[1, 2, 3]", result.toString());
        assertNotSame(first, result);
        assertNotSame(second, result);
        assertEquals("[1, 2]", first.toString());
        assertEquals("[2, 3]", second.toString());

        result.add(99);

        assertEquals("[1, 2, 3, 99]", result.toString());
        assertEquals("[1, 2]", first.toString());
        assertEquals("[2, 3]", second.toString());
    }

    // ===== intersect() =====

    /**
     * Verify intersection contains exactly the shared elements.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIntersectOverlappingSets() {
        IntegerSet first = set(-3, 0, 8);
        IntegerSet second = set(8, -3, 11);

        IntegerSet result = first.intersect(second);

        assertEquals("[-3, 8]", result.toString());
        assertEquals(2, result.length());
        assertTrue(result.contains(-3));
        assertTrue(result.contains(8));
        assertFalse(result.contains(0));
        assertFalse(result.contains(11));
        assertEquals("[-3, 0, 8]", first.toString());
        assertEquals("[-3, 8, 11]", second.toString());
    }

    /**
     * Verify disjoint intersection is empty and self-intersection is unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIntersectDisjointAndSameOperand() {
        IntegerSet disjoint = set(1).intersect(set(2));
        IntegerSet set = set(1, 2);

        IntegerSet self = set.intersect(set);

        assertEquals("[]", disjoint.toString());
        assertTrue(disjoint.isEmpty());
        assertEquals("[1, 2]", self.toString());
        assertNotSame(set, self);
        assertEquals("[1, 2]", set.toString());
    }

    /**
     * Modify the intersection result and verify both input sets remain unchanged.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testIntersectIndependentResult() {
        IntegerSet first = set(1, 2);
        IntegerSet second = set(2, 3);

        IntegerSet result = first.intersect(second);

        assertEquals("[2]", result.toString());
        assertNotSame(first, result);
        assertNotSame(second, result);

        result.remove(2);

        assertEquals("[]", result.toString());
        assertEquals("[1, 2]", first.toString());
        assertEquals("[2, 3]", second.toString());
    }

    // ===== diff() =====

    /**
     * Verify A.diff(B) returns elements in A but not B, and distinguish direction.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testDiffDirectionMatters() {
        IntegerSet first = set(1, 2, 3);
        IntegerSet second = set(2, 3, 4);

        IntegerSet firstMinusSecond = first.diff(second);
        IntegerSet secondMinusFirst = second.diff(first);

        assertEquals("[1]", firstMinusSecond.toString());
        assertEquals("[4]", secondMinusFirst.toString());
        assertFalse(firstMinusSecond.equals(secondMinusFirst));
        assertEquals("[1, 2, 3]", first.toString());
        assertEquals("[2, 3, 4]", second.toString());
    }

    /**
     * Verify difference with an empty set and difference with itself.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testDiffEmptyAndSameOperand() {
        IntegerSet set = set(1, 2);

        IntegerSet selfDiff = set.diff(set);
        IntegerSet diffEmpty = set.diff(new IntegerSet());

        assertEquals("[]", selfDiff.toString());
        assertTrue(selfDiff.isEmpty());
        assertNotSame(set, selfDiff);
        assertEquals("[1, 2]", diffEmpty.toString());
        assertNotSame(set, diffEmpty);
        assertEquals("[1, 2]", set.toString());
    }

    /**
     * Modify the difference result and verify neither input changes.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testDiffIndependentResult() {
        IntegerSet first = set(1, 2);
        IntegerSet second = set(2, 3);

        IntegerSet result = first.diff(second);

        assertEquals("[1]", result.toString());
        assertNotSame(first, result);
        assertNotSame(second, result);

        result.add(99);

        assertEquals("[1, 99]", result.toString());
        assertEquals("[1, 2]", first.toString());
        assertEquals("[2, 3]", second.toString());
    }

    // ===== complement() =====

    /**
     * Verify A.complement(B) returns elements in B but not A.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testComplementReverseDifference() {
        IntegerSet first = set(1, 2, 3);
        IntegerSet second = set(2, 3, 4);

        IntegerSet result = first.complement(second);

        assertEquals("[4]", result.toString());
        assertTrue(result.contains(4));
        assertFalse(result.contains(1));
        assertFalse(result.contains(2));
        assertFalse(result.contains(3));
        assertEquals("[1]", first.diff(second).toString());
        assertEquals("[1, 2, 3]", first.toString());
        assertEquals("[2, 3, 4]", second.toString());
    }

    /**
     * Verify complement with an empty receiver and with an empty argument.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testComplementEmptyOperands() {
        IntegerSet empty = new IntegerSet();
        IntegerSet value = set(5);

        IntegerSet emptyComplement = empty.complement(value);
        IntegerSet complementEmpty = value.complement(new IntegerSet());

        assertEquals("[5]", emptyComplement.toString());
        assertEquals("[]", complementEmpty.toString());
        assertTrue(empty.isEmpty());
        assertEquals("[5]", value.toString());
    }

    /**
     * Modify the complement result and verify neither input changes.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testComplementIndependentResult() {
        IntegerSet first = set(1, 2);
        IntegerSet second = set(2, 3);

        IntegerSet result = first.complement(second);

        assertEquals("[3]", result.toString());
        assertNotSame(first, result);
        assertNotSame(second, result);

        result.add(99);

        assertEquals("[3, 99]", result.toString());
        assertEquals("[1, 2]", first.toString());
        assertEquals("[2, 3]", second.toString());
    }

    // ===== isSubsetOf() =====

    /**
     * Verify a proper subset is recognized.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfProperSubset() {
        IntegerSet subset = set(1, 2);
        IntegerSet superset = set(1, 2, 3);

        boolean isSubset = subset.isSubsetOf(superset);

        assertTrue(isSubset);
        assertFalse(superset.isSubsetOf(subset));
    }

    /**
     * Verify a set with an element missing from the other set is not a subset.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfNotSubset() {
        IntegerSet candidate = set(1, 4);
        IntegerSet other = set(1, 2, 3);

        boolean isSubset = candidate.isSubsetOf(other);

        assertFalse(isSubset);
        assertTrue(candidate.contains(4));
        assertFalse(other.contains(4));
    }

    /**
     * Verify equal sets are subsets of each other.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsSubsetOfEqualSets() {
        IntegerSet first = set(2, 1);
        IntegerSet second = set(1, 2);

        assertTrue(first.isSubsetOf(second));
        assertTrue(second.isSubsetOf(first));
        assertTrue(first.equals(second));
    }

    /**
     * Verify the empty set is a subset of a nonempty set, but not vice versa.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfEmptyCases() {
        IntegerSet empty = new IntegerSet();
        IntegerSet nonempty = set(1);

        assertTrue(empty.isSubsetOf(nonempty));
        assertFalse(nonempty.isSubsetOf(empty));
        assertTrue(empty.isSubsetOf(new IntegerSet()));
    }

    // ===== isDisjoint() =====

    /**
     * Verify disjoint sets return true.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointNoOverlap() {
        IntegerSet first = set(1, 2);
        IntegerSet second = set(3, 4);

        assertTrue(first.isDisjoint(second));
        assertTrue(second.isDisjoint(first));
    }

    /**
     * Verify overlapping sets return false.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointOverlap() {
        IntegerSet first = set(1, 2);
        IntegerSet second = set(2, 4);

        assertFalse(first.isDisjoint(second));
        assertFalse(second.isDisjoint(first));
        assertTrue(first.contains(2));
        assertTrue(second.contains(2));
    }

    /**
     * Verify disjointness with an empty set and with the same nonempty object.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointEmptyAndSameOperand() {
        IntegerSet empty = new IntegerSet();
        IntegerSet value = set(1);

        assertTrue(empty.isDisjoint(value));
        assertTrue(value.isDisjoint(empty));
        assertFalse(value.isDisjoint(value));
        assertFalse(set(1).isDisjoint(set(1)));
    }

    // ===== symmetricDiff() =====

    /**
     * Verify symmetric difference contains elements appearing in exactly one set.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testSymmetricDiffPartiallyOverlappingSets() {
        IntegerSet first = set(-3, 0, 8, 15);
        IntegerSet second = set(-3, 8, 11, 22);

        IntegerSet result = first.symmetricDiff(second);

        assertEquals("[0, 11, 15, 22]", result.toString());
        assertFalse(result.contains(-3));
        assertFalse(result.contains(8));
        assertTrue(result.contains(0));
        assertTrue(result.contains(11));
        assertEquals("[-3, 0, 8, 15]", first.toString());
        assertEquals("[-3, 8, 11, 22]", second.toString());
        assertEquals(result.toString(), second.symmetricDiff(first).toString());
    }

    /**
     * Verify symmetric difference of identical sets is empty.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffIdenticalSets() {
        IntegerSet set = set(1, 2);

        IntegerSet sameObject = set.symmetricDiff(set);
        IntegerSet equalContents = set.symmetricDiff(set(2, 1));

        assertEquals("[]", sameObject.toString());
        assertTrue(sameObject.isEmpty());
        assertNotSame(set, sameObject);
        assertEquals("[]", equalContents.toString());
        assertEquals("[1, 2]", set.toString());
    }

    /**
     * Verify behavior with an empty set and with disjoint sets.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffEmptyAndDisjointSets() {
        IntegerSet empty = new IntegerSet();
        IntegerSet values = set(2, 3);

        IntegerSet withEmpty = empty.symmetricDiff(values);
        IntegerSet disjoint = set(1).symmetricDiff(set(2));

        assertEquals("[2, 3]", withEmpty.toString());
        assertEquals("[1, 2]", disjoint.toString());
        assertTrue(empty.isEmpty());
        assertEquals("[2, 3]", values.toString());
    }

    /**
     * Modify the result and verify both input sets remain unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffIndependentResult() {
        IntegerSet first = set(1, 2);
        IntegerSet second = set(2, 3);

        IntegerSet result = first.symmetricDiff(second);

        assertEquals("[1, 3]", result.toString());
        assertNotSame(first, result);
        assertNotSame(second, result);

        result.remove(1);

        assertEquals("[3]", result.toString());
        assertEquals("[1, 2]", first.toString());
        assertEquals("[2, 3]", second.toString());
    }

    // ===== isEmpty() =====

    /**
     * Verify a newly constructed set is empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyNewSet() {
        IntegerSet set = new IntegerSet();

        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
        assertEquals("[]", set.toString());
    }

    /**
     * Verify a set with an element is not empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyPopulatedSet() {
        IntegerSet set = set(0);

        assertFalse(set.isEmpty());
        assertEquals(1, set.length());
        assertTrue(set.contains(0));
    }

    /**
     * Clear a populated set and verify it is empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyAfterClear() {
        IntegerSet set = set(1);
        assertFalse(set.isEmpty());

        set.clear();

        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
        assertEquals("[]", set.toString());
    }

    // ===== toString() =====

    /**
     * Verify elements appear in ascending numeric order regardless of insertion order.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testToStringAscendingOrder() {
        IntegerSet set = new IntegerSet();
        set.add(8);
        set.add(-3);
        set.add(15);
        set.add(0);

        String text = set.toString();

        assertEquals("[-3, 0, 8, 15]", text);
    }

    /**
     * Verify an empty set formats as [].
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testToStringEmptyFormatting() {
        IntegerSet set = new IntegerSet();

        String text = set.toString();

        assertEquals("[]", text);
        assertFalse(text.contains("null"));
    }

    /**
     * Verify comma-space formatting, brackets, and duplicate suppression.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testToStringExactSpacingAndDuplicates() {
        IntegerSet set = new IntegerSet();
        set.add(2);
        set.add(1);
        set.add(2);
        set.add(3);

        String text = set.toString();

        assertEquals("[1, 2, 3]", text);
        assertFalse(text.contains("[1,2,3]"));
        assertEquals(3, set.length());
    }

    /**
     * Builds a set containing the given values, in the order supplied.
     *
     * @param values elements to add
     * @return a new set
     */
    private static IntegerSet set(int... values) {
        IntegerSet result = new IntegerSet();
        for (int value : values) {
            result.add(value);
        }
        return result;
    }
}
