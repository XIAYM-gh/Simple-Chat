package tcp.server;

import java.security.*;

public class SHAUtil {
     public static String SHA256(final String strText) {
         return SHA(strText, "SHA-256");
     }

      public static String SHA512(final String strText) {
          return SHA(strText, "SHA-512");
      }
      
      public static String SHAMD5(String strText) {
          return SHA(strText, "MD5");
      }

       private static String SHA(final String strText, final String strType) {
           String strResult = null;

           if (strText != null && strText.length() > 0) {
               try {
                   MessageDigest messageDigest = MessageDigest.getInstance(strType);
                   messageDigest.update(strText.getBytes());
                   byte byteBuffer[] = messageDigest.digest();

                   StringBuffer strHexString = new StringBuffer();
                   for (int i = 0; i < byteBuffer.length; i++) {
                       String hex = Integer.toHexString(0xff & byteBuffer[i]);
                       if (hex.length() == 1) {
                           strHexString.append('0');
                       }
                       strHexString.append(hex);
                   }
                   strResult = strHexString.toString();
               } catch (NoSuchAlgorithmException e) {
                   e.printStackTrace();
               }
           }

           return strResult;
       }
}
