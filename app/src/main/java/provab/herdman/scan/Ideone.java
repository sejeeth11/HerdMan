package provab.herdman.scan;

import android.util.Log;

public class Ideone {


    public Ideone() throws Exception {
        // your code goes here
       // encryptData(data, "101");

    }

    public String getResult(String data)
    {
        String decryptedstring=hexToASCII(data);
        Log.e("decrypted text", decryptedstring);
        return decryptData(decryptedstring,"101");
    }

    public void encryptData(String input, String key) {
        String encryptedText = "";
        for (int i = 0; i < input.length(); i++) {
            int random = 0;
            if (i == 0) {
                random = 13;
            }
            if (i == 1) {
                random = 12;
            }
            if (i == 2) {
                random = 3;
            }
            if (i == 3) {
                random = 4;

            }
            if (i == 4) {
                random = 2;
            }
            if (i == 5) {
                random = 5;
            }
            if (i == 6) {
                random = 7;
            }
            if (i == 7) {
                random = 6;

            }
            if (i == 8) {
                random = 9;
            }
            if (i == 9) {
                random = 14;
            }
            if (i == 10) {
                random = 15;
            }
            if (i == 11) {
                random = 1;
            }
            if (i == 12) {
                random = 8;
            }
            if (i == 13) {
                random = 10;
            }
            if (i == 14) {
                random = 11;
            }
            int finalAscii = getValue(i, input, key, random);
            String temp = Integer.toHexString(finalAscii);
            encryptedText += temp;

        }

        Log.e("encrypted text", encryptedText);
        String decryptedstring=hexToASCII(encryptedText);
        Log.e("decrypted text", decryptedstring);
        decryptData(decryptedstring,"101");
    }


    public String  decryptData(String output, String key)
    {
        String decryptedText = "";
        for (int i = 0; i < output.length(); i++) {
            int random = 0;
            if (i == 0) {
                random = 13;
            }
            if (i == 1) {
                random = 12;
            }
            if (i == 2) {
                random = 3;
            }
            if (i == 3) {
                random = 4;

            }
            if (i == 4) {
                random = 2;
            }
            if (i == 5) {
                random = 5;
            }
            if (i == 6) {
                random = 7;
            }
            if (i == 7) {
                random = 6;

            }
            if (i == 8) {
                random = 9;
            }
            if (i == 9) {
                random = 14;
            }
            if (i == 10) {
                random = 15;
            }
            if (i == 11) {
                random = 1;
            }
            if (i == 12) {
                random = 8;
            }
            if (i == 13) {
                random = 10;
            }
            if (i == 14) {
                random = 11;
            }
            int asciivalue = getInvertedValue(i, output, key, random);
            char ch= (char) asciivalue;
            decryptedText += ch;

        }

        Log.e("decrypted text", decryptedText);
        return decryptedText;
    }

    public  int getValue(int i, String input, String key, int random) {
        int asciiInputChar = input.charAt(i);
        int j = i + 1;
        int modvalue = j % key.length();
        int acsciiKey = key.charAt(modvalue);
        int finalvalue=asciiInputChar + acsciiKey + random;
        Log.e("pos"+i,finalvalue+"");
        return finalvalue;

    }

    public  int getInvertedValue(int i, String input, String key, int random)
    {
        int asciiInputChar = input.charAt(i);
        int j = i + 1;
        int modvalue = j % key.length();
        int acsciiKey = key.charAt(modvalue);
        int finalvalue=asciiInputChar - acsciiKey - random;
        Log.e("pos"+i,finalvalue+"");
        return finalvalue;
    }

    private static String asciiToHex(String asciiValue) {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
    }

    private static String hexToASCII(String hexValue) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexValue.length(); i += 2) {
            String str = hexValue.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }


}