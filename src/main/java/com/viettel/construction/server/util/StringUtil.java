package com.viettel.construction.server.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class StringUtil {
    public static boolean isNullOrEmpty(String input) {
        if (input == null || "".equals(input.trim()) || "null".equals(input)) {
            return true;
        }
        return false;
    }

    public static String[] splitStringByCharacter(String str, String character) {
        String[] result = null;
        if (!StringUtil.isNullOrEmpty(str) || !StringUtil.isNullOrEmpty(character)) {
            result = str.split(character);
        }
        return result;
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static String printPhone(String phoneNum) {
        StringBuilder sb = new StringBuilder(15);
        StringBuilder temp = new StringBuilder(phoneNum.toString());

        while (temp.length() < 11)
            temp.insert(0, "0");

        char[] chars = temp.toString().toCharArray();


        for (int i = 0; i < chars.length; i++) {
            if (i == 4)
                sb.append("-");
            else if (i == 7)
                sb.append("-");
            sb.append(chars[i]);
        }

        return sb.toString();
    }

    //Convert Image to Base64 Encoded String
    //Decode Base64 String to Image

    public static String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public static Bitmap setImage(String stringBase64) {
        if (stringBase64 != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] imageBytes = baos.toByteArray();
            imageBytes = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            return decodedImage;
        } else return null;
//        imageView.setImageBitmap(decodedImage);
    }

    public static String getVersionApp(Context context) {
        PackageInfo pinfo = null;
        String verName = "";
        try {
            pinfo = context.getPackageManager().getPackageInfo("com.viettel.construction", 0);
//            int verCode = pinfo.versionCode;
            verName = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    private static Map<Character, Character> MAP_NORM;

    public static String removeAccents(String value) {
        if (MAP_NORM == null || MAP_NORM.size() == 0) {
            MAP_NORM = new HashMap<Character, Character>();
            MAP_NORM.put('À', 'A');


            MAP_NORM.put('Á', 'A');

            MAP_NORM.put('Â', 'A');

            MAP_NORM.put('Ã', 'A');

            MAP_NORM.put('Ä', 'A');

            MAP_NORM.put('È', 'E');

            MAP_NORM.put('É', 'E');

            MAP_NORM.put('Ê', 'E');

            MAP_NORM.put('Ë', 'E');

            MAP_NORM.put('Í', 'I');

            MAP_NORM.put('Ì', 'I');

            MAP_NORM.put('Î', 'I');

            MAP_NORM.put('Ï', 'I');

            MAP_NORM.put('Ù', 'U');

            MAP_NORM.put('Ú', 'U');

            MAP_NORM.put('Û', 'U');

            MAP_NORM.put('Ü', 'U');

            MAP_NORM.put('Ò', 'O');

            MAP_NORM.put('Ó', 'O');

            MAP_NORM.put('?', 'O');

            MAP_NORM.put('Ô', 'O');

            MAP_NORM.put('Õ', 'O');

            MAP_NORM.put('Ö', 'O');

            MAP_NORM.put('Ñ', 'N');

            MAP_NORM.put('Ç', 'C');

            MAP_NORM.put('ª', 'A');

            MAP_NORM.put('º', 'O');

            MAP_NORM.put('§', 'S');

            MAP_NORM.put('³', '3');

            MAP_NORM.put('²', '2');

            MAP_NORM.put('¹', '1');

            MAP_NORM.put('à', 'a');

            MAP_NORM.put('á', 'a');

            MAP_NORM.put('â', 'a');

            MAP_NORM.put('ã', 'a');

            MAP_NORM.put('ä', 'a');

            MAP_NORM.put('è', 'e');

            MAP_NORM.put('é', 'e');

            MAP_NORM.put('ê', 'e');

            MAP_NORM.put('ë', 'e');

            MAP_NORM.put('í', 'i');

            MAP_NORM.put('ì', 'i');

            MAP_NORM.put('î', 'i');

            MAP_NORM.put('ï', 'i');

            MAP_NORM.put('ù', 'u');

            MAP_NORM.put('ú', 'u');

            MAP_NORM.put('û', 'u');

            MAP_NORM.put('ü', 'u');

            MAP_NORM.put('ò', 'o');

            MAP_NORM.put('ó', 'o');

            MAP_NORM.put('ô', 'o');

            MAP_NORM.put('õ', 'o');

            MAP_NORM.put('ö', 'o');

            MAP_NORM.put('ñ', 'n');

            MAP_NORM.put('ç', 'c');

            MAP_NORM.put('?', 'a');

            MAP_NORM.put('?', 'e');

            MAP_NORM.put('?', '?');

            MAP_NORM.put('?', '?');

            MAP_NORM.put('?', '?');

            MAP_NORM.put('?', '?');

            MAP_NORM.put('?', 's');

            MAP_NORM.put('?', '?');

            MAP_NORM.put('?', 'a');

            MAP_NORM.put('?', 'e');

            MAP_NORM.put('?', '?');

            MAP_NORM.put('?', '?');

            MAP_NORM.put('?', '?');

            MAP_NORM.put('?', '?');

            MAP_NORM.put('?', '?');
        }

        if (value == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder(value);

        for (int i = 0; i < value.length(); i++) {
            Character c = MAP_NORM.get(sb.charAt(i));
            if (c != null) {
                sb.setCharAt(i, c.charValue());
            }
        }

        return sb.toString();

    }

    private static char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
            'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
            'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
            'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
            'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
            'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
            'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
            'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
            'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
            'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
            'ữ', 'Ự', 'ự', 'ỳ', 'Ỳ', 'ỹ', 'Ỹ'};

    // Mang cac ky tu thay the khong dau
    private static char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E',
            'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
            'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
            'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
            'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u', 'U', 'u', 'y', 'Y', 'y', 'Y'};

    /**
     * Bo dau 1 ky tu
     *
     * @param ch
     * @return
     */
    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS[index];
        }
        return ch;
    }

    /**
     * Bo dau 1 chuoi
     *
     * @param s
     * @return
     */
    public static String removeAccentStr(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
