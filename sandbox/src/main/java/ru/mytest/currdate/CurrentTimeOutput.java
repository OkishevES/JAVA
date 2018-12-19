package ru.mytest.currdate;

import java.util.*;
import java.text.*;

public class CurrentTimeOutput {

   public static void main(String args[]) {
      Date dateNow = new Date();
      SimpleDateFormat formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd 'and time' hh:mm:ss a zzz");

      System.out.println("Current date " + formatForDateNow.format(dateNow));
   }
}