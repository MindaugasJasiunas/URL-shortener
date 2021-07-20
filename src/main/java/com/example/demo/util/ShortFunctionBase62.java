package com.example.demo.util;

public class ShortFunctionBase62 {
    private static final String ALPHABET= "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE= ALPHABET.length();

    /**
     *
     * @param num database id to encode to base 62 format for shortened url
     * @return string with encoded id to base 62 format
     */
    public static String encode(long num){
        if(num<1){ return null; }
        StringBuilder sb = new StringBuilder();

        while( num>0 ){
            int modulo= (int) (num%BASE); // modulo is never bigger than 61 (BASE-1) - down-casting is safe
            num= num/BASE;
            sb.insert(0, ALPHABET.charAt(modulo));
        }
        return sb.toString();
    }

    /**
     *
     * @param str base 62 format encoded shortened url
     * @return num decoded (base 10) id of a table column in DB
     */
    public static long decode(String str){
        if(str==null || str.isEmpty() || str.charAt(0)=='0'){ return -1; }

        long num=0;
        for(int i=0; i<str.length(); i++){
            if(ALPHABET.indexOf(str.charAt(i)) == -1){
                return -1; //invalid input
            }
            num= (num*BASE) + ALPHABET.indexOf(str.charAt(i));
        }
        return num;
    }

    public static void main(String[] args) {
        System.out.println(ALPHABET.charAt(0));
        System.out.println(BASE);
        System.out.println(encode(125L)); // 21
        System.out.println(encode(91574294826053L)); // Q0DRQksv
        System.out.println(encode(-5)); // null
        System.out.println(encode(0)); // null
        System.out.println();
        System.out.println(ALPHABET.charAt(0));
        System.out.println();
        System.out.println(decode("21")); //125
        System.out.println(decode("Q0DRQksv")); // 91574294826053
        System.out.println(decode("")); // -1
        System.out.println(decode(null)); // -1
        System.out.println(decode("-1")); // -1
        System.out.println(decode("#$")); // -1
        System.out.println(decode("0")); // -1

    }


}
