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

/**
 * Created by Georg Schmidt on 11.11.2016.
 */
public class SecureRandom extends java.security.SecureRandom {

    private static SecureRandom instance = null;

    protected static SecureRandom getInstance()
    {
        if(instance == null)
        {
            instance = new SecureRandom();
        }
        return instance;
    }

    private final static char[] ALPHABET = {'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            ':', ';', '<', '=', '>', '?', '@',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z',
            '[', '\\', ']', '^', '_',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z',

            '{','}','[',']'
    };

    private final static char[] ALPHABET_UNIQUE = {'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
            ':', ';', '<', '=', '>', '?', '@', '[', ']', '_', '{','}','[',']',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z',
    };



    public char nextChar() {
        return ALPHABET_UNIQUE[super.nextInt(ALPHABET_UNIQUE.length)];
    }


    /**
     * Minimal password size 5, Default is 12 chars
     * @param size
     * @return password
     */
    public static char[] getSecurePassword(int size)
    {
        if(size < 5)
            size = 12;

        char[] newPassword = new char[size];

        for (int i = 0; i < size; i++) {
            newPassword[i] = getInstance().nextChar();
        }
        return newPassword;
    }

    public static String getSecurePasswordString(int size)
    {
        return new String(getSecurePassword(size));
    }

    public static String getPin(int length) {
        return Integer.toString(getInstance().next(length));
    }

    public static byte[] getBytes(int size) {
        byte[] key = new byte[size];
        getInstance().nextBytes(key);
        return key;
    }
}
