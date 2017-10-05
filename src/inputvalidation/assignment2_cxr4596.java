package inputvalidation;

import java.util.Scanner;

public class assignment2_cxr4596 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Scanner input = new Scanner(System.in);
			boolean done = false;

			do{
				System.out.print("Enter a word:\t");
				String test = input.nextLine();
				System.out.println("Echo:\t\t" + test + "\n");
				
				if(test.equalsIgnoreCase("done") || test.equalsIgnoreCase("exit"))
					done = true;
			}while(!done);
			
			input.close();
	}

}