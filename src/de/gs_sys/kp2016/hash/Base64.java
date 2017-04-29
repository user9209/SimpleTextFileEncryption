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

package de.gs_sys.kp2016.hash;

import java.nio.charset.StandardCharsets;

/**
 * Created by Georg Schmidt on 20.04.2016.
 */
public class Base64 {

    public static String encode(String in)
    {
        return java.util.Base64.getEncoder().encodeToString(in.getBytes(StandardCharsets.UTF_8));
    }
    public static String encode(byte[] in)
    {
        return java.util.Base64.getEncoder().encodeToString(in);
    }

    public static byte[] decode(String in)
    {
        return java.util.Base64.getDecoder().decode(in);
    }

    public static String decodeString(String in)
    {
        return new String(java.util.Base64.getDecoder().decode(in), StandardCharsets.UTF_8);
    }

    public static byte[] decode(byte[] in)
    {
        return java.util.Base64.getDecoder().decode(in);
    }
}
