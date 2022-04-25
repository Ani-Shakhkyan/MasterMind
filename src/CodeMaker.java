import java.util.Scanner;
import java.util.*;

public class CodeMaker {

    public static boolean isNotInArray(int num,int[] arr)
    {
        for(int i = 0; i < arr.length; i++)
        {
            if(arr[i] == num)
            {
                //System.out.println("arr["+i+"] : "+arr[i]+" is equal to num " + num);
                return false;
            }
        }
        return true;
    }
    public static int[] generateCode(int length) {
        int rtn[] = new int[length];
        Random rand = new Random();
        for(int i = 0; i < length; i++)
        {
            rtn[i] = rand.nextInt(9);
        }
        return rtn;
    }

    public static void convertToArray(int value, int[] arrayOfDigits) {
        int copy = value, len = 0;
        len = arrayOfDigits.length - 1;
        for(;len>=0;len--)
        {
            arrayOfDigits[len] = value%10;
            value/=10;
        }
    }

    public static int evaluate(int[] code1, int[] code2) {
        int rightPos = 0, rightNumber = 0, forLen = code1.length, index = 0;
        int[] indexesKept = new int[forLen];

        for(int m = 1;m < indexesKept.length; m++) {
            if (code1[m] == code2[0] && code1[m] != code2[m]) {
                rightNumber++;
                break;
            }
        }
        for (int i = 0; i < forLen; i++)
        {
            if(code1[i] == code2[i])
            {
                rightPos++;
                continue;
            }
            for (int j = 0; j < forLen; j++)
            {
                if(code1[i] == code2[j] && i!=j && isNotInArray(j,indexesKept) && code1[i] != code2[i]
                        &&  code1[j] != code2[j])
                {
                    System.out.println("index of code 1: " + i+ " code2: "+j);
                    //System.out.println("code1 and code2 "+ i + " th "+ (code1[i] != code2[i]));
                    rightNumber++;
                    indexesKept[index] = j;
                    index++;
                    break;
                }
            }
        }
        return rightPos*10+rightNumber;
    }

    public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		System.out.println("Enter the number of digits in the code: ");
		int codeLength = input.nextInt(), feedback = 0, guess, steps = 0;

		/*int codeKept[] = generateCode(codeLength), */
        int codeKept[] = {2,4,8,4}, codeGuessed[] = new int[codeKept.length];
		int a = codeKept.length;
//		while (a-- > 0)
//            System.out.println(codeKept[a]);
		do {
			System.out.println("Enter your next guess: ");
			guess = input.nextInt();
            convertToArray(guess, codeGuessed);

			steps++;
//			Arrays.toString(codeKept);
//            Arrays.toString(codeGuessed);
			feedback = evaluate(codeKept, codeGuessed);

			System.out.println("The feedback " + steps + " is: " + feedback);
		} while (feedback != codeLength * 10);

		System.out.println("You guessed the code in " + steps + " steps!");
    }
}
