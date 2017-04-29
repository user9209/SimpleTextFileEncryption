/*
    Copyright (C) 2016  Georg Schmidt <gs-develop@gs-sys.de>
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package de.gs_sys.kp2016.crypto;
/*
 * Created by Georg Schmidt on 16.07.2016.
 */

import de.gs_sys.basics.crypto.hmac.PBKDF;
import de.gs_sys.kp2016.hash.Base64;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.*;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.*;
import org.bouncycastle.crypto.params.KeyParameter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Encrypts and Decrypts byte[], string and files with BC
 * Algos: AES, Rijndael, Twofish, Camellia, Blowfish, Serpent, Threefish
 */

public class SymmetricCipher {

    private static final int DEFAULT_WH = 10000;
    private static final int BUFFER_SPACE = 102400;

    // https://bouncycastle.org/specifications.html

    private static BlockCipher engine = new TwofishEngine();
    private static ENGINE activeEngine = ENGINE.Twofish;

    private static final int ThreefishSize = 1024;

    public enum ENGINE {AES, Rijndael, Twofish, Camellia, Blowfish, Serpent, Threefish } // AES_WRAP

    /*
    private static BlockCipher engine = new TwofishEngine();
    private static BlockCipher engine = new CamelliaEngine();
    private static BlockCipher engine = new BlowfishEngine();
    private static BlockCipher engine = new AESEngine();
    private static BlockCipher engine = new AESWrapEngine();
    private static BlockCipher engine = new RijndaelEngine();
    private static BlockCipher engine = new SerpentEngine();
    private static BlockCipher engine = new ThreefishEngine();
    */





    // BEGIN En-/De-Crypt File

    public static boolean decryptFile(String password, String filenameIn, String filenameOut) throws DecryptionException {
        return decryptFile(extendKey(password,10),filenameIn, filenameOut);
    }

    public static boolean encryptFile(String password, String filenameIn, String filenameOut) {
        return encryptFile(extendKey(password,10),filenameIn, filenameOut);
    }

    public static boolean decryptFile(byte[] password, String filenameIn, String filenameOut) throws DecryptionException {

        byte[] key =  extendKey(password);

        try ( InputStream inputStream = new FileInputStream(filenameIn) ) {
            OutputStream outputStream = new FileOutputStream(filenameOut);

            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine), new ZeroBytePadding());
            cipher.init(false, new KeyParameter(key));

            byte[] buffer = new byte[BUFFER_SPACE];
            int readSize;
            int writeSize;

            byte[] out;

            while((readSize = inputStream.read(buffer))!=-1)
            {
                writeSize = cipher.getOutputSize(buffer.length);
                out = new byte[writeSize];

                writeSize = cipher.processBytes(buffer, 0, readSize, out, 0);
                outputStream.write(out, 0, writeSize);
            }
            outputStream.flush();
            try {
                out = new byte[1024];
                writeSize = cipher.doFinal(out, 0);
                outputStream.write(out, 0, writeSize);
                outputStream.flush();
            } catch (Exception ce) {
                ce.printStackTrace();
                throw new DecryptionException(SymmetricCipher.class.getName());
            }

            outputStream.close();
            inputStream.close();
            return true;
        } catch (FileNotFoundException e) {

        } catch (IOException e1) {

        }
        return false;
    }


    public static boolean encryptFile(byte[] password, String filenameIn, String filenameOut) {

        byte[] key =  extendKey(password);

        try ( InputStream inputStream = new FileInputStream(filenameIn) ) {
            OutputStream outputStream = new FileOutputStream(filenameOut);

            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine), new ZeroBytePadding());
            cipher.init(true, new KeyParameter(key));

            byte[] buffer = new byte[BUFFER_SPACE];
            int readSize;
            int writeSize;

            byte[] out;

            while((readSize = inputStream.read(buffer))!=-1)
            {
                writeSize = cipher.getOutputSize(buffer.length);
                out = new byte[writeSize];

                writeSize = cipher.processBytes(buffer, 0, readSize, out, 0);

                outputStream.write(out, 0, writeSize);
            }

            try {
                out = new byte[1024];
                writeSize = cipher.doFinal(out, 0);
                outputStream.write(out, 0, writeSize);
                outputStream.flush();
            } catch (Exception ce) {
                ce.printStackTrace();
                return false;
            }
            outputStream.close();
            inputStream.close();
            return true;
        } catch (FileNotFoundException e) {

        } catch (IOException e1) {

        }
        return false;
    }

    // END En-/De-Crypt File





    // BEGIN En-/De-Crypt String / byte[]

    public static String encrypt(String key, String in) {

        int wh = new SecureRandom().nextInt(10000);
        byte[] keyB = extendKey(key, wh);
        return wh + "|" + Base64.encode(encrypt(keyB, in.getBytes(StandardCharsets.UTF_8)));
    }

    public static byte[] encrypt(String keyS, byte[] in) {
        byte[] key =  extendKey(keyS, DEFAULT_WH);
        return encrypt(key, in);
    }

    public static byte[] encrypt(byte[] key, byte[] in) {
        key = extendKey(key);

        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));
        cipher.init(true, new KeyParameter(key));
        byte[] out = new byte[cipher.getOutputSize(in.length)];
        int length = cipher.processBytes(in, 0, in.length, out, 0);
        try {
            cipher.doFinal(out, length);
        } catch (Exception ce) {
            ce.printStackTrace();
            return null;
        }
        return out;
    }

    public static String decrypt(String key, String in) throws DecryptionException {
        String[] whAndCypherData = in.split("\\|");

        int wh = Integer.parseInt(whAndCypherData[0]);
        byte[] keyB = extendKey(key, wh);

        byte[] out = decrypt(keyB, Base64.decode(whAndCypherData[1]));
        if(out == null)
            throw new DecryptionException(SymmetricCipher.class.getName());
        return new String(out,StandardCharsets.UTF_8);
    }

    public static byte[] decrypt(String keyS, byte[] cipherText) throws DecryptionException {
        byte[] key = extendKey(keyS, DEFAULT_WH);
        return decrypt(key, cipherText);
    }

    /**
     * May Trim 0-bytes at the end, witch are needed !
     * @param key
     * @param cipherText
     */
    public static byte[] decrypt(byte[] key, byte[] cipherText) throws DecryptionException {
        key = extendKey(key);

        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));
        cipher.init(false, new KeyParameter(key));
        byte[] out = new byte[cipher.getOutputSize(cipherText.length)];
        int length = cipher.processBytes(cipherText, 0, cipherText.length, out, 0);
        try {
            cipher.doFinal(out, length);
        } catch (Exception ce) {
            // ce.printStackTrace();
            throw new DecryptionException(SymmetricCipher.class.getName());
        }

        int t = -1;
        for (int i = out.length - 1; i > 0; i--) {
            if(out[i] == 0)
                t = i;
            else
                break;
        }

        if(t >= 0){
            byte[] out2 = new byte[t];
            System.arraycopy(out, 0, out2, 0, t);
            return out2;
        }

        return out;
    }

    // END En-/De-Crypt String / byte[]









    public static String info() {
        return engine.getAlgorithmName() + " with a block size of " + (engine.getBlockSize() * 8) + " bits.";
    }

    public static String engine() {
        return engine.getAlgorithmName();
    }

    public static void setEngine(ENGINE e)
    {
        activeEngine = e;
        switch (e)
        {
            case AES:
                engine = new AESEngine();
                break;
        //    case AES_WRAP:
        //        engine = new AESWrapEngine();
        //        break;
            case Rijndael:
                engine = new RijndaelEngine();
                break;
            case Camellia:
                engine = new CamelliaEngine();
                break;
            case Blowfish:
                engine = new BlowfishEngine();
               break;
            case Serpent:
                engine = new SerpentEngine();
                break;
            case Threefish:
               //engine = new ThreefishEngine(256);
               engine = new ThreefishEngine(ThreefishSize);
               break;
            case Twofish:
               default:
                   engine = new TwofishEngine();
        }
    }


    protected static byte[] extendKey(String key, int wh)
    {
        if(activeEngine == ENGINE.Threefish)
            return PBKDF.bigPBKDF(key,wh, ThreefishSize);

        return PBKDF.bytePBKDF(key,wh);
    }
    protected static byte[] extendKey(byte[] key)
    {
        if(activeEngine == ENGINE.Threefish)
            return PBKDF.bigPBKDF(key, DEFAULT_WH, ThreefishSize);

        return PBKDF.PBKDF(key);
    }

    protected static byte[] stringKeyToByteKey(String keyIn)
    {
        return extendKey(keyIn, DEFAULT_WH);
    }

    @Deprecated
    protected static BlockCipher getEngine(ENGINE engine) {
        switch (engine)
        {
            case AES:
                return new AESEngine();
            //    case AES_WRAP:
            //        return new AESWrapEngine();
            case Rijndael:
                return new RijndaelEngine();
            case Camellia:
                return new CamelliaEngine();
            case Blowfish:
                return new BlowfishEngine();
            case Serpent:
                return new SerpentEngine();
            case Threefish:
                // return new ThreefishEngine(256);
                return new ThreefishEngine(ThreefishSize);
            case Twofish:
            default:
                return new TwofishEngine();
        }
    }
}