public class test2 {

    public static void main(String[] args) {

        String i1 = "abc";
        String i2 = "abc";
        String i5 = i1 + i2;
        String i6 = "abcabc";

        String i3 = new String("abc");
        String i4 = new String("abc");


        System.out.println(i1 == i2);
        System.out.println(i6 == i5);
        System.out.println(i6.equals(i5));

        StringBuffer sb = new StringBuffer("123");




        /*Integer i1 = 40;
        Integer i2 = 40;
        Integer i3 = 0;
        Integer i4 = new Integer(40);
        Integer i5 = new Integer(40);
        Integer i6 = new Integer(0);

        System.out.println("i1=i2   " + (i1 == i2));  //true
        System.out.println("i1=i2+i3   " + (i1 == i2 + i3));//true
        System.out.println("i1=i4   " + (i1 == i4));  //false
        System.out.println("i4=i5   " + (i4 == i5));//false
        System.out.println("i4=i5+i6   " + (i4 == i5 + i6));
        System.out.println("40=i5+i6   " + (40 == i5 + i6));*/

    }
}
