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

package de.gs_sys.basics.crypto.hmac;

import de.gs_sys.kp2016.hash.Base64;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

import java.nio.charset.StandardCharsets;

/**
 * Created by Georg Schmidt on 20.03.2017..
 */
public class PBKDF {

    public static final int SaltBitSize = 128;
    private static final int Iterations = 10000;
    private static final int KeyBitSize = 256;

    private static final String PBKDF_SALT = "as456ddsa64da85s4d6as5446d5s5";

    // SALT should be set random
    private static byte[] SALT = PBKDF_SALT.getBytes(StandardCharsets.UTF_8); //new byte[SaltBitSize / 8];

    /**
     *   Performs a Password-Based Key Derivation Function
     */
    public static String PBKDF(String pas, int iterations)
    {

        return Base64.encode(bytePBKDF( pas, iterations));
    }

    public static byte[] bytePBKDF(String pas, int iterations)
    {
        if(pas == null)
            return null;

        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator();

        // uses PKCS#5 standard
        generator.init(
                PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(pas.toCharArray()),
                SALT,
                iterations);

        // Generate Key 32 chars at 8 bit so total 256 bit
        KeyParameter key = (KeyParameter)generator.generateDerivedMacParameters(KeyBitSize);

        return key.getKey();
    }

    /**
     *   Performs a Password-Based Key Derivation Function
     */
    public static byte[] PBKDF(byte[] pas, int iterations)
    {
        if(pas == null)
            return null;

        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator();

        // uses PKCS#5 standard
        generator.init(
                pas,
                SALT,
                iterations);

        // Generate Key 32 chars at 8 bit so total 256 bit
        KeyParameter key = (KeyParameter)generator.generateDerivedMacParameters(KeyBitSize);

        return key.getKey();
    }

    /**
     *   Performs a Password-Based Key Derivation Function
     */
    public static String PBKDF(String pas)
    {

        return PBKDF(pas, Iterations);
    }

    /**
     *   Performs a Password-Based Key Derivation Function
     */
    public static char[] PBKDF(char[] pas)
    {
        if(pas == null)
            return null;
        return PBKDF(new String(pas), Iterations).toCharArray();
    }

    /**
     *   Performs a Password-Based Key Derivation Function
     */
    public static byte[] PBKDF(byte[] pas)
    {
        return PBKDF(pas, Iterations);
    }


    /**
     *   Performs a Password-Based Key Derivation Function
     */
    public static byte[] bigPBKDF(byte[] pas, int iterations, int KeyBitSize)
    {
        if(pas == null)
            return null;

        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator();

        // uses PKCS#5 standard
        generator.init(
                pas,
                SALT,
                iterations);

        // Generate Key 32 chars at 8 bit so total 256 bit
        KeyParameter key = (KeyParameter)generator.generateDerivedMacParameters(KeyBitSize);

        return key.getKey();
    }

    public static byte[] bigPBKDF(String pas, int iterations, int KeyBitSize)
    {
        if(pas == null)
            return null;

        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator();

        // uses PKCS#5 standard
        generator.init(
                PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(pas.toCharArray()),
                SALT,
                iterations);

        // Generate Key 32 chars at 8 bit so total 256 bit
        KeyParameter key = (KeyParameter)generator.generateDerivedMacParameters(KeyBitSize);

        return key.getKey();
    }
}
