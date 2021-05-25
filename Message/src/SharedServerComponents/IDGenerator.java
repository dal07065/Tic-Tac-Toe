package SharedServerComponents;

public final class IDGenerator {

    private static String alphabet[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    public static String getNewID(Object thisObject) {

        String ID = "";

        for(int i = 0; i < 10; i++)
        {
            int num = (int) (Math.random() * 10);
            if(num % 2 == 0)
            {
                ID += alphabet[num];
            }
            else
            {
                ID += String.valueOf(num);
            }
        }
        return ID;
    }

}
