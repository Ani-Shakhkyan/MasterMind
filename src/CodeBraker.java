import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class CodeBraker {

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

    public static int powerOfTen(int power)
    {
        int powerOfTen = 1;
        while(power > 0)
        {
            powerOfTen*=10;
            power--;
        }
        return powerOfTen;
    }
    public static int convertToInteger(int[] arrayOfDigits)
    {
        int ten = powerOfTen(arrayOfDigits.length - 1), rtn = 0, i = 0;
        while (ten >= 1)
        {
            rtn += arrayOfDigits[i] * ten;
            ten/=10;
            i++;
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
    public static int[][] generatePool(int length)
    {
        int powerOfTen = powerOfTen(length);

        int ret[][] = new int[powerOfTen][length];
        for(int i = 0; i < powerOfTen; i++)
        {
           convertToArray(i,ret[i]);
        }
        return ret;
    }

    public static int chooseFromPool(int[][] pool) {
        int len = pool[0].length , powerOfTen = powerOfTen(len-1), number = 0;
        for(int i = 1; i <=len; i++)
        {
            number += i*powerOfTen;
            powerOfTen/=10;
        }
        return convertToInteger(pool[number]);
    }

    public static int chooseFromPool(int[][] pool, int feedback, int previousGuess) {
        Random rand = new Random();

        int randRet = 0, randIndex = 0, len = pool.length - 1;
        if(len > 0)
            randIndex = rand.nextInt(len);
        //System.out.println("randindex is: "+ randIndex);
        if(pool.length == 0)
            return 0;
        randRet = convertToInteger(pool[randIndex]);
        return randRet;
    }
    public static int[][] refreshPool(int[][] pool, int feedback, int previousGuess) {
        int[] prevGuess = new int[pool[0].length];
        convertToArray(previousGuess,prevGuess);
        int count = 0;
        System.out.println("pool length is "+ pool.length);
        for(int j = 0; j < pool.length;j++)
        {
            System.out.println("prevguess "+Arrays.toString(prevGuess)+ "and "+Arrays.toString(pool[j])+" have "+ evaluate(prevGuess,pool[j]));
            if(evaluate(prevGuess,pool[j]) == feedback){
                count++;
            };
        }
        System.out.println("there are "+count+" elements with matching feedback");
        int[][] filteredPool = new int[count][prevGuess.length];
        count--;
        for (int m = 0; m < pool.length;m++)
        {
            if(evaluate(prevGuess,pool[m]) == feedback){
                {
                    for (int i = 0;count>=0&& i < pool[0].length; i++) {
                        filteredPool[count][i] = pool[m][i];
                    }
                    count--;
                }
            }
        }
        return filteredPool;
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
                    //System.out.println("index of code 1: " + i+ " code2: "+j);
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
        int[] code1 = {6,7,2,1};
        int[] code2 = {8,1,4,6};
        System.out.println(evaluate(code2,code1));
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of digits in the code: ");
        int codeLength = input.nextInt(), feedback = 0, steps = 0;

        int[][] codePool = generatePool(codeLength);
        int guess = chooseFromPool(codePool);

        do {
            System.out.println("My next guess is: " + guess);
            System.out.println("Enter the feedback: ");
            feedback = input.nextInt();

            steps++;
            if(feedback > 0)
                codePool = refreshPool(codePool, feedback, guess);
            System.out.println("length of refreshed pool is " + codePool.length);
            for (int a = 0; a < codePool.length;a++)
                System.out.println(Arrays.toString(codePool[a]));
            guess = chooseFromPool(codePool, feedback, guess);
        } while (feedback != codeLength * 10);
        System.out.println("I guessed the code in " + steps + " steps!");
    }
}