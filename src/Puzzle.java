import java.util.ArrayList;
import java.util.HashSet;

public class Puzzle {
	
	// string holding letters, that cannot have a digit value of 0.
	private static String cannotBeZero;

   /** Solve the word puzzle.
    * @param args three words (addend1, addend2 and sum)
    */
   public static void main (String[] args) {
//	   String[] test = {"AAA", "BBB", "CCC"};
//	   String[] test = {"SEND", "MORE", "MONEY"};
//	   solvePuzzle (test);
	   solvePuzzle (args);
   }
   
   /**
    * Tries to solve the puzzle. If the puzzle is solvable, the first solution is printed on the
    * console.
    * @param input array containing addend1, addend2 and sum.
    * @throws RuntimeException if no solutions are found.
    */
   public static void solvePuzzle (String[] input) {
	   validate (input);
	   setExceptions (input);

	   ArrayList<String[]> solutions = new ArrayList<String[]>();
	   
	   String uniqueLetters = getUniqueLetters (input);
	   String uniqueWithFiller = setFiller (uniqueLetters);
	   HashSet<String> permutations = getPermutations (uniqueWithFiller);
	   
	   for (String permutation : permutations) {
		   tryDigits (permutation, input, solutions);
	   }
	   
	   if (solutions.size() == 0) {
		   throw new RuntimeException ("Cannot solve puzzle: " + argsToString (input));
	   } else {
		   System.out.println("Puzzle: " + argsToString (input));
		   System.out.println("Number of solutions: " + solutions.size());
		   System.out.println("First solution: " + argsToString (solutions.get (0)));
		   printMap (uniqueLetters, argsToString (input), argsToString (solutions.get (0)));
	   }
   }
   
   private static void printMap (String uniqueLetters, String puzzle, String digits) {
	   ArrayList<Integer> indexes = new ArrayList<Integer>();
	   
	   for (int i = 0; i < uniqueLetters.length(); i++) {
		   indexes.add(puzzle.indexOf(uniqueLetters.charAt(i)));
	   }
	   
	   for (int i = 0; i < indexes.size(); i++) {
		   System.out.println(uniqueLetters.charAt(i) + " -- " + digits.charAt(indexes.get(i)));
	   }
   }
   
   /**
    * Tries to solve the puzzle with given letter - digit map. If the map checks out,
    * digit version of the puzzle is added to the solution set of the puzzle.
    * @param permutation letter - digit map.
    * @param input array containing addend1, addendd2, sum of puzzle.
    * @param solutions reference to array containing solutions.
    */
   private static void tryDigits (String permutation, String[] input, ArrayList<String[]> solutions) {
	   String[] args = input.clone();
	   
	   // replaces the letters with the corresponding digits.
	   for (int i = 0; i < args.length; i++) {
		   char[] array = args[i].toCharArray();
		   
		   for (int j = 0; j < array.length; j++) {
			   array[j] = (char) (permutation.indexOf (array[j]) + '0');
		   }
		   
		   args[i] = String.valueOf (array);
	   }
	   
	   if ((Long.parseLong (args[0]) + Long.parseLong (args[1])) == Long.parseLong (args[2])) {
			   solutions.add (args);
	   } else {
		   return;
	   }
   }

   /**
    * Validates the puzzle's input arguments.
    */
   private static void validate (String[] input) {
	   // checks if the correct amount of arguments were given.
	   if (input.length != 3) {
		   throw new RuntimeException ("Puzzle uses only 3 words: " + argsToString (input));
	   }
	   
	   // checks if the strings are only upper-case letters.
	   // makes sure the arguments are no longer than 18 letters.
	   for (String s : input) {
		   if (!s.matches ("[A-Z]+")) {
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
    * Puts the letters that cannot have the value of 0 in a global variable.
    * These letters are the first ones of the puzzle arguments.
    * @param input Array containing strings.
    */
   private static void setExceptions(String[] input) {
	   StringBuffer sb = new StringBuffer();
	   
	   for (String s : input) {
		   sb.append (s.charAt (0));
	   }
	   
	   cannotBeZero = sb.toString();
   }
   
   /**
    * Puts all the puzzle's unique letters in a string. If the amount of letters isn't 10, 
    * filler elements are added so permutations can be made.
    * @param input Puzzle argument array.
    * @return String containing unique letters with possible filler elements.
    */
   private static String getUniqueLetters (String[] input) {
	   StringBuffer sb = new StringBuffer();
	   
	   // adds all the unique letters into the string-buffer.
	   for (String s : input) {
		   for (int i = 0; i < s.length(); i++) {
			   if (sb.indexOf (String.valueOf (s.charAt (i))) < 0) {
				   sb.append (s.charAt (i));
			   }
		   }
	   }
	   
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
   private static String setFiller(String input) {
	   StringBuffer sb = new StringBuffer();
	   
	   sb.append(input);

	   while (sb.length() < 10) {
		   sb.append("-");
	   }
	   
	   return sb.toString();
   }
   
   /**
    * Gets the permutations of the input string.
    * @param input String containing unique letters and filler elements.
    * @return Array-list containing permutations of the string.
    */
   // http://introcs.cs.princeton.edu/java/23recursion/Permutations.java.html
   private static HashSet<String> getPermutations (String input) {
	   HashSet<String> permutations = new HashSet<String>();
	   fillPermutationArray ("", input, permutations);

	   return permutations;
   }
   
   /**
    * Adds permutations to an array-list. A few permutations are omitted.
    * @param permutations reference to array-list containing permutations.
    */
   private static void fillPermutationArray (String prefix, String input, HashSet<String> permutations) {
	   int l = input.length();
	   
	   if (l == 0) {
		   // checks if the permutation doesn't start with the first letter of a puzzle argument.
		   // if it doesn't, add it to the list.
		   if (!cannotBeZero.contains (String.valueOf (prefix.charAt (0)))) {
				   permutations.add (prefix);
		   }
	   } else {
		   for (int i = 0; i < l; i++) {
			   fillPermutationArray (prefix + input.charAt (i), input.substring (0, i) + input.substring (i+1, l), permutations);
		   }
	   }
   }
   
   /**
    * @param input an array containing strings. 
    * @return A string containing the array's strings' values separated separated by a whitespace.
    */
   private static String argsToString (String[] input) {
	   StringBuffer sb = new StringBuffer();
	   
	   sb.append (input[0] + " + " + input[1] + " = " + input[2]);
	   
	   return sb.toString();
   }
}