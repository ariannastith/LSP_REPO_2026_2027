package org.howard.edu.lsp.assignment5;

import java.util.Set;
import java.util.TreeSet;

/**
 * A mutable mathematical set of {@code int} values.
 *
 * <p>The set never stores duplicate values. Elements are kept in ascending
 * numerical order internally, which is also the order used by
 * {@link #toString()}.</p>
 *
 * <p>Every operation that returns an {@code IntegerSet} returns a new,
 * independent object; neither the receiver nor the argument is modified.</p>
 */
public class IntegerSet {

    /** Ascending, duplicate-free storage for the elements of this set. */
    private final TreeSet<Integer> elements = new TreeSet<Integer>();

    /**
     * Creates an empty set.
     */
    public IntegerSet() {
        // The backing TreeSet starts empty.
    }

    /**
     * Removes every element from this set.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Returns the number of distinct elements in this set.
     *
     * @return the cardinality of this set
     */
    public int length() {
        return elements.size();
    }

    /**
     * Compares this set with another set for element equality.
     *
     * <p>Two sets are equal when they contain exactly the same values,
     * regardless of the order in which the values were added.</p>
     *
     * @param b the set to compare against; may be {@code null}
     * @return {@code true} if both sets contain exactly the same elements
     */
    public boolean equals(IntegerSet b) {
        if (b == null) {
            return false;
        }
        if (b == this) {
            return true;
        }
        return elements.equals(b.elements);
    }

    /**
     * Reports whether a value is a member of this set.
     *
     * @param value the value to look for
     * @return {@code true} if {@code value} is in this set
     */
    public boolean contains(int value) {
        return elements.contains(Integer.valueOf(value));
    }

    /**
     * Returns the largest element in this set.
     *
     * @return the maximum element
     * @throws IllegalStateException if this set is empty
     */
    public int largest() {
        requireNotEmpty("largest");
        return elements.last().intValue();
    }

    /**
     * Returns the smallest element in this set.
     *
     * @return the minimum element
     * @throws IllegalStateException if this set is empty
     */
    public int smallest() {
        requireNotEmpty("smallest");
        return elements.first().intValue();
    }

    /**
     * Adds a value to this set. Values already present are ignored.
     *
     * @param item the value to add
     */
    public void add(int item) {
        elements.add(Integer.valueOf(item));
    }

    /**
     * Removes a value from this set. Values that are absent are ignored.
     *
     * @param item the value to remove
     */
    public void remove(int item) {
        elements.remove(Integer.valueOf(item));
    }

    /**
     * Returns the union of this set and {@code b}: every element that appears
     * in either set.
     *
     * @param b the other set
     * @return a new set containing the union
     * @throws IllegalArgumentException if {@code b} is {@code null}
     */
    public IntegerSet union(IntegerSet b) {
        requireNonNull(b);
        IntegerSet result = copyOf(elements);
        result.elements.addAll(b.elements);
        return result;
    }

    /**
     * Returns the intersection of this set and {@code b}: every element that
     * appears in both sets.
     *
     * @param b the other set
     * @return a new set containing the intersection
     * @throws IllegalArgumentException if {@code b} is {@code null}
     */
    public IntegerSet intersect(IntegerSet b) {
        requireNonNull(b);
        IntegerSet result = copyOf(elements);
        result.elements.retainAll(b.elements);
        return result;
    }

    /**
     * Returns the elements of this set that are not in {@code b}.
     *
     * @param b the other set
     * @return a new set containing the difference
     * @throws IllegalArgumentException if {@code b} is {@code null}
     */
    public IntegerSet diff(IntegerSet b) {
        requireNonNull(b);
        IntegerSet result = copyOf(elements);
        result.elements.removeAll(b.elements);
        return result;
    }

    /**
     * Returns the elements of {@code b} that are not in this set.
     *
     * <p>This is the assignment-specific definition of complement, the reverse
     * difference rather than a universe-based complement.</p>
     *
     * @param b the other set
     * @return a new set containing the reverse difference
     * @throws IllegalArgumentException if {@code b} is {@code null}
     */
    public IntegerSet complement(IntegerSet b) {
        requireNonNull(b);
        IntegerSet result = copyOf(b.elements);
        result.elements.removeAll(elements);
        return result;
    }

    /**
     * Reports whether every element of this set is also in {@code b}. An empty
     * set is a subset of every set.
     *
     * @param b the candidate superset
     * @return {@code true} if this set is a subset of {@code b}
     * @throws IllegalArgumentException if {@code b} is {@code null}
     */
    public boolean isSubsetOf(IntegerSet b) {
        requireNonNull(b);
        return b.elements.containsAll(elements);
    }

    /**
     * Reports whether this set and {@code b} share no elements. An empty set is
     * disjoint from every set.
     *
     * @param b the other set
     * @return {@code true} if the two sets have no element in common
     * @throws IllegalArgumentException if {@code b} is {@code null}
     */
    public boolean isDisjoint(IntegerSet b) {
        requireNonNull(b);
        Set<Integer> shared = new TreeSet<Integer>(elements);
        shared.retainAll(b.elements);
        return shared.isEmpty();
    }

    /**
     * Returns the symmetric difference of this set and {@code b}: the elements
     * that occur in exactly one of the two sets.
     *
     * @param b the other set
     * @return a new set containing the symmetric difference
     * @throws IllegalArgumentException if {@code b} is {@code null}
     */
    public IntegerSet symmetricDiff(IntegerSet b) {
        requireNonNull(b);
        IntegerSet result = copyOf(elements);
        for (Integer value : b.elements) {
            if (!result.elements.remove(value)) {
                result.elements.add(value);
            }
        }
        return result;
    }

    /**
     * Reports whether this set has no elements.
     *
     * @return {@code true} if this set is empty
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Returns the elements in ascending numerical order, separated by a comma
     * and one space, inside square brackets, for example {@code [-3, 0, 8, 15]}.
     * An empty set produces {@code []}.
     *
     * @return the formatted contents of this set
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        boolean first = true;
        for (Integer value : elements) {
            if (!first) {
                builder.append(", ");
            }
            builder.append(value);
            first = false;
        }
        return builder.append("]").toString();
    }

    /**
     * Builds a new set holding a copy of the given values.
     *
     * @param values the values to copy
     * @return a new independent set
     */
    private static IntegerSet copyOf(Set<Integer> values) {
        IntegerSet result = new IntegerSet();
        result.elements.addAll(values);
        return result;
    }

    /**
     * Rejects a {@code null} set argument.
     *
     * @param b the argument to validate
     * @throws IllegalArgumentException if {@code b} is {@code null}
     */
    private static void requireNonNull(IntegerSet b) {
        if (b == null) {
            throw new IllegalArgumentException("the other IntegerSet must not be null");
        }
    }

    /**
     * Rejects an operation that is undefined on an empty set.
     *
     * @param operation the name of the operation being attempted
     * @throws IllegalStateException if this set is empty
     */
    private void requireNotEmpty(String operation) {
        if (elements.isEmpty()) {
            throw new IllegalStateException(operation + "() is undefined for an empty set");
        }
    }
}
