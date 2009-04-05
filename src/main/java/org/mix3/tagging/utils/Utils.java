package org.mix3.tagging.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

import org.mix3.tagging.WicketApplication;

public class Utils {
	public final static String RANDOM_ALGORITHM = "SHA1PRNG";
	public final static int    RANDOM_LENGTH    = 24;
	
	public static Properties getDBProperties(){
		Properties back = new Properties();
		InputStream is = WicketApplication.class.getResourceAsStream("/db.properties");
		if (is == null) {
			throw new RuntimeException("Unable to locate db.properties");
		}
		try {
			back.load(is);
			is.close();
		} catch (IOException e) {
			throw new RuntimeException("Unable to load db.properties");
		}
		return back;
	}
	
	public static String digest(String strKey) throws NoSuchAlgorithmException{
		//md5のキーとなる文字列
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(strKey.getBytes());
		byte byDig[] = md.digest();
		String strOut = "";
		for (int i = 0; i < byDig.length; i++) {
			int d = byDig[i];
			if (d < 0) { //byte型では128～255が負値になっているので補正
				d += 256;
			}
			if (d < 16) { //0～15は16進数で1けたになるので、2けたになるよう頭に0を追加
				strOut += "0";
			}
			strOut += Integer.toString(d, 16); //ダイジェスト値の1バイトを16進数2けたで表示
		}
		return strOut;
	}
	
    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        OutputStream os = new BufferedOutputStream(b);
        int c;
        try {
            while ((c = is.read()) != -1) {
                os.write(c);
            }
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
        return b.toByteArray();
    }
    
    public static byte[] getRandom() throws NoSuchAlgorithmException {
    	SecureRandom random = SecureRandom.getInstance(RANDOM_ALGORITHM);
    	byte seed[] = random.generateSeed(RANDOM_LENGTH);
    	byte b[] = new byte[RANDOM_LENGTH];
    	random.setSeed(seed);
    	random.nextBytes(b);
    	return b;
    }
}
