import java.util.ArrayList;

public class Puzzle {
	
	private static String cannotBeZero;

   /** Solve the word puzzle.
    * @param args three words (addend1, addend2 and sum)
    */
   public static void main (String[] args) {
	   String[] test = {"A", "B", "C"};
	   
	   validate(test);
	   
//	   System.out.println(getUniqueLetters (test));
	   setExceptions(test);
//	   System.out.println(cannotBeZero);
	   
	   System.out.println(!cannotBeZero.contains(String.valueOf("1")));
	   
//	   ArrayList<String> permutations = getPermutations (getUniqueLetters (test));
	   
//	   System.out.println(permutations.size());
   }
   

   
   public static ArrayList<String> getPermutations (String input) {
	   ArrayList<String> permutations = new ArrayList<String>();
	   fillPermutationArray ("", input, permutations);
	   
	   return permutations;
   }
   
   private static void fillPermutationArray (String prefix, String input, ArrayList<String> permutations) {
	   int l = input.length();
	   
	   if (l == 0) {
		   if (!cannotBeZero.contains(String.valueOf(prefix.charAt(0)))) {
			   permutations.add(prefix);
		   }
	   } else {
		   for (int i = 0; i < l; i++) {
			   fillPermutationArray (prefix + input.charAt(i), input.substring(0, i) + input.substring(i+1, l), permutations);
		   }
	   }
   }
   
   /**
    * Puts the letters that cannot have the value of 0 in a global string.
    * These letters are the first ones of the puzzle arguments.
    * @param Array containing strings.
    */
   public static void setExceptions(String[] input) {
	   StringBuffer sb = new StringBuffer();
	   
	   for (String s : input) {
		   sb.append(s.charAt(0));
	   }
	   
	   cannotBeZero = sb.toString();
   }
   
   /**
    * @param Puzzle argument array.
    * @return String containing unique letters and filler.
    */
   public static String getUniqueLetters (String[] input) {
	   StringBuffer sb = new StringBuffer();
	   int counter = 0;
	   
	   // adds all the unique letters into the stringbuffer.
	   for (String s : input) {
		   for (int i = 0; i < s.length(); i++) {
			   if (sb.indexOf(String.valueOf(s.charAt(i))) < 0) {
				   sb.append(s.charAt(i));
			   }
		   }
	   }
	   
	   // can't have more than 10 unique letters.
	   if (sb.length() > 10) {
		   throw new RuntimeException ("Too many unique letters across all arguments: " + argsToString(input));
	   }
	   
	   // if the unique letter count isn't 10, non-letters will be added.
	   while (sb.length() < 10) {
		   sb.append(counter);
		   counter++;
	   }
	   
	   return sb.toString();
   }

   /**
    * Validates the puzzle's input arguments.
    */
   public static void validate (String[] input) {
	   // checks if the correct amount of arguments were given.
	   if (input.length != 3) {
		   throw new RuntimeException ("Input '" + argsToString (input) + "' doesn't containt the correct amount [3] of words.");
	   }
	   
	   // checks if the strings are only uppercase letters.
	   // makes sure the arguments are no longer than 18 letters.
	   for (String s : input) {
		   if (!s.matches("[A-Z]+")) {
			   throw new RuntimeException ("Arguments must contain only uppercase letters: " + argsToString (input));
		   }
		   
		   if (s.length() > 18) {
			   throw new RuntimeException ("Arguments cannot be longer than 18 letters: " + argsToString (input));
		   }
	   }
	   
	   // sum has to be longer or equal than addend1 and addend2.
	   if ((input[2].length() < input[0].length()) || (input[2].length() < input[1].length())) {
		   throw new RuntimeException ("Sum has to be longer or equal in length to the addenda: " + argsToString (input));
	   }
   }
   
   /**
    * @param input -- An array containing strings. 
    * @return A string containing the array's strings' values separated separated by a whitespace.
    */
   public static String argsToString(String[] input) {
	   StringBuffer sb = new StringBuffer();
	   
	   for (String s : input) {
		   sb.append(s + " ");
	   }
	   
	   return sb.toString().trim();
   }
}

