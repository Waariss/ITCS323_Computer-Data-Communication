import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ParityGenerator {
    /*
     * What we input
     * 1. An array of bit string of any size up to word_size (datawords).
     * 2. Maximum size of each dataword (word_size) where word_size ≥ 5.
     * 3. Type of parity (one-dimensional-even, one-dimensional-odd,
     * two-dimensional-even,
     * two-dimensional-odd)
     * 4. Size of the array of the dataword (array_size)
     */

    // Declartion
    static int word_size;
    static int size;
    static int testcase = 1;
    static int CheckCase = 1;
    static boolean isOnedimensional = true;
    String[] parity_type_set = { "1e", "1o", "2e", "2o" };
    static String parity_type;
    static int array_size;
    static String dataword = "";
    static String codeword_ = "";
    int check_result_set[] = { 1, 2 }; // PASS = 1 or FAIL = 0
    static int check_result;
    static ArrayList<Integer> dt = new ArrayList<Integer>();
    static ArrayList<Integer> codeword = new ArrayList<Integer>();
    static ArrayList<Integer> firstsent = new ArrayList<Integer>();
    static ArrayList<ArrayList<Integer>> twoD_codeword = new ArrayList<ArrayList<Integer>>();
    static ArrayList<ArrayList<Integer>> firstsent_twoD_codeword = new ArrayList<ArrayList<Integer>>();
    static ArrayList<ArrayList<Integer>> DirectRead = new ArrayList<ArrayList<Integer>>();
    // static int twoD_codeword[][];
    // static int DirectRead[][];

    public static int random_ws() {
        int min = 5; // where word_size ≥ 5
        int max = 16;
        int ran = 0;
        ran = (int) Math.floor(Math.random() * (max - min + 1) + min);

        return ran;
    }

    public static int random_ar() {
        int min = 3;
        int max = 10;
        int ran = 0;
        ran = (int) Math.floor(Math.random() * (max - min + 1) + min);

        return ran;
    }

    public static int random_bits() {
        int bit;
        Random rand = new Random();
        bit = rand.nextInt(2); // random 1 and 0
        return bit;
    }

    public static void setvalue_forOneDimension(String pt) {
        word_size = random_ws(); // set word size
        for (int i = 0; i < word_size; i++) {
            int bit = random_bits();
            dataword += bit;
        }
        parity_type = pt; // set parity type
        size = word_size;
    }

    public static void setvalue_forTwoDimension(String pt) {
        word_size = random_ws(); // set word size
        parity_type = pt; // set parity type
        array_size = random_ar(); // set array size
        int count = 0;
        for (int i = 0; i < array_size; i++) {
            for (int j = 0; j < word_size; j++) {
                int bit = random_bits();
                dataword += bit;
            }
            count = count + word_size;
        }
        // System.out.println(count);
        size = count;
        // System.out.println(word_size);
    }

    public static void parity_gen(String dataword, int word_size, String parity_type, int array_size) {
        int i, sum = 0, sum2d = 0, rol, col;
        // change String of dataword to int in ArrayList
        String[] datawordSprit = dataword.split("");
        ArrayList<String> dataStrList = new ArrayList<String>(Arrays.asList(datawordSprit));
        for (i = 0; i < size; i++) {
            // System.out.println(dataStrList.get(i));
            int currentBitString = Integer.parseInt(dataStrList.get(i));
            codeword.add(currentBitString);
            firstsent.add(currentBitString);
        }
        // System.out.println(dataword); //check
        // System.out.println(codeword); //check

        // one-dimensional read ******************************************************
        for (i = 0; i < codeword.size(); i++) {
            sum += (codeword.get(i));
        }

        // two-dimenstional read *****************************************************
        for (i = 0; i <= array_size; i++) {
            DirectRead.add(new ArrayList<>());
            twoD_codeword.add(new ArrayList<>());
            if (i != 0)
                firstsent_twoD_codeword.add(new ArrayList<>());
        }
        // System.out.println(array_size);
        // System.out.println(temp);
        int x = 0;
        int temp = word_size;
        int dummy = temp;
        for (rol = array_size - 1; rol >= 0; rol--) {
            for (col = x; col < temp; col++) {
                DirectRead.get(rol).add(codeword.get(col));
            }
            x = x + dummy;
            temp = temp + dummy;
        }
        for (rol = array_size - 1; rol >= 0; rol--) { // store codeword in two dimensional
            for (col = dummy - 1; col >= 0; col--) {
                // DirectRead.get(rol).add(codeword.get(col));
                firstsent_twoD_codeword.get(rol).add(DirectRead.get(rol).get(col));
                twoD_codeword.get(rol).add(DirectRead.get(rol).get(col));
            }
        }
        // System.out.println(codeword);
        // System.out.println(DirectRead);
        // System.out.println(twoD_codeword);
        // Parity add
        // ********************************************************************
        if (parity_type == "1e" || parity_type == "1o") {
            if (sum % 2 == 0) {
                if (parity_type == "1e")
                    codeword.add(0); // even check and it is even --> add 0
                if (parity_type == "1o")
                    codeword.add(1); // odd check and it is even --> add 1
            } else {
                if (parity_type == "1e")
                    codeword.add(1); // even check and it is odd --> add 1
                if (parity_type == "1o")
                    codeword.add(0); // odd check and it is odd --> add 0
            }
        } else {
            for (rol = 0; rol < array_size; rol++) {
                for (col = 0; col < word_size; col++) {
                    // System.out.println(twoD_codeword.get(rol).get(col));
                    Integer currentbit = twoD_codeword.get(rol).get(col);
                    sum2d += currentbit;
                }
                if (sum2d % 2 == 0) {
                    if (parity_type == "2e")
                        twoD_codeword.get(rol).add(0); // even check and it is even --> add 0
                    if (parity_type == "2o")
                        twoD_codeword.get(rol).add(1); // odd check and it is even --> and 1
                } else {
                    if (parity_type == "2e")
                        twoD_codeword.get(rol).add(1); // even check and it is odd --> add 1
                    if (parity_type == "2o")
                        twoD_codeword.get(rol).add(0); // odd check amd it is odd --> add 0
                }
                // System.out.println(sum2d);
                sum2d = 0;
            }
            for (col = 0; col < word_size + 1; col++) {
                for (rol = 0; rol < array_size; rol++) {
                    Integer currentbit = twoD_codeword.get(rol).get(col);
                    sum2d += currentbit;
                }
                if (sum2d % 2 == 0) {
                    if (parity_type == "2e")
                        twoD_codeword.get(array_size).add(0); // even check and it is even --> add 0
                    if (parity_type == "2o")
                        twoD_codeword.get(array_size).add(1); // odd check and it is even --> and 1
                } else {
                    if (parity_type == "2e")
                        twoD_codeword.get(array_size).add(1); // even check and it is odd --> add 1
                    if (parity_type == "2o")
                        twoD_codeword.get(array_size).add(0); // odd check amd it is odd --> add 0
                }
                // System.out.println(sum2d);
                sum2d = 0;
            }

            // System.out.println(DirectRead);
            // System.out.println(twoD_codeword);

        }
    }

    public static void generator_output() {
        // Result output *********************************************************
        int i;
        System.out.println("========================================================");
        System.out.println("Test case" + " " + testcase);
        String parity_type_output = "";
        if (parity_type == "1e") {
            parity_type_output = "one-dimensional-even";
            isOnedimensional = true;
        } else if (parity_type == "1o") {
            isOnedimensional = true;
            parity_type_output = "one-dimensional-odd";
        } else if (parity_type == "2e") {
            parity_type_output = "two-dimensional-even";
            isOnedimensional = false;
        } else if (parity_type == "2o") {
            parity_type_output = "two-dimensional-odd";
            isOnedimensional = false;
        } else {
            parity_type_output = "unknow parity type";
            isOnedimensional = false;
        }
        System.out.println("Parity type is " + parity_type_output);
        if (isOnedimensional == true)
            System.out.println("The dataword sent" + " " + dataword);
        else {
            String dataword2Doutput = "";
            int flag = word_size;
            for (i = 0; i < size; i++) {
                dataword2Doutput += dataword.charAt(i);
                if (i == flag) {
                    dataword2Doutput += " ";
                    flag = flag + word_size;
                }
            }
            System.out.println("The dataword sent" + " " + dataword2Doutput);
        }
        if (isOnedimensional == true) {
            System.out.println("Actual array of dataword is " + firstsent);
            System.out.println("Received " + codeword);
        } else {
            for (i = 0; i < array_size; i++)
                System.out.println("Actual array of dataword is " + firstsent_twoD_codeword.get(i));
            for (i = 0; i <= array_size; i++) {
                System.out.println("Received " + twoD_codeword.get(i));
            }

        }
        System.out.println("========================================================");
        testcase++;

    }

    public static void setCompleteRandomkValue_forOneDimension(String pt) {
        word_size = random_ws(); // set word size
        word_size = word_size + 1;
        for (int i = 0; i < word_size; i++) {
            int bit = random_bits();
            codeword_ += bit;
        }
        parity_type = pt; // set parity type
        size = word_size;
    }

    public static void setCompleteRandomkValue_forTwoDimension(String pt) {
        word_size = random_ws(); // set word size
        word_size = word_size + 1;
        parity_type = pt; // set parity type
        array_size = random_ar(); // set array size
        array_size = array_size + 1;
        int count = 0;
        for (int i = 0; i < array_size; i++) {
            for (int j = 0; j < word_size; j++) {
                int bit = random_bits();
                codeword_ += bit;
            }
            count = count + word_size;
        }

        size = count;

    }

    public static void parity_check(String codeword_, String parity_type, int size) {
        /*
         * Input:
         * 1. An array of codewords to check
         * 2. Type of parity (one-dimensional-even, one-dimensional-odd,
         * two-dimensional-even,two-dimensional-odd)
         * 3. Size of the array of the dataword (array_size)
         * 
         * Output:
         * 1. Validity of the array of codewords (PASS=1 or FAIL=0)
         */

        int i, sum = 0, rol, col;
        boolean flag = true;

        String[] datawordSprit = codeword_.split("");
        ArrayList<String> dataStrList = new ArrayList<String>(Arrays.asList(datawordSprit));
        for (i = 0; i < size; i++) {
            // System.out.println(dataStrList.get(i));
            int currentBitString = Integer.parseInt(dataStrList.get(i));
            codeword.add(currentBitString);
            firstsent.add(currentBitString);
        }
        // int check_result_set [] = {1,2}; //PASS = 1 or FAIL = 0

        // two-dimenstional read *****************************************************
        for (i = 0; i <= array_size; i++) {
            DirectRead.add(new ArrayList<>());
            twoD_codeword.add(new ArrayList<>());
            if (i != 0)
                firstsent_twoD_codeword.add(new ArrayList<>());
        }
        // System.out.println(array_size);
        // System.out.println(temp);
        int x = 0;
        int temp = word_size;
        int dummy = temp;
        for (rol = array_size - 1; rol >= 0; rol--) {
            for (col = x; col < temp; col++) {
                DirectRead.get(rol).add(codeword.get(col));
            }
            x = x + dummy;
            temp = temp + dummy;
        }
        for (rol = array_size - 1; rol >= 0; rol--) { // store codeword in two dimensional
            for (col = dummy - 1; col >= 0; col--) {
                // DirectRead.get(rol).add(codeword.get(col));
                firstsent_twoD_codeword.get(rol).add(DirectRead.get(rol).get(col));
                twoD_codeword.get(rol).add(DirectRead.get(rol).get(col));
            }
        }

        if (parity_type == "1e") {
            for (i = 0; i < size; i++) {
                sum += codeword.get(i);
            }
            if (sum % 2 == 0)
                check_result = 1;
            else
                check_result = 0;
        } else if (parity_type == "1o") {
            for (i = 0; i < size; i++) {
                sum += codeword.get(i);
            }
            if (sum % 2 == 0)
                check_result = 0;
            else
                check_result = 1;
        } else if (parity_type == "2e") {
            for (rol = 0; rol < array_size; rol++) {
                for (col = 0; col < word_size; col++) {
                    sum += twoD_codeword.get(rol).get(col);
                }
                if (sum % 2 == 0)
                    flag = true;
                else {
                    flag = false;
                    break;
                }
                sum = 0;
            }
            if (flag == false)
                check_result = 0;
            else
                check_result = 1;
        } else {
            for (rol = 0; rol < array_size; rol++) {
                for (col = 0; col < word_size; col++) {
                    sum += twoD_codeword.get(rol).get(col);
                }
                if (sum % 2 != 0)
                    flag = true;
                else {
                    flag = false;
                    break;
                }
                sum = 0;
            }
            if (flag == false)
                check_result = 0;
            else
                check_result = 1;
        }

    }

    public static void checker_output() {
        int i;
        System.out.println("========================================================");
        System.out.println("Test case" + " " + CheckCase);
        String parity_type_output = "";
        if (parity_type == "1e") {
            parity_type_output = "one-dimensional-even";
            isOnedimensional = true;
        } else if (parity_type == "1o") {
            isOnedimensional = true;
            parity_type_output = "one-dimensional-odd";
        } else if (parity_type == "2e") {
            parity_type_output = "two-dimensional-even";
            isOnedimensional = false;
        } else if (parity_type == "2o") {
            parity_type_output = "two-dimensional-odd";
            isOnedimensional = false;
        } else {
            parity_type_output = "unknow parity type";
            isOnedimensional = false;
        }
        System.out.println("Parity type is " + parity_type_output);
        if (isOnedimensional == true)
            System.out.println("The dataword sent" + " " + codeword_);
        else {
            String codeword2Doutput = "";
            int flag = word_size - 1;
            for (i = 0; i < size; i++) {
                codeword2Doutput += codeword_.charAt(i);
                if (i == flag) {
                    codeword2Doutput += " ";
                    flag = flag + word_size;
                }
            }
            System.out.println("The dataword sent" + " " + codeword2Doutput);
        }
        if (check_result == 1)
            System.out.println("PASS");
        else
            System.out.println("FAIL");
        System.out.println("========================================================");
        CheckCase++;
    }

    public static void oneDConverter_toString() { // use parity_gen to check in parity_check
        int i;
        for (i = 0; i < word_size + 1; i++) {
            codeword_ += codeword.get(i);
        }
    }

    public static void twoDConverter_toString() { // use parity_gen to check in parity_check
        int rol, col;
        for (rol = 0; rol < array_size; rol++) {
            for (col = 0; col < word_size + 1; col++) {
                codeword_ += twoD_codeword.get(rol).get(col);
            }

        }
    }

    public static void main(String[] args) {
        // String[] parity_type_set = { "1e", "1o", "2e", "2o" };
        // Generator
        System.out.println("Test case for parity generator");
        setvalue_forOneDimension("1e");
        parity_gen(dataword, word_size, parity_type, array_size);
        generator_output();

        setvalue_forOneDimension("1e");
        parity_gen(dataword, word_size, parity_type, array_size);
        generator_output();

        setvalue_forOneDimension("1o");
        parity_gen(dataword, word_size, parity_type, array_size);
        generator_output();

        setvalue_forOneDimension("1o");
        parity_gen(dataword, word_size, parity_type, array_size);
        generator_output();

        setvalue_forTwoDimension("2e");
        parity_gen(dataword, word_size, parity_type, array_size);
        generator_output();

        setvalue_forTwoDimension("2e");
        parity_gen(dataword, word_size, parity_type, array_size);
        generator_output();

        setvalue_forTwoDimension("2e");
        parity_gen(dataword, word_size, parity_type, array_size);
        generator_output();

        setvalue_forTwoDimension("2o");
        parity_gen(dataword, word_size, parity_type, array_size);
        generator_output();

        setvalue_forTwoDimension("2o");
        parity_gen(dataword, word_size, parity_type, array_size);
        generator_output();

        setvalue_forTwoDimension("2o");
        parity_gen(dataword, word_size, parity_type, array_size);
        generator_output();

        System.out.println("Test case for Parity checker");

        setCompleteRandomkValue_forOneDimension("1e");
        parity_check(codeword_, parity_type, array_size);
        checker_output();

        setvalue_forOneDimension("1e");
        oneDConverter_toString();
        parity_check(codeword_, parity_type, array_size);
        checker_output();

        setCompleteRandomkValue_forOneDimension("1o");
        parity_check(codeword_, parity_type, array_size);
        checker_output();

        setvalue_forOneDimension("1o");
        oneDConverter_toString();
        parity_check(codeword_, parity_type, array_size);
        checker_output();

        setCompleteRandomkValue_forTwoDimension("2e");
        parity_check(codeword_, parity_type, array_size);
        checker_output();

        setvalue_forTwoDimension("2e");
        twoDConverter_toString();
        parity_check(codeword_, parity_type, array_size);
        checker_output();

        setvalue_forTwoDimension("2e");
        twoDConverter_toString();
        parity_check(codeword_, parity_type, array_size);
        checker_output();

        setCompleteRandomkValue_forTwoDimension("2e");
        parity_check(codeword_, parity_type, array_size);
        checker_output();

        setvalue_forTwoDimension("2e");
        twoDConverter_toString();
        parity_check(codeword_, parity_type, array_size);
        checker_output();

        setvalue_forTwoDimension("2e");
        twoDConverter_toString();
        parity_check(codeword_, parity_type, array_size);
        checker_output();

    }
}
