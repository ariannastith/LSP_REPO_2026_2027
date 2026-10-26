package org.howard.edu.lsp.midterm.inheritance;
 
public class Driver {
    private static int passed = 0;
    private static int failed = 0;
 
    private static void check(String label, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS: " + label);
        } else {
            failed++;
            System.out.println("FAIL: " + label);
        }
    }
 
    private static boolean equal(double actual, double expected) {
        return Math.abs(actual - expected) < 0.000001;
    }
 
    private static void test(String label, Employee employee,
                             double expected) {
        try {
            check(label, equal(employee.calculatePay(), expected));
        } catch (Exception e) {
            check(label, false);
        }
    }
 
    public static void main(String[] args) {
        test("Full-time 0 hours", new FullTimeEmployee(0, 20), 0);
        test("Full-time 30 hours", new FullTimeEmployee(30, 20), 600);
        test("Full-time 40 hours", new FullTimeEmployee(40, 20), 800);
        test("Full-time 45 hours", new FullTimeEmployee(45, 20), 950);
        test("Full-time 50 hours", new FullTimeEmployee(50, 10), 550);
        test("Part-time 0 hours", new PartTimeEmployee(0, 20), 0);
        test("Part-time 30 hours", new PartTimeEmployee(30, 20), 600);
        test("Part-time 45 hours", new PartTimeEmployee(45, 20), 900);
        test("Contractor 0 hours", new Contractor(0, 20), 0);
        test("Contractor 30 hours", new Contractor(30, 20), 690);
        test("Contractor 45 hours", new Contractor(45, 20), 1035);
        test("Contractor 40 hours", new Contractor(40, 10), 460);
        System.out.println("RESULT: " + passed + " PASSED, "
                           + failed + " FAILED");
    }
}
