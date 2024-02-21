package FirstLab;

import FirstLab.FirstPackage.FirstPackage;

public class FirstClass {
    public static void main(String[] s) {
        FirstPackage o = new FirstPackage(10, 15);
        int i, j;
        for (i = 1; i <= 8; i++) {
            for(j = 1; j <= 8; j++) {
                o.setFirst(i);
                o.setSecond(j);
                System.out.print(o.getSum());
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}


