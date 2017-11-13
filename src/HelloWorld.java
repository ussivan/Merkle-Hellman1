import java.util.BitSet;
 
public class HelloWorld {
    public static void main(String[] args) {
        String s = "hello@^@$05oiejfcq3941[u204";
        System.out.println(s);
        BitSet a = stringToBitSet(s);
        System.out.println(a);
        s = bitSetToString(a);
        System.out.println(s);
    }
 
    static String bitSetToString(BitSet a) {
        int length = (a.length() + Character.SIZE - 1)
                / Character.SIZE;
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            BitSet character =
                    a.get(Character.SIZE * i,
                    Character.SIZE * (i + 1));
            result[i] = (char)
                    character.toLongArray()[0];
        }
        return new String(result);
    }
 
    static BitSet stringToBitSet(String s) {
        BitSet a = new BitSet();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            for (int j = 0; j < Character.SIZE; j++) {
                boolean bit = ((c >> j) & 1) > 0;
                a.set(Character.SIZE * i + j, bit);
            }
        }
        return a;
    }
}