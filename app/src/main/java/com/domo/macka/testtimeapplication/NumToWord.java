package com.domo.macka.testtimeapplication;

/**
 * Created by Macka on 11/22/15.
 */
public class NumToWord {


    private static final String[] tensNames = {
            "",
            " ten",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
    };

    private static final String[] numNames = {
            "",
            " one",
            " two",
            " three",
            " four",
            " five",
            " six",
            " seven",
            " eight",
            " nine",
            " ten",
            " eleven",
            " twelve",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
    };

    public static String convertToWord(String num) {
        String word = "";
        int number = Integer.parseInt(num);

        if (Math.abs(number) / 10 < 1 && number != 0) {
            word = numNames[Math.abs(number)];
            if (number < 0) {
                word = "minus" + word;
            } else {
                word = "plus" + word;
            }
        }
        if (Math.abs(number) / 10 >= 1) {
            if (number > 0) {
                Double number2 = Math.floor(number / 10);
                word = "plus" + tensNames[number2.intValue()] + numNames[number % 10];
            }
            if (number < 0) {
                Double number2 = Math.floor(Math.abs(number) / 10);
                word = "minus" + tensNames[number2.intValue()] + numNames[Math.abs(number) % 10];
            }
        }
        if (number == 0) {
            return "zero";
        }
        return word;
    }
}
