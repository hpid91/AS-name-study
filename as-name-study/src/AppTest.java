
public class AppTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int bitmask = 0x000F;
        int val = 0x2222;
        // prints "2"
        System.out.println("val=" + val);
        System.out.println("bitmask=" + bitmask);
        System.out.println("&:" + (val & bitmask));
        System.out.println("^:" + (val ^ bitmask));
        System.out.println("|:" + (val | bitmask));
        System.out.println(">>:" + (val >> bitmask));
        System.out.println(">>>:" + (val >>> bitmask));
        System.out.println("<<:" + (val << bitmask));
	}

}
