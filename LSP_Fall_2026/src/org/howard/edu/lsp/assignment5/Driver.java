package org.howard.edu.lsp.assignment5;

/**
 * Assignment 5 grading driver. Do not modify.
 * Run with IntegerSet.java; copy the entire console output for grading.
 */
public class Driver {
    private static int passed = 0;
    private static int total = 0;
    private static int earned = 0;
    private static int possible = 0;
    private static String category = "";
    private static boolean foundationReliable = true;
    private static int categoryEarned = 0;
    private static int categoryPossible = 0;

    private interface TestAction {
        void run() throws Throwable;
    }

    private static void start(String name) {
        finish();
        category = name;
        categoryEarned = 0;
        categoryPossible = 0;
        System.out.println("\n--- " + name + " ---");
    }

    private static void finish() {
        if (!category.equals("")) {
            System.out.println("SUBTOTAL " + category + ": " +
                categoryEarned + "/" + categoryPossible);
        }
    }

    private static void test(String name, int points, TestAction action) {
        total++;
        possible += points;
        categoryPossible += points;
        try {
            action.run();
            passed++;
            earned += points;
            categoryEarned += points;
            System.out.println("PASS [" + points + "/" + points + "] " + name);
        } catch (Throwable error) {
            System.out.println("FAIL [0/" + points + "] " + name +
                " | " + error.getClass().getSimpleName() +
                ": " + error.getMessage());
        }
    }

    private static void check(boolean result, String expected, String actual) {
        if (!result) {
            throw new AssertionError("expected=" + expected + "; actual=" + actual);
        }
    }

    private static IntegerSet set(int... values) {
        IntegerSet result = new IntegerSet();
        for (int value : values) {
            result.add(value);
        }
        return result;
    }

    private static void expectSet(IntegerSet actual, String expected) {
        String observed = actual.toString();
        check(expected.equals(observed), expected, observed);
    }

    private static void expectBoolean(boolean actual, boolean expected) {
        check(actual == expected, Boolean.toString(expected), Boolean.toString(actual));
    }

    private static void expectInt(int actual, int expected) {
        check(actual == expected, Integer.toString(expected), Integer.toString(actual));
    }

    private static void expectEmptyException(IntegerSet s, boolean largest) {
        try {
            if (largest) {
                s.largest();
            } else {
                s.smallest();
            }
            throw new AssertionError("expected IllegalStateException; no exception thrown");
        } catch (IllegalStateException expected) {
            // Correct behavior.
        }
    }

    public static void main(String[] args) {
        System.out.println("ASSIGNMENT 5 - INTEGERSET TEST RESULTS");
        System.out.println("Do not edit Driver.java. Submit the complete output.");
        // Check whether test fixtures and string-based comparisons are trustworthy.
        // A failure here may cause cascading failures in other categories.
        try {
            IntegerSet probe = new IntegerSet();
            probe.add(3);
            probe.add(-1);
            probe.add(3);
            if (probe.length() != 2 || !probe.contains(-1) ||
                    !probe.contains(3) || ! "[-1, 3]".equals(probe.toString())) {
                foundationReliable = false;
            }
        } catch (Throwable error) {
            foundationReliable = false;
        }
        if (!foundationReliable) {
            System.out.println("WARNING: FOUNDATION CHECK FAILED (add/length/contains/toString).");
            System.out.println("Dependent failures may be cascading. Score is PROVISIONAL;");
            System.out.println("instructor must inspect the ZIP before finalizing deductions.");
        }
        start("clear()");
        test("clears populated set", 2, new TestAction() {
            public void run() { IntegerSet s=set(1,2,3); s.clear(); expectSet(s,"[]"); }
        });
        test("clearing empty set", 2, new TestAction() {
            public void run() { IntegerSet s=set(); s.clear(); expectSet(s,"[]"); }
        });
        start("length()");
        test("unique count with duplicates", 2, new TestAction() {
            public void run() { expectInt(set(3,3,2,1,1).length(),3); }
        });
        test("empty length", 2, new TestAction() {
            public void run() { expectInt(set().length(),0); }
        });
        start("equals(IntegerSet)");
        test("order independent", 2, new TestAction() {
            public void run() { expectBoolean(set(1,2,3).equals(set(3,1,2)),true); }
        });
        test("different content", 2, new TestAction() {
            public void run() { expectBoolean(set(1,2).equals(set(1,3)),false); }
        });
        test("empty equals empty", 2, new TestAction() {
            public void run() { expectBoolean(set().equals(set()),true); }
        });
        test("same object and different sizes", 2, new TestAction() {
            public void run() { IntegerSet s=set(-1,0); expectBoolean(s.equals(s),true); expectBoolean(s.equals(set(-1)),false); }
        });
        start("contains(int)");
        test("present and absent", 2, new TestAction() {
            public void run() { IntegerSet s=set(-3,0,9); expectBoolean(s.contains(-3),true); expectBoolean(s.contains(8),false); }
        });
        test("empty contains", 2, new TestAction() {
            public void run() { expectBoolean(set().contains(0),false); }
        });
        start("largest()");
        test("negative and positive", 2, new TestAction() {
            public void run() { expectInt(set(-20,-5,0,13).largest(),13); }
        });
        test("empty throws IllegalStateException", 2, new TestAction() {
            public void run() { expectEmptyException(set(),true); }
        });
        start("smallest()");
        test("negative and positive", 2, new TestAction() {
            public void run() { expectInt(set(20,-5,0,13).smallest(),-5); }
        });
        test("empty throws IllegalStateException", 2, new TestAction() {
            public void run() { expectEmptyException(set(),false); }
        });
        start("add(int)");
        test("duplicates ignored", 2, new TestAction() {
            public void run() { IntegerSet s=set(4,4,4); expectInt(s.length(),1); expectSet(s,"[4]"); }
        });
        test("boundary integers", 2, new TestAction() {
            public void run() { expectSet(set(Integer.MAX_VALUE,Integer.MIN_VALUE,0),"[-2147483648, 0, 2147483647]"); }
        });
        start("remove(int)");
        test("remove existing", 2, new TestAction() {
            public void run() { IntegerSet s=set(1,2,3); s.remove(2); expectSet(s,"[1, 3]"); }
        });
        test("remove absent", 2, new TestAction() {
            public void run() { IntegerSet s=set(1); s.remove(9); expectSet(s,"[1]"); }
        });
        start("union(IntegerSet)");
        test("overlap", 2, new TestAction() {
            public void run() { expectSet(set(-3,0,8).union(set(8,11)),"[-3, 0, 8, 11]"); }
        });
        test("empty and same operand", 2, new TestAction() {
            public void run() { IntegerSet s=set(1,2); expectSet(s.union(set()),"[1, 2]"); expectSet(s.union(s),"[1, 2]"); }
        });
        test("does not mutate inputs", 3, new TestAction() {
            public void run() { IntegerSet a=set(1,2), b=set(2,3); IntegerSet r=a.union(b); expectSet(a,"[1, 2]"); expectSet(b,"[2, 3]"); r.add(99); expectSet(a,"[1, 2]"); expectSet(b,"[2, 3]"); }
        });
        start("intersect(IntegerSet)");
        test("overlap", 2, new TestAction() {
            public void run() { expectSet(set(-3,0,8).intersect(set(8,-3,11)),"[-3, 8]"); }
        });
        test("disjoint and same operand", 2, new TestAction() {
            public void run() { expectSet(set(1).intersect(set(2)),"[]"); IntegerSet s=set(1,2); expectSet(s.intersect(s),"[1, 2]"); }
        });
        test("independent result", 3, new TestAction() {
            public void run() { IntegerSet a=set(1,2), b=set(2,3); IntegerSet r=a.intersect(b); r.remove(2); expectSet(a,"[1, 2]"); expectSet(b,"[2, 3]"); }
        });
        start("diff(IntegerSet)");
        test("direction matters", 2, new TestAction() {
            public void run() { expectSet(set(1,2,3).diff(set(2,3,4)),"[1]"); }
        });
        test("empty and same operand", 2, new TestAction() {
            public void run() { IntegerSet s=set(1,2); expectSet(s.diff(s),"[]"); expectSet(s.diff(set()),"[1, 2]"); }
        });
        test("independent result", 3, new TestAction() {
            public void run() { IntegerSet a=set(1,2), b=set(2,3); IntegerSet r=a.diff(b); r.add(99); expectSet(a,"[1, 2]"); expectSet(b,"[2, 3]"); }
        });
        start("complement(IntegerSet)");
        test("assignment-specific reverse difference", 2, new TestAction() {
            public void run() { expectSet(set(1,2,3).complement(set(2,3,4)),"[4]"); }
        });
        test("empty operands", 1, new TestAction() {
            public void run() { expectSet(set().complement(set(5)),"[5]"); expectSet(set(5).complement(set()),"[]"); }
        });
        test("independent result", 2, new TestAction() {
            public void run() { IntegerSet a=set(1,2), b=set(2,3); IntegerSet r=a.complement(b); r.add(99); expectSet(a,"[1, 2]"); expectSet(b,"[2, 3]"); }
        });
        start("isSubsetOf(IntegerSet)");
        test("proper subset", 2, new TestAction() {
            public void run() { expectBoolean(set(1,2).isSubsetOf(set(1,2,3)),true); }
        });
        test("not subset", 2, new TestAction() {
            public void run() { expectBoolean(set(1,4).isSubsetOf(set(1,2,3)),false); }
        });
        test("equal sets", 1, new TestAction() {
            public void run() { expectBoolean(set(2,1).isSubsetOf(set(1,2)),true); }
        });
        test("empty cases", 2, new TestAction() {
            public void run() { expectBoolean(set().isSubsetOf(set(1)),true); expectBoolean(set(1).isSubsetOf(set()),false); }
        });
        start("isDisjoint(IntegerSet)");
        test("no overlap", 2, new TestAction() {
            public void run() { expectBoolean(set(1,2).isDisjoint(set(3,4)),true); }
        });
        test("overlap", 2, new TestAction() {
            public void run() { expectBoolean(set(1,2).isDisjoint(set(2,4)),false); }
        });
        test("empty and same operand", 2, new TestAction() {
            public void run() { expectBoolean(set().isDisjoint(set(1)),true); expectBoolean(set(1).isDisjoint(set(1)),false); }
        });
        start("symmetricDiff(IntegerSet)");
        test("partially overlapping", 3, new TestAction() {
            public void run() { expectSet(set(-3,0,8,15).symmetricDiff(set(-3,8,11,22)),"[0, 11, 15, 22]"); }
        });
        test("identical sets", 2, new TestAction() {
            public void run() { IntegerSet s=set(1,2); expectSet(s.symmetricDiff(s),"[]"); }
        });
        test("empty and disjoint", 2, new TestAction() {
            public void run() { expectSet(set().symmetricDiff(set(2,3)),"[2, 3]"); expectSet(set(1).symmetricDiff(set(2)),"[1, 2]"); }
        });
        test("independent result", 2, new TestAction() {
            public void run() { IntegerSet a=set(1,2), b=set(2,3); IntegerSet r=a.symmetricDiff(b); r.remove(1); expectSet(a,"[1, 2]"); expectSet(b,"[2, 3]"); }
        });
        start("isEmpty()");
        test("new set", 1, new TestAction() {
            public void run() { expectBoolean(set().isEmpty(),true); }
        });
        test("populated set", 1, new TestAction() {
            public void run() { expectBoolean(set(0).isEmpty(),false); }
        });
        test("after clear", 1, new TestAction() {
            public void run() { IntegerSet s=set(1); s.clear(); expectBoolean(s.isEmpty(),true); }
        });
        start("toString()");
        test("ascending order", 2, new TestAction() {
            public void run() { expectSet(set(8,-3,15,0),"[-3, 0, 8, 15]"); }
        });
        test("empty formatting", 1, new TestAction() {
            public void run() { expectSet(set(),"[]"); }
        });
        test("exact spacing and duplicate handling", 2, new TestAction() {
            public void run() { expectSet(set(2,1,2,3),"[1, 2, 3]"); }
        });
        finish();
        System.out.println("\n======================================");
        System.out.println("FUNCTIONAL SCORE: " + earned + "/" + possible +
                (foundationReliable ? "" : " (PROVISIONAL - cascading failures possible)"));
        System.out.println("TESTS PASSED: " + passed + "/" + total);
        System.out.println("MANUAL REVIEW: Javadocs /5; AI report /3");
        System.out.println("FINAL SCORE: functional score + manual review (out of 100)");
        System.out.println("NOTE: Failed foundational methods can affect dependent tests.");
        System.out.println("Instructor will review the source before finalizing such deductions.");
        System.out.println("======================================");
    }
}
