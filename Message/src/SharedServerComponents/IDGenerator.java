package SharedServerComponents;

public final class IDGenerator {

    public static String getNewID(Object thisObject) {
        return "" + thisObject.hashCode();
    }

}
