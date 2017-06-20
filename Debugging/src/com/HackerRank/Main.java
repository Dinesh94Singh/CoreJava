package com.HackerRank;

import com.HackerRank.StringUtilities;

public class Main {

    public static void main(String[] args) {

        StringUtilities utils = new StringUtilities();
        StringBuilder sb = new StringBuilder();
        while(sb.length()<0){
            utils.appendChars(sb,'a');
        }
        System.out.println(sb);
    }
}
