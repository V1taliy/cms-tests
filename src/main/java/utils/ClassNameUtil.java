package utils;

public class ClassNameUtil {

    /**
     * default private constructor
     */
    private ClassNameUtil() {
    }

    /**
     * Static method throws an error specifically where to return the
     * first element of the array ( the first line ) - this is
     * the name of this class.
     *
     * @return class name from stack trace
     * @throws RuntimeException
     */
    public static String getCurrentClassName() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            return e.getStackTrace()[1].getClassName();
        }
    }

}