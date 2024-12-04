public class yes {
    public static void main(String[] args) {
        String str = "mul(";
        if(str.matches("mul\\(")) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
