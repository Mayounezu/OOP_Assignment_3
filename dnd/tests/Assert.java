package dnd.tests;

public class Assert {
    private static int passCount = 0;
    private static int failCount = 0;

    public static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null ? actual == null : expected.equals(actual)) {
            passCount++;
        } else {
            failCount++;
            System.out.println("FAIL: " + message + " - expected <" + expected + "> but was <" + actual + ">");
        }
    }

    public static void assertTrue(String message, boolean condition) {
        if (condition) {
            passCount++;
        } else {
            failCount++;
            System.out.println("FAIL: " + message);
        }
    }

    public static void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    public static void assertThrows(String message, Runnable action) {
        try {
            action.run();
            failCount++;
            System.out.println("FAIL: " + message + " - expected an exception but none was thrown");
        } catch (RuntimeException e) {
            passCount++;
        }
    }

    public static void assertDoesNotThrow(String message, Runnable action) {
        try {
            action.run();
            passCount++;
        } catch (RuntimeException e) {
            failCount++;
            System.out.println("FAIL: " + message + " - did not expect an exception but got: " + e);
        }
    }

    public static int getPassCount() {
        return passCount;
    }

    public static int getFailCount() {
        return failCount;
    }
}
