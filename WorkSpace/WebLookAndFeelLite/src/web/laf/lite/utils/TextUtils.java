/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package web.laf.lite.utils;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class provides a set of utilities to work with various text usage cases.
 *
 * @author Mikle Garin
 */

public final class TextUtils
{
    /**
     * Separators used to determine words in text.
     */
    private static final List<String> textSeparators =
            Arrays.asList ( " ", ".", ",", ":", ";", "/", "\\", "\n", "\t", "|", "{", "}", "[", "]", "(", ")", "<", ">", "-", "+", "\"",
                    "'", "*", "%", "$", "#", "@", "!", "~", "^", "&", "?" );

    /**
     * Text provider for any type of objects.
     */

    /**
     * Default ID part length.
     */
    private static final int idPartLength = 5;

    /**
     * Default ID prefix.
     */
    private static final String defaultIdPrefix = "WebLaF";

    /**
     * Default ID suffix.
     */
    private static final String defaultIdSuffix = "ID";

    /**
     * Returns first number found in text.
     *
     * @param text text to search through
     * @return first found number
     */
    public static Integer findFirstNumber ( final String text )
    {
        final StringBuilder sb = new StringBuilder ( "" );
        for ( int j = 0; j < text.length (); j++ )
        {
            final char ch = text.charAt ( j );
            if ( Character.isDigit ( ch ) )
            {
                sb.append ( ch );
            }
            else if ( sb.length () > 0 )
            {
                break;
            }
        }
        return Integer.parseInt ( sb.toString () );
    }

    /**
     * Returns a word from text at the specified location.
     *
     * @param text     text to retrieve the word from
     * @param location word location
     * @return word
     */
    public static String getWord ( final String text, final int location )
    {
        int wordStart = location;
        int wordEnd = location;

        // At the word start
        while ( wordEnd < text.length () - 1 && !textSeparators.contains ( text.substring ( wordEnd, wordEnd + 1 ) ) )
        {
            wordEnd++;
        }

        // At the word end
        while ( wordStart > 0 && !textSeparators.contains ( text.substring ( wordStart - 1, wordStart ) ) )
        {
            wordStart--;
        }

        return wordStart == wordEnd ? null : text.substring ( wordStart, wordEnd );
    }

    /**
     * Returns a word start index at the specified location.
     *
     * @param text     text to retrieve the word start index from
     * @param location word location
     * @return word start index
     */
    public static int getWordStart ( final String text, final int location )
    {
        int wordStart = location;
        while ( wordStart > 0 && !textSeparators.contains ( text.substring ( wordStart - 1, wordStart ) ) )
        {
            wordStart--;
        }
        return wordStart;
    }

    /**
     * Returns a word end index at the specified location.
     *
     * @param text     text to retrieve the word end index from
     * @param location word location
     * @return word end index
     */
    public static int getWordEnd ( final String text, final int location )
    {
        int wordEnd = location;
        while ( wordEnd < text.length () - 1 && !textSeparators.contains ( text.substring ( wordEnd, wordEnd + 1 ) ) )
        {
            wordEnd++;
        }
        return wordEnd;
    }

    /**
     * Returns text with first lines removed.
     *
     * @param text  text to crop
     * @param count lines count to crop
     * @return cropped text
     */
    public static String removeFirstLines ( final String text, final int count )
    {
        int found = 0;
        int index = 0;
        while ( found < count )
        {
            index = text.indexOf ( "\n", index );
            if ( index != -1 )
            {
                index += 1;
                found++;
            }
            else
            {
                return "";
            }
        }
        return text.substring ( index );
    }

    /**
     * Returns a list of text parts splitted using specified separator.
     *
     * @param text      text to split
     * @param separator text parts separator
     * @return list of splitted parts
     */
    public static List<String> split ( final String text, final String separator )
    {
        return Arrays.asList ( text.split ( separator ) );
    }

    /**
     * Returns point extracted from text.
     *
     * @param text text to extract point from
     * @return extracted point
     */
    public static Point parsePoint ( final String text )
    {
        return parsePoint ( text, "," );
    }

    /**
     * Returns point extracted from text.
     *
     * @param text      text to extract point from
     * @param separator point values separator
     * @return extracted point
     */
    public static Point parsePoint ( final String text, final String separator )
    {
        final String[] parts = text.split ( separator );
        return parts.length == 2 ? new Point ( Integer.parseInt ( parts[ 0 ].trim () ), Integer.parseInt ( parts[ 1 ].trim () ) ) : null;
    }

    /**
     * Returns text with all control symbols removed.
     * This method works faster than simple replaceAll("\\p{Cntrl}", "").
     *
     * @param text text to modify
     * @return text without conytol symbols
     */
    public static String removeControlSymbols ( final String text )
    {
        final int length = text.length ();
        final char[] oldChars = new char[ length ];
        text.getChars ( 0, length, oldChars, 0 );
        int newLen = 0;
        for ( int j = 0; j < length; j++ )
        {
            final char ch = oldChars[ j ];
            if ( ch >= ' ' )
            {
                oldChars[ newLen ] = ch;
                newLen++;
            }
        }
        return text;
    }

    /**
     * Returns shortened text.
     *
     * @param text      text to shorten
     * @param maxLength maximum shortened text lenght
     * @param addDots   add dots at the end of the text when shortened
     * @return shortened text
     */
    public static String shortenText ( final String text, final int maxLength, final boolean addDots )
    {
        return text.length () <= maxLength ? text :
                text.substring ( 0, maxLength > 3 && addDots ? maxLength - 3 : maxLength ) + ( addDots ? "..." : "" );
    }

    /**
     * Returns a list of text parts splitted using specified separator.
     *
     * @param string    text to split
     * @param separator text parts separator
     * @return list of splitted parts
     */
    public static List<String> stringToList ( final String string, final String separator )
    {
        final List<String> imageTags = new ArrayList<String> ();
        if ( string != null )
        {
            final StringTokenizer tokenizer = new StringTokenizer ( string, separator, false );
            while ( tokenizer.hasMoreTokens () )
            {
                imageTags.add ( tokenizer.nextToken ().trim () );
            }
        }
        return imageTags;
    }

    /**
     * Returns a list of integer parts splitted using specified separator.
     *
     * @param string    text to split
     * @param separator text parts separator
     * @return list of splitted parts
     */
    public static List<Integer> stringToIntList ( final String string, final String separator )
    {
        final List<String> stringList = stringToList ( string, separator );
        if ( stringList != null )
        {
            final List<Integer> intList = new ArrayList<Integer> ( stringList.size () );
            for ( String s : stringList )
            {
                intList.add ( Integer.parseInt ( s ) );
            }
            return intList;
        }
        else
        {
            return null;
        }
    }



    /**
     * Returns random ID with default prefix and suffix.
     *
     * @return ID
     */
    public static String generateId ()
    {
        return generateId ( null, null );
    }

    /**
     * Returns random ID with specified prefix and default suffix.
     *
     * @param prefix id prefix
     * @return ID
     */
    public static String generateId ( final String prefix )
    {
        return generateId ( prefix, null );
    }

    /**
     * Returns random ID with specified prefix and suffix.
     *
     * @param prefix id prefix
     * @param suffix id suffix
     * @return ID
     */
    public static String generateId ( final String prefix, final String suffix )
    {
        final StringBuilder idBuilder = new StringBuilder ();
        idBuilder.append ( prefix == null ? defaultIdPrefix : prefix );
        idBuilder.append ( "-" );
        idBuilder.append ( generateId ( idPartLength ) );
        idBuilder.append ( "-" );
        idBuilder.append ( generateId ( idPartLength ) );
        idBuilder.append ( "-" );
        idBuilder.append ( generateId ( idPartLength ) );
        idBuilder.append ( "-" );
        idBuilder.append ( generateId ( idPartLength ) );
        idBuilder.append ( "-" );
        idBuilder.append ( suffix == null ? defaultIdSuffix : suffix );
        return idBuilder.toString ();
    }

    /**
     * Returns randomly generated ID part with specified length.
     *
     * @param length part length in symbols
     * @return ID part
     */
    private static String generateId ( final int length )
    {
        final StringBuilder stringBuilder = new StringBuilder ( length );
        for ( int i = 0; i < length; i++ )
        {
            char next = 0;
            int range = 10;
            switch ( MathUtils.random ( 3 ) )
            {
                case 0:
                {
                    next = '0';
                    range = 10;
                    break;
                }
                case 1:
                {
                    next = 'a';
                    range = 26;
                    break;
                }
                case 2:
                {
                    next = 'A';
                    range = 26;
                    break;
                }
            }
            stringBuilder.append ( ( char ) ( ( MathUtils.random ( range ) ) + next ) );
        }
        return stringBuilder.toString ();
    }
}