package com.HackerRank;

/**
 * Created by Dinesh Singh on 6/19/2017.
 */
public class StringUtilities {
    private StringBuilder sb = new StringBuilder();
    private int charsAdded = 0;

    public void appendChars(StringBuilder sb, char c){
        sb.append(c);
        this.sb = sb;
        charsAdded ++;
    }
}
