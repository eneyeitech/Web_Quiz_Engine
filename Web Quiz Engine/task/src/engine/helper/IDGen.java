package engine.helper;

public class IDGen {
    private static long id = 0;

    public static long getId() {
        return ++id;
    }
}
