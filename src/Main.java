import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        String content = Files.readString(Paths.get("input.txt"));

        //xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))

        // do() and don't() en/disables the mul()

        Stack<String> primaryStack = new Stack<>();
        Stack<Integer> numberStack = new Stack<>();
        Stack<String> doStack = new Stack<>();
        doStack.add("do()");
       // String command = "mul(0,0)";

        int mul1 = 0;

        int result = 0;

        char[] contentChar = content.toCharArray();

        for (int i = 0; i < contentChar.length; i++) {
            char c = contentChar[i];
            if(c == 'm' && primaryStack.empty() && doStack.peek().equals("do()")) {
                primaryStack.add(Character.toString(c));
                continue;
            }
            if(c == 'd') {
                if(i+4 <= contentChar.length && content.startsWith("do()", i)) {
                    doStack.add("do()");
                } else if(i + 7 <= contentChar.length && content.startsWith("don't()", i)) {
                    doStack.add("don't()");
                }
            }
            if(primaryStack.empty()) {
                continue;
            }
            if(primaryStack.peek().equals("don't()")) {
                continue;
            }
            if(primaryStack.peek().equals("m")) {
                // go through command
                String startCmd = "";
                if(i + 3 <= contentChar.length)
                    startCmd = content.substring(i-1, i+3); // "mul("
                //System.out.println(startCmd);
                if(!startCmd.isBlank() && !startCmd.equals("mul(")) {
                    primaryStack.clear();
                    continue;
                }
                // it will have the starting cmd
                i+=2;
                primaryStack.pop();
                primaryStack.add("mul(");
                continue;
            }
            if(primaryStack.peek().equals("mul(")) {
                if(Character.toString(c).matches("[0-9]")) numberStack.add(c - '0');
                else if (c == ',') {
                    int size = numberStack.size();
                    System.out.println("size: " + size);
                    for (int j = 0; j < size; j++) {
                        int popped = numberStack.pop();

                        System.out.println(popped);
                        int num = popped * ((int) Math.pow(10, j));
                        mul1 += num;
                    }
                    System.out.println("mul1: " + mul1);
                    primaryStack.pop();
                    primaryStack.add(",");
                } else {
                    //invalid character! start over
                    primaryStack.clear();
                    numberStack.clear();
                    mul1 = 0;
                }
                continue;
            }
            if(primaryStack.peek().equals(",")) {
                if(Character.toString(c).matches("[0-9]")) numberStack.add(c - '0');
                else if (c == ')') {
                    int size = numberStack.size();
                    System.out.println("Size: " + numberStack.size());
                    int mul2 = 0;
                    for (int j = 0; j < size; j++) {
                        int num = numberStack.pop() * ((int) Math.pow(10, j));
                        mul2 += num;
                    }
                    System.out.println("mul2: " + mul2);
                    result += mul1 * mul2;
                    mul1 = 0;
                    primaryStack.clear();
                } else {
                    // invalid character!
                    primaryStack.clear();
                    numberStack.clear();
                    mul1 = 0;
                }
            }
        }
        System.out.println(result);

    }
}