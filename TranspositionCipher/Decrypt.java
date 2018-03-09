import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Decrypt {
	
	public static String Substituion(int i, char ch, String alphabet)
    {
        char[] alphabetArray = alphabet.toCharArray();  /* Substitution of Alphabet */
        alphabetArray[i] = ch;
        return new String(alphabetArray);
    }
    
    public static String Transposition(String blank, String[] column, String[] row)
    {
        String finalword = "";                        /* Reverse Transposition of Coloumn Order */
        int[] pat = {0, 1, 2, 3, 4, 5, 6, 7}; 
        for (int i = 0; i<8; i++)
        {
            column[i]= blank + row[pat[5]].charAt(i) + row[pat[4]].charAt(i) + row[pat[1]].charAt(i)+ row[0].charAt(i)+ row[pat[3]].charAt(i)+ row[pat[6]].charAt(i)+ row[pat[7]].charAt(i)+ row[2].charAt(i);
            finalword += column[i];
        }
       return finalword;
    }

	public static void main(String[] args) throws FileNotFoundException {
		String key = args[0];
        int hash = key.hashCode();
        Random rnd = new Random(hash); 
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ", newAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ " ;
        
       for (int i = 0; i < 100 ; i++) 
       {
         int num1 = rnd.nextInt(27) , num2 = rnd.nextInt(27);
         char letter1 = newAlpha.charAt(num1), letter2 = newAlpha.charAt(num2);
         newAlpha = Substituion(num1, letter2, newAlpha);
         newAlpha = Substituion(num2, letter1, newAlpha);  
       }
     
        int x=0, y=0; 
        String words = "";
        
        File inputFile = new File(args[1]);  /* Reading TextFile*/
        Scanner in = new Scanner(inputFile);
        while (in.hasNextLine())
        {
            String value = in.nextLine();   
            words += value;
        } 
        in.close();
    
         char matrix[][]=new char [8][words.length()];  /* Converting input string into Matrix*/
         char msg[] = words.toCharArray();
     
         for (int i=0; i< msg.length;i++)
         {
             matrix[x][y]=msg[i];
             if (x==(8-1))
             {
                x=0;
                y=y+1;
             } 
             else { x++;}
         } 
 
         String[] row = {"", "", "", "", "", "", "", ""};
     
         for (int j=0;j<y;j++)
         {
            for (int i=0;i<8;i++)
            {
               row[j] += matrix[i][j];
            }
         }
         String[] col = {"", "", "", "", "", "", "", ""};
         String blank = "";
         String word = Transposition(blank, col, row);
         char[] Array = word.toCharArray(); char letter = ' ', newLetter = ' ';
         int indexAlpha = 0;
          for (int i = 0; i < 64; i++)     /* Reverse Substitution*/
             {
                 letter = word.charAt(i);
                 indexAlpha = newAlpha.indexOf(letter);
                 newLetter = alpha.charAt(indexAlpha);
                 Array[i] = newLetter;
             }
     
         System.out.println("Text has been decrypted into output.txt");
         System.out.println("Output.txt: ");
         System.out.print(Array);
     
         PrintWriter out = new PrintWriter(args[2]);   /* Writing File*/
         for (int i=0; i<Array.length ; i++)
         {
             out.print(Array[i]);
         }
         out.close();
	}

	}


