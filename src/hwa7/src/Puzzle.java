package hwa7.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Puzzle {

   private static int solutionCount = 0;

   public static void main(String[] args) throws IOException {

      Scanner sc = new Scanner(System.in);
      String w1 = sc.next();
      String w2 = sc.next();
      String w3 = sc.next();
      if (w1.length() > 18 || w2.length() > 18 || w3.length() > 18) {
         System.out.println("Word length cannot exceed 18 characters.");
         return;
      }
      solve(new String[]{w1, w2, w3});
   }

   public static void solve(String[] words) {
      if (words.length != 3) {
         System.out.println("Invalid puzzle. It must contain 3 words.");
         return;
      }

      String w1 = words[0];
      String w2 = words[1];
      String w3 = words[2];

      usedLetter = new boolean[26];
      usedDigit = new boolean[10];
      assignedDigit = new int[26];
      markLetters(w1);
      markLetters(w2);
      markLetters(w3);
      solutionCount = 0;
      backtrack(0, w1, w2, w3);
      if (solutionCount > 0) {
         System.out.println("Total number of solutions: " + solutionCount);
         printSolution(w1, w2, w3);
      } else {
         System.out.println("No solution");
      }
   }

   static void printSolution(String word1, String word2, String word3) {
      System.out.println("One of the solutions:");
      System.out.println(word1 + " = " + value(word1));
      System.out.println(word2 + " = " + value(word2));
      System.out.println(word3 + " = " + value(word3));
      System.out.println();
      System.out.println("Letters values:");
      printValues();
      System.out.println();
   }

   static void printValues() {
      List<Character> letters = new ArrayList<>();
      for (int i = 0; i < 26; i++) {
         if (usedLetter[i]) {
            letters.add((char) ('A' + i));
         }
      }
      Collections.sort(letters);

      System.out.print("Letter values: ");
      for (char letter : letters) {
         int value = assignedDigit[letter - 'A'];
         System.out.print(letter + ":" + value + " ");
      }
      System.out.println();
   }

   static boolean[] usedLetter;
   static boolean[] usedDigit;
   static int[] assignedDigit;

   static void markLetters(String w)
   {
      for(int i = 0; i < w.length(); ++i)
         usedLetter[w.charAt(i) - 'A'] = true;
   }

   static boolean check(String w1, String w2, String w3)
   {
      if(leadingZero(w1) || leadingZero(w2) || leadingZero(w3))
         return false;
      return value(w1) + value(w2) == value(w3);
   }

   static boolean leadingZero(String w) { return assignedDigit[w.charAt(0) - 'A'] == 0; }

   static int value(String w)
   {
      int val = 0;
      for(int i = 0; i < w.length(); ++i)
         val = val * 10 + assignedDigit[w.charAt(i) - 'A'];
      return val;
   }

   static void backtrack(int char_idx, String w1, String w2, String w3) {
      if (char_idx == 26) {
         if (check(w1, w2, w3)) {
            solutionCount++;
         }
         return;
      }

      if (!usedLetter[char_idx]) {
         backtrack(char_idx + 1, w1, w2, w3);
         return;
      }

      for (int digit = 0; digit < 10; ++digit) {
         if (!usedDigit[digit]) {
            usedDigit[digit] = true;
            assignedDigit[char_idx] = digit;
            backtrack(char_idx + 1, w1, w2, w3);
            usedDigit[digit] = false;
         }
      }
   }
}
