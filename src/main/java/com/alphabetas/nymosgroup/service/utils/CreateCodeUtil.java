package com.alphabetas.nymosgroup.service.utils;

import java.util.Random;

public class CreateCodeUtil {
    private static String[] symbols = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}; // 62 symbols
   public static String createCode(int length){
       Random random = new Random();
       StringBuilder builder = new StringBuilder();
       while(builder.length() <= length){
           builder.append(symbols[random.nextInt(62)]);
       }
       return builder.toString();
   }

   // nymos-group.herokuapp.com
   // localhost:8090
   public static String createInviteCode(){
       return ("nymos-group.herokuapp.com/invite/"+createCode(5));
   }
}
