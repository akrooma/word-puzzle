import java.util.ArrayList;

public class Puzzle {
	
	private static String cannotBeZero;

   /** Solve the word puzzle.
    * @param args three words (addend1, addend2 and sum)
    */
   public static void main (String[] args) {
	   String[] test = {"A", "B", "C"};
//	   String s = "CBCHDSF";
//	   validate(test);

//	   System.out.println(getUniqueLetters (test));
//	   setExceptions(test);
//	   System.out.println(cannotBeZero);
	   
//	   System.out.println(!cannotBeZero.contains(String.valueOf(s.charAt(0))));
	   
//	   ArrayList<String> permutations = getPermutations (getUniqueLetters (test));
	   String s = "0123456ABC";
	   String a = "ABC";
	   
	   for (int i = 0; i < test.length; i++) {

	   }
	   
	   char[] array = a.toCharArray();
	   
	   for (int i = 0; i < array.length; i++) {
		   array[i] = (char) (s.indexOf(array[i]) + '0');
	   }
	   a = String.valueOf(array);
	   
	   System.out.println(a);
   }
   
   public static void solvePuzzle (String[] input) {
	   validate (input);
	   setExceptions (input);
	   
	   String[] replacements = {};
	   ArrayList<String> results = new ArrayList<String>();
	   
	   String uniqueLetters = getUniqueLetters (input);
	   String uniqueLettersWithFiller = getUniquesWithFiller(uniqueLetters);
	   ArrayList<String> permutations = getPermutations (uniqueLettersWithFiller);
	   
	   for (String s : permutations) {
		   for (int i = 0; i < input.length; i++) {
			   for (int j = 0; j < input[i].length(); j++) {
//				    = input[i].replace(input[i].charAt(j), (char) s.indexOf(input[i].charAt(j)));
			   }
		   }		   
	   }
   }

   /**
    * Validates the puzzle's input arguments.
    */
   private static void validate (String[] input) {
	   // checks if the correct amount of arguments were given.
	   if (input.length != 3) {
		   throw new RuntimeException ("Input '" + argsToString (input) + "' doesn't containt the correct amount [3] of words.");
	   }
	   
	   // checks if the strings are only upper-case letters.
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
    * Puts the letters that cannot have the value of 0 in a global string.
    * These letters are the first ones of the puzzle arguments.
    * @param input Array containing strings.
    */
   private static void setExceptions(String[] input) {
	   StringBuffer sb = new StringBuffer();
	   
	   for (String s : input) {
		   sb.append(s.charAt(0));
	   }
	   
	   cannotBeZero = sb.toString();
   }
   
   /**
    * @param input Puzzle argument array.
    * @return String containing unique letters and filler.
    */
   private static String getUniqueLetters (String[] input) {
	   StringBuffer sb = new StringBuffer();
	   
	   // adds all the unique letters into the string-buffer.
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
	   
	   return sb.toString();
   }
   
   /**
    * Adds filler elements to the unique letters so permutations can be made.
    * @param input containing unique letters.
    * @return String containing unique letters and filler elements.
    */
   private static String getUniquesWithFiller(String input) {
	   int counter = 0;
	   StringBuffer sb = new StringBuffer();
	   
	   sb.append(input);

	   while (sb.length() < 10) {
		   sb.append(counter);
		   counter++;
	   }
	   
	   return sb.toString();
   }
   
   /**
    * @param input String containing unique letters and filler.
    * @return Array-list containing permutations of the string. A few permutations are omitted.
    */
   // http://introcs.cs.princeton.edu/java/23recursion/Permutations.java.html
   private static ArrayList<String> getPermutations (String input) {
	   ArrayList<String> permutations = new ArrayList<String>();
	   fillPermutationArray ("", input, permutations);
	   
	   System.out.println(permutations.size());
	   return permutations;
   }
   
   private static void fillPermutationArray (String prefix, String input, ArrayList<String> permutations) {
	   int l = input.length();
	   
	   if (l == 0) {
		   // checks if the permutation doesn't start with the first letter of a puzzle argument.
		   // if it doesn't, add it to the list.
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
    * @param input an array containing strings. 
    * @return A string containing the array's strings' values separated separated by a whitespace.
    */
   private static String argsToString(String[] input) {
	   StringBuffer sb = new StringBuffer();
	   
	   for (String s : input) {
		   sb.append(s + " ");
	   }
	   
	   return sb.toString().trim();
   }
}

