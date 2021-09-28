package com.hos.foo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Palindromes {

    private final static int MAX_ID = 10005;
    private final static int MAX_ID_LEN = 5;
    private static List<Character> primeList;

    static {
        buildPrimeNumbers();
    }

    public static String solution(int i) {
        // int nn = i;
        if (i < 0 || i > 10000)
            throw new IllegalStateException(
                    "start index should be between 0 and 1000, Sorry");

        String finalNewId = "";

        while (finalNewId.length() < MAX_ID_LEN) {
            finalNewId += primeList.get(i++);
        }

        // System.out.println("Index==>" + nn + ": value ==>" + finalNewId);
        return finalNewId;

    }

    private static void buildPrimeNumbers() {
        primeList = new ArrayList<Character>();
        for (int i = 2; primeList.size() <= MAX_ID; i++) {
            if (detectPrimeNumber(i)) {
                String temp = Integer.toString(i);
                for (int c = 0; c < temp.length(); c++)
                    primeList.add(temp.charAt(c));
            }
        }
    }

    public static boolean detectPrimeNumber(int n) {
        for (int i = 2; 2 * i <= n; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String... arg) {
        Palindromes s = new Palindromes();
        System.out.println(s.findAllPalindromesUsingCenter("fgaba"));

        s.hello(null);
        //System.out.println(primeList);
        //System.out.println(primeList.size());
        Palindromes.solution(0);
        Palindromes.solution(3);
        Palindromes.solution(4);
        Palindromes.solution(45);
        Palindromes.solution(89);
        Palindromes.solution(10000);
        Palindromes.solution(-3);
        Palindromes.solution(1066);
    }

    public void hello(String ob) {
        System.out.println("string");
    }

    public void hello(Object ob) {
        System.out.println("ob");

    }

    public Set<String> findAllPalindromesUsingCenter(String input) {
        Set<String> palindromes = new HashSet<>();
        for (int i = 0; i < input.length(); i++) {
            palindromes.addAll(findPalindromes(input, i, i + 1));
            palindromes.addAll(findPalindromes(input, i, i));
        }
        return palindromes;
    }

    private Set<String> findPalindromes(String input, int low, int high) {
        Set<String> result = new HashSet<>();
        while (low >= 0 && high < input.length()
                && input.charAt(low) == input.charAt(high)) {
            result.add(input.substring(low, high + 1));
            low--;
            high++;
        }
        return result;
    }

}
