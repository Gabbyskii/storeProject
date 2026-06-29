import java.util.Scanner;

public class TextUI {
    private Scanner scan = new Scanner(System.in);

    public String promptText(String msg){
        System.out.println(msg);
        return scan.nextLine();
    }

    public double promptNum(String msg) {
        System.out.println(msg);
        return Double.parseDouble(scan.nextLine());
    }



}
