package com.smc.androidbase.arithmetic;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class KMP {

    public static void main(String[] args){
        KMP kmp = new KMP();
        int pos = kmp.kmp("abacbcabababbcbc", "ababb");
        System.out.println(pos);
    }

    int[] getNext(String p){

        int j, k, length;
        j = 0;
        k = -1;
        length = p.length();
        int[] next = new int[length];
        next[0] = -1;
        while(j < length - 1){
            if(k == -1 || p.charAt(j) == p.charAt(k)){
                next[++j] = ++k;
            }else{
                k = next[k];
            }
        }
        return next;
    }

    int kmp(String t, String p){
        int i = 0;
        int j = 0;
        int[] next = getNext(p);
        while(i < t.length() && j < p.length()){
            if(j == -1 || t.charAt(i) == p.charAt(j)){
                i++;
                j++;
            }else{
                j=next[j];
            }
        }
        if(j == p.length()){
            return i - j;
        }else{
            return -1;
        }
    }
}
