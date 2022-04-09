import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class HammingCode {
    static int word_size = 7;
    static String dataword = "";
    static String codeword_ = "";
    static ArrayList<Integer> codeword_before_encode = new ArrayList<Integer>();
    static ArrayList<Integer> codeword = new ArrayList<Integer>();
    static String checkcodeword = "";
    static int bit_error = -1;
    static String final_codeword = "";
    static int testcase = 1;
    static int checkcase = 1;
    static int error_postion;

    public static int random_bits() {
        int bit;
        Random rand = new Random();
        bit = rand.nextInt(2); // random 1 and 0
        return bit;
    }

    public static String randdom_dataword() {
        dataword = "";
        codeword_ = "";
        for (int i = 0; i < word_size; i++) {
            int bit = random_bits();
            dataword += bit;
        }
        return dataword;
    }

    public static void Hamming_gen(String dataword) {
        int i, j = 0;
        int r1, r2, r4, r8, sum;

        codeword_before_encode.clear();
        codeword.clear();
        final_codeword = "";

        String[] datawordSprit = dataword.split("");
        ArrayList<String> dataStrList = new ArrayList<String>(Arrays.asList(datawordSprit));
        for (i = 0; i < word_size; i++) {
            // System.out.println(dataStrList.get(i));
            int currentBitString = Integer.parseInt(dataStrList.get(i));
            codeword_before_encode.add(currentBitString);
        }
        double n = 1;
        // xn =ar^(n-1)
        j = word_size - 1;
        int k = 10;
        for (i = 0; i < word_size + 4; i++) {
            codeword.add(null);
        }
        // Operation
        for (i = 0; i < word_size + 4; i++) {
            // System.out.println(n);
            double flag = 1 * (Math.pow(2, n - 1));
            if (i + 1 == flag) {
                codeword.set(k, null);
                n++;
            } else {
                codeword.set(k, codeword_before_encode.get(j));
                j--;
            }
            k--;
            // System.out.println("i is " + i + " Flag is " + flag);

        }
        // System.out.println("Test" + codeword);
        // R1
        sum = 0;
        for (i = 0; i < word_size + 4; i++) {
            if (i % 2 == 0 && i != 10)
                sum += codeword.get(i);
        }
        if (sum % 2 == 0)
            r1 = 0;
        else
            r1 = 1;
        // System.out.println(sum);
        // System.out.println(r1);
        codeword.set(10, r1);
        // System.out.println(codeword);

        boolean check = false;
        int temp = 0;
        int a = 0;
        sum = 0;
        // System.out.println(sum);
        // System.out.println(codeword);
        // R2
        for (i = 0; i < word_size + 4; i++) {
            if (check == false) {
                temp++;
            }
            if (temp == 3) {
                temp = 0;
                check = true;
            }
            if (check == true) {
                a++;
            }
            if (a == 2) {
                a = 0;
                check = false;
            }
            if (temp != 0 & i != 9) {
                sum += codeword.get(i);
            }
            // System.out.println("temp = " + temp + " at i = " + i);
        }

        if (sum % 2 == 0)
            r2 = 0;
        else
            r2 = 1;
        codeword.set(9, r2);
        // System.out.println(codeword);

        sum = 0;
        j = 0;
        // System.out.println(codeword);
        for (i = word_size + 3; i >= 0; i--) {
            j++;
            if (check == false) {
                temp++;
            }
            if (temp == 5) {
                temp = 0;
                check = true;
            }
            if (check == true) {
                a++;
            }
            if (a == 4) {
                a = 0;
                check = false;
            }
            if (temp != 0 & i != 4) {
                // System.out.println(codeword.get(j));
                sum += codeword.get(j);
            }
            // System.out.println("temp = " + temp + " at i = " + i + " j " + j);
        }

        if (sum % 2 == 0)
            r4 = 0;
        else
            r4 = 1;
        codeword.set(7, r4);
        // System.out.println(codeword);

        sum = 0;
        j = -1;
        for (i = word_size + 3; i >= 0; i--) {
            j++;
            if (check == false) {
                temp++;
            }
            if (temp == 9) {
                temp = 0;
                check = true;
            }
            if (check == true) {
                a++;
            }
            if (a == 8) {
                a = 0;
                check = false;
            }
            if (temp != 0 & i >= 8) {
                // System.out.println(codeword.get(j));
                sum += codeword.get(j);
            }
            // System.out.println("temp = " + temp + " at i = " + i + " j " + j);
        }
        if (sum % 2 == 0)
            r8 = 0;
        else
            r8 = 1;
        codeword.set(3, r8);
        // System.out.println(codeword);
        for (i = 0; i < word_size + 4; i++) {
            int numstr = codeword.get(i);
            String str = String.valueOf(numstr);
            final_codeword += str;
        }

    }

    public static void Hamming_gen_output() {
        System.out.println("==========================================================");
        System.out.println("Test case " + testcase);
        System.out.println("Dataword: " + dataword);
        System.out.println("A code word based on Hamming code: " + final_codeword);
        System.out.println("==========================================================");
        testcase++;
    }

    public static void Hamming_check(String codeword_) {
        int i, sum = 0, j, temp = 0, a = 0;
        boolean check = false;
        codeword.clear();
        String[] datawordSprit = codeword_.split("");
        ArrayList<String> dataStrList = new ArrayList<String>(Arrays.asList(datawordSprit));
        for (i = 0; i < word_size; i++) {
            // System.out.println(dataStrList.get(i));
            int currentBitString = Integer.parseInt(dataStrList.get(i));
            codeword.add(currentBitString);
        }

        for (i = 0; i < 4; i++) {
            codeword.add(random_bits());
        }

        int r1, r2, r4, r8;
        sum = 0;
        // R1
        for (i = 0; i < word_size + 4; i++) {
            if (i % 2 == 0)
                sum += codeword.get(i);
        }
        if (sum % 2 == 0)
            r1 = 0;
        else
            r1 = 1;
        // R2
        for (i = 0; i < word_size + 4; i++) {
            if (check == false) {
                temp++;
            }
            if (temp == 3) {
                temp = 0;
                check = true;
            }
            if (check == true) {
                a++;
            }
            if (a == 2) {
                a = 0;
                check = false;
            }
            if (temp != 0) {
                sum += codeword.get(i);
            }
            // System.out.println("temp = " + temp + " at i = " + i);
        }

        if (sum % 2 == 0)
            r2 = 0;
        else
            r2 = 1;
        // R4
        // System.out.println(codeword);

        sum = 0;
        j = 0;
        // System.out.println(codeword);
        for (i = word_size + 3; i >= 0; i--) {
            j++;
            if (check == false) {
                temp++;
            }
            if (temp == 5) {
                temp = 0;
                check = true;
            }
            if (check == true) {
                a++;
            }
            if (a == 4) {
                a = 0;
                check = false;
            }
            if (temp != 0) {
                // System.out.println(codeword.get(j));
                sum += codeword.get(j);
            }
            // System.out.println("temp = " + temp + " at i = " + i + " j " + j);
        }

        if (sum % 2 == 0)
            r4 = 0;
        else
            r4 = 1;

        // System.out.println(codeword);
        // R8
        sum = 0;
        j = -1;
        for (i = word_size + 3; i >= 0; i--) {
            j++;
            if (check == false) {
                temp++;
            }
            if (temp == 9) {
                temp = 0;
                check = true;
            }
            if (check == true) {
                a++;
            }
            if (a == 8) {
                a = 0;
                check = false;
            }
            if (temp != 0) {
                // System.out.println(codeword.get(j));
                sum += codeword.get(j);
                // System.out.println(sum);
            }
            // System.out.println("temp = " + temp + " at i = " + i + " j " + j);
        }
        if (sum % 2 == 0)
            r8 = 0;
        else
            r8 = 1;

        /*
         * System.out.println(codeword);
         * System.out.println(r1);
         * System.out.println(r2);
         * System.out.println(r4);
         * System.out.println(r8);
         */
        if (r1 == 0 & r2 == 0 & r4 == 0 & r8 == 0)
            bit_error = -1;
        else
            bit_error = 0;
        checkcodeword = "";
        error_postion = 0;
        if (bit_error == 0) {
            String bit = String.valueOf(r1);
            checkcodeword += bit;
            bit = String.valueOf(r8);
            checkcodeword += bit;
            bit = String.valueOf(r4);
            checkcodeword += bit;
            bit = String.valueOf(r2);
            checkcodeword += bit;
            error_postion = Integer.parseInt(checkcodeword, 2);
        }
    }

    public static void checker_output() {
        int i;
        final_codeword = "";
        for (i = 0; i < word_size + 4; i++) {
            int numstr = codeword.get(i);
            String str = String.valueOf(numstr);
            final_codeword += str;
        }

        System.out.println("==========================================================");
        System.out.println("Check case " + checkcase);
        System.out.println("Codeword is " + final_codeword);
        if (bit_error == -1)
            System.out.println("no error found");
        else
            System.out.println("Location of error: " + checkcodeword);
        System.out.println("The error position is " + error_postion);
        checkcase++;
        System.out.println("==========================================================");
    }

    public static void main(String[] args) {

        dataword = randdom_dataword();
        Hamming_gen(dataword);
        Hamming_gen_output();

        dataword = randdom_dataword();
        Hamming_gen(dataword);
        Hamming_gen_output();

        dataword = randdom_dataword();
        Hamming_gen(dataword);
        Hamming_gen_output();

        dataword = randdom_dataword();
        Hamming_gen(dataword);
        Hamming_gen_output();

        dataword = randdom_dataword();
        Hamming_gen(dataword);
        Hamming_gen_output();

        dataword = randdom_dataword();
        Hamming_gen(dataword);
        Hamming_gen_output();

        dataword = randdom_dataword();
        Hamming_gen(dataword);
        Hamming_gen_output();

        dataword = randdom_dataword();
        Hamming_gen(dataword);
        Hamming_gen_output();

        dataword = randdom_dataword();
        Hamming_gen(dataword);
        Hamming_gen_output();

        dataword = randdom_dataword();
        Hamming_gen(dataword);
        Hamming_gen_output();

        // CHECK

        codeword_ = randdom_dataword();
        Hamming_check(codeword_);
        checker_output();

        codeword_ = randdom_dataword();
        Hamming_check(codeword_);
        checker_output();

        codeword_ = randdom_dataword();
        Hamming_check(codeword_);
        checker_output();

        codeword_ = randdom_dataword();
        Hamming_check(codeword_);
        checker_output();

        codeword_ = randdom_dataword();
        Hamming_check(codeword_);
        checker_output();

        codeword_ = randdom_dataword();
        Hamming_check(codeword_);
        checker_output();

        codeword_ = randdom_dataword();
        Hamming_check(codeword_);
        checker_output();

        codeword_ = randdom_dataword();
        Hamming_check(codeword_);
        checker_output();

        codeword_ = randdom_dataword();
        Hamming_check(codeword_);
        checker_output();

        codeword_ = randdom_dataword();
        Hamming_check(codeword_);
        checker_output();

    }
}
