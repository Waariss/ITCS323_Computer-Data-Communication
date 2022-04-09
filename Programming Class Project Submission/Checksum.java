// Import the Class
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;  
import java.io.*;
// Declartion
class Checksum{
static int sBase = 2; // Base 2
static int dBase = 10; // Base 10
static int i = 1; 
static int check_decimal = 0; // Store decimal value in Check function
static int num_block = 0; // store num_block value
static int word_size = 0 ;// store word_size value 
static int sum_decimal = 0; // store decimal of dataword
static int flag = 0 ; // Store dif of sum of binary and word_size 
static String checksum; // store value after Complementing
static String sub; // store value of number out of word_size like 4 word_size ex. 11011 store 1 at first
static String remain; // store value that remain like 4 word_size ex. 11011 store 1011 at remain
static String sum_binary;// store sum binary in gen
static String sum_check; // store sum binary in check
static ArrayList<String> data_binary = new ArrayList<String>();// arraylist for final output for gen
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("********START Generator********");
		System.out.println("Enter Num block");
		num_block = sc.nextInt(); //scan input num_block
		System.out.println("Enter Word Size");
		word_size = sc.nextInt(); // scan input word_size
		do{ // loop for scan data word by not more than num_block
			System.out.println("Enter Dataword number "+ i +": ");
			int n = sc.nextInt();// scan input dataword
			data_binary.add(String.valueOf(n)); // add dataword after convert to string in data_binary arraylist
			sum_decimal += Integer.valueOf(convert(n,sBase,dBase)); // convert dataword input to base 10 and store in sum_decimal
			n = 0;
			i++;
		}while(i <= num_block);{ // when i more than num_block close scanner
			sc.close(); // close can
		}
		Checksum_gen(data_binary,word_size,num_block,sum_binary); // run Checksum_gen function
		Checksum_check(data_binary,word_size,num_block);// run Checksum_check function
	}
	static void Checksum_gen(ArrayList<String>  data_binary,int word_size,int num_block,String sum_binary){
		sum_binary = convert(sum_decimal,dBase,sBase); // convert sum_decimal back to binary 
		if(sum_binary.length() > word_size){ // if binary are more than word_size 
			flag = sum_binary.length() - word_size; // find dif of sum of binary and word_size 
			sub = sum_binary.substring(0,flag); // store flag number of binary to store like 4 word_size, flag is 1 ex. 11011 store 1 at first
			remain = sum_binary.substring(flag); // store value that remain like 4 word_size ex. 11011 store 1011 at remain
			sum_binary = null;
			sum_decimal = 0;
			sum_decimal += Integer.valueOf(convert(Integer.valueOf(sub),sBase,dBase)); // convert sub to decimal and store into sum_decimal
			sum_decimal += Integer.valueOf(convert(Integer.valueOf(remain),sBase,dBase));// convert remain to decimal and store into sum_decimal
			sum_binary = convert(sum_decimal,dBase,sBase); // convert back to binary
			// after convert back to binary but binary are not the same length like word_size will add 0 at first by find dif of sum of binary and word_size 
			if(sum_binary.length() != word_size){  
				int dif = word_size - sum_binary.length(); find dif of sum of binary and word_size 
				StringBuilder sb = new StringBuilder(sum_binary); // use StringBuilder to manage String 
				for( i =0  ; i< dif ;i++){
					sb.insert(0,'0'); // add 0 at first by index of dif 
				}
				sum_binary = sb.toString(); // convert to string and store 
			}
		}
		Complementing(sum_binary);//Complementing
		data_binary.add(checksum); // add sum into data_binary arraylist for final output for gen
		// convert arraylist oto string by StringBuilder
		StringBuilder db = new StringBuilder(); 
		for( String s : data_binary){
			db.append(s);
			db.append(" ");
		}
		System.out.println("Codeword: " + db.toString()); 
		System.out.println("********END Generator********");
	}
	public static String convert(int number, int sBase, int dBase){ //convert function
        		return Integer.toString(Integer.parseInt(String.valueOf(number), sBase), dBase);
    		}
    	public static String Complementing(String sum_binary){ // Function for change 0 to 1 and 1 to 0 
    		checksum = ""; 
		for(int i = 0; i < sum_binary.length() ; i++)
		{	
			char a = sum_binary.charAt(i);
			if(a == '1'){ //by if found 1 and 0 in checksum to store 
				checksum += '0';
			}
			else{ //by if found 0 and 1 in checksum to store 
				checksum += '1';
			}
		}
		return checksum;
    	}
	static boolean same(String s){ // function for checker if pattern are same number like 0000 or 1111 will return true
		int n = s.length();
		for(int i = 1; i < n;i++){
			if(s.charAt(i) != s.charAt(0)){
				return false;
			}
			else{
				return true;
			}
		}
		return true;
	}
	static void Checksum_check(ArrayList<String>  data_binary,int word_size,int num_block){
    	System.out.println("********START Checker********");
    	for(int i = 0; i < num_block+1 ; i++){
    		check_decimal += Integer.valueOf(convert(Integer.valueOf(data_binary.get(i)),sBase,dBase));
    	}
    	sum_check = convert(check_decimal,dBase,sBase);
    	if(sum_check.length() > word_size){ // if binary are more than word_size
			flag = sum_check.length() - word_size; // find dif of sum of binary and word_size 
			sub = sum_check.substring(0,flag); // store flag number of binary to store like 4 word_size, flag is 1 ex. 11011 store 1 at first
			remain = sum_check.substring(flag); // store value that remain like 4 word_size ex. 11011 store 1011 at remain
			sum_check = null;
			check_decimal = 0;
			check_decimal += Integer.valueOf(convert(Integer.valueOf(sub),sBase,dBase)); // convert sub to decimal and store into sum_decimal
			check_decimal += Integer.valueOf(convert(Integer.valueOf(remain),sBase,dBase)); // convert remain to decimal and store into sum_decimal
			sum_check = convert(check_decimal,dBase,sBase); // convert back to binary
			// after convert back to binary but binary are not the same length like word_size will add 0 at first by find dif of sum of binary and word_size
			if(sum_check.length() != word_size){
				int dif = word_size - sum_check.length();
				StringBuilder sb = new StringBuilder(sum_check); // use StringBuilder to manage String 
				for( i =0  ; i< dif ;i++){
					sb.insert(0,'0'); // add 0 at first by index of dif 
				}
				sum_check = sb.toString(); // convert to string and store 
			}
		}
    	Complementing(sum_check); //Complementing
		System.out.println("Codeword: " + checksum); 
		if(same(checksum)){ // if same function return true print pass 
			System.out.println("Validity of codeword: PASS");
		}
		else{ // if same function return flase print fail
			System.out.println("Validity of codeword: FAIL");
		}
		System.out.println("********END Checker********");
    }
}
