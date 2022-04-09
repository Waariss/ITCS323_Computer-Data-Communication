#include<stdio.h>
#include <string.h>
//#include<conio.h>
int main(){
	//declare 
	int Ctype = 0 ; // Ctype is generator size
	int word_size = 0; // word_size is dataword size
	int len = 0; // Store length of dataword 
	char dataword[100],gen[50],CRC[50]; // dataword and CRC is stored in Character array
	int i,k;
	strcpy(CRC,dataword);
	printf("Enter CRC-type\n");// scan CRC type 
	scanf("%d",&Ctype);
	printf("Enter Word size\n");// Scan word_size
	scanf("%d",&word_size);
	
	// Scan dataword and generator
	printf("Enter Dataword\n");
	scanf("%s",&dataword);
	printf("Enter Generator\n");
	scanf("%s",&gen);
	
	//add additional zeros to the dataword 
  	for (i=0;i < Ctype - 1 ; i++) 
  	//loop executes from 0 to CRC type - 2 and  add these number of 0's at the end of dataword
   	{
     		dataword [Ctype+i] = '0' ;
        }                 
   	dataword[Ctype+word_size-1] = '\0';
   	
   	// find remain when divide dataword by generator
	do{
		for(i  = 0; i < Ctype-1 ; i++){ // loop to divide
			if(dataword[i] == gen[i]){
				dataword[i] = '0';
			}
			else{
				dataword[i] = '1';
			}
		}
		// if all elements are zero then breaking loop
		k = 0;
		for(i = 0; i < Ctype+word_size-1; i++){
			if(dataword[i] == '0'){
				k++;
			}
		}
		if(k==(Ctype+word_size-1)){ // all values are 0 will break while loop
			break;
		} 

		while(dataword[0] != '1'){ // left swap the all element untill dataword[0] != '1'
			for(i = 0; i < Ctype+word_size-1 ; i++){ // make word_size = Ctype+word_size-1
				dataword[i] = dataword[i+1]; //left swap
			}
			word_size--; 
		}
		len = Ctype+word_size-1; //  length of dataword 
	}while(len>Ctype-1);
	dataword[len]='\0';
	printf(" Remainder is : %s",dataword);
	return 0;
}
