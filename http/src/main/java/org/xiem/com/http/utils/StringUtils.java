package org.xiem.com.http.utils;

import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static final String[] toLowerCaseWordArray(String text) {
		
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		
		StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+");
		
		String[] words = new String[tokens.countTokens()];
		
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken().toLowerCase();
		}
		
		return words;
	}

	public static final String[] toStringArray(String text) {
		
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		
		StringTokenizer tokens = new StringTokenizer(text, ",\r\n/\\");
		
		String[] words = new String[tokens.countTokens()];
		
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken();
		}
		
		return words;
	}

	public static final String[] toStringArray(String text, String token) {
		
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		
		StringTokenizer tokens = new StringTokenizer(text, token);
		
		String[] words = new String[tokens.countTokens()];
		
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken();
		}
		
		return words;
	}

	public static String[] splitOnWhitespace(String source) {
		
		int pos = -1;
		
		LinkedList<String> list = new LinkedList<String>();
		
		int max = source.length();
		
		for (int i = 0; i < max; i++) {
			char c = source.charAt(i);
			if (Character.isWhitespace(c)) {
				if (i - pos > 1) {
					list.add(source.substring(pos + 1, i));
				}
				pos = i;
			}
		}
		
		return list.toArray(new String[list.size()]);
	}

	public static final String replaceAll(String str, String key, String replacement) {
		
		if (str != null && key != null && replacement != null && !str.equals("") && !key.equals("")) {
			StringBuilder strbuf = new StringBuilder();
			int begin = 0;
			int slen = str.length();
			int npos = 0;
			int klen = key.length();
			for (; begin < slen && (npos = str.indexOf(key, begin)) >= begin; begin = npos + klen) {
				strbuf.append(str.substring(begin, npos)).append(replacement);
			}

			if (begin == 0) {
				return str;
			}
			if (begin < slen) {
				strbuf.append(str.substring(begin));
			}
			return strbuf.toString();
		} else {
			return str;
		}
	}

	public static String UnicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		boolean hasU = false;
		while (matcher.find()) {
			hasU = true;
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		String s = str;
		try {
			if (!hasU) {
				int i = 0;
				String rstr = "";
				while (i + 4 <= str.length()) {
					ch = (char) Integer.parseInt(str.substring(i, i = i + 4), 16);
					rstr = rstr + ch;
				}
				str = rstr;
			}
		} catch (Exception ex) {
			str = s;
			ex.printStackTrace();
		}
		return str;
	}

	public static final String EMPTY_STRING = "";//���ַ���

	/***************************************************************
	 * �Ƚ������ַ�������Сд���У���
	 * 
	 * StringUtil.equals(null, null)   = true
	 * StringUtil.equals(null, "abc")  = false
	 * StringUtil.equals("abc", null)  = false
	 * StringUtil.equals("abc", "abc") = true
	 * StringUtil.equals("abc", "ABC") = false
	 *
	 * str1 -- Ҫ�Ƚϵ��ַ���1
	 * str2 -- Ҫ�Ƚϵ��ַ���2
	 *
	 * ��������ַ�����ͬ,���߶���NULL,�򷵻�TRUE
	 ****************************************************************/
	public static boolean equals(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equals(str2);
	}

	/***********************************************************************
	 * �Ƚ������ַ�������Сд�����У���
	 * 
	 * StringUtil.equalsIgnoreCase(null, null)   = true
	 * StringUtil.equalsIgnoreCase(null, "abc")  = false
	 * StringUtil.equalsIgnoreCase("abc", null)  = false
	 * StringUtil.equalsIgnoreCase("abc", "abc") = true
	 * StringUtil.equalsIgnoreCase("abc", "ABC") = true
	 *
	 * str1 -- Ҫ�Ƚϵ��ַ���1
	 * str2 -- Ҫ�Ƚϵ��ַ���2
	 *
	 * ��������ַ�����ͬ,���߶���NULL,�򷵻�TRUE
	 ***********************************************************************/
	public static boolean equalsIgnoreCase(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equalsIgnoreCase(str2);
	}

	/*************************************************************************
	 * ����ַ����Ƿ��ǿհ�:NULL�����ַ���""��ֻ�пհ��ַ�.
	 * 
	 * StringUtil.isBlank(null)      = true
	 * StringUtil.isBlank("")        = true
	 * StringUtil.isBlank(" ")       = true
	 * StringUtil.isBlank("bob")     = false
	 * StringUtil.isBlank("  bob  ") = false
	 *
	 * str -- Ҫ�����ַ���
	 *
	 * ���Ϊ�հ�,�򷵻�TRUE
	 *************************************************************************/
	public static boolean isBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/***********************************************************************
	 * ����ַ����Ƿ��ǿհ�:NULL�����ַ���""��ֻ�пհ��ַ�.
	 * 
	 * StringUtil.isBlank(null)      = false
	 * StringUtil.isBlank("")        = false
	 * StringUtil.isBlank(" ")       = false
	 * StringUtil.isBlank("bob")     = true
	 * StringUtil.isBlank("  bob  ") = true
	 *
	 * str -- Ҫ�����ַ���
	 *
	 * ���Ϊ�հ�,�򷵻�TRUE
	 ***********************************************************************/
	public static boolean isNotBlank(String str) {
		
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	/***********************************************************************
	 * ����ַ����Ƿ�ΪNULL����ַ���"".
	 * 
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty("")        = true
	 * StringUtil.isEmpty(" ")       = false
	 * StringUtil.isEmpty("bob")     = false
	 * StringUtil.isEmpty("  bob  ") = false
	 *
	 * str -- Ҫ�����ַ���
	 *
	 * ���Ϊ��,�򷵻�TRUE
	 ***********************************************************************/
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	/***********************************************************************
	 * ����ַ����Ƿ���<code>null</code>�Ϳ��ַ���<code>""</code>��
	 * 
	 * StringUtil.isEmpty(null)      = false
	 * StringUtil.isEmpty("")        = false
	 * StringUtil.isEmpty(" ")       = true
	 * StringUtil.isEmpty("bob")     = true
	 * StringUtil.isEmpty("  bob  ") = true
	 *
	 * str -- Ҫ�����ַ���
	 * �����Ϊ��,�򷵻�TRUE
	 ***********************************************************************/
	public static boolean isNotEmpty(String str) {
		return ((str != null) && (str.length() > 0));
	}

	/***********************************************************************
	 * ���ַ����в���ָ���ַ���,�����ص�һ��ƥ�������ֵ.����ַ���ΪNULL��δ�ҵ�,�򷵻�-1.
	 * 
	 * StringUtil.indexOf(null, *)          = -1
	 * StringUtil.indexOf(*, null)          = -1
	 * StringUtil.indexOf("", "")           = 0
	 * StringUtil.indexOf("aabaabaa", "a")  = 0
	 * StringUtil.indexOf("aabaabaa", "b")  = 2
	 * StringUtil.indexOf("aabaabaa", "ab") = 1
	 * StringUtil.indexOf("aabaabaa", "")   = 0
	 *
	 * str 			-- Ҫɨ����ַ���
	 * searchStr 	-- Ҫ���ҵ��ַ���
	 *
	 * ��һ��ƥ�������ֵ.����ַ���ΪNULL��δ�ҵ�,�򷵻�-1
	 ***********************************************************************/
	public static int indexOf(String str, String searchStr) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		return str.indexOf(searchStr);
	}

	/***********************************************************************
	 * ���ַ����в���ָ���ַ���,�����ص�һ��ƥ�������ֵ.����ַ���ΪNULL��δ�ҵ�,�򷵻�-1.
	 * 
	 * StringUtil.indexOf(null, *, *)          = -1
	 * StringUtil.indexOf(*, null, *)          = -1
	 * StringUtil.indexOf("", "", 0)           = 0
	 * StringUtil.indexOf("aabaabaa", "a", 0)  = 0
	 * StringUtil.indexOf("aabaabaa", "b", 0)  = 2
	 * StringUtil.indexOf("aabaabaa", "ab", 0) = 1
	 * StringUtil.indexOf("aabaabaa", "b", 3)  = 5
	 * StringUtil.indexOf("aabaabaa", "b", 9)  = -1
	 * StringUtil.indexOf("aabaabaa", "b", -1) = 2
	 * StringUtil.indexOf("aabaabaa", "", 2)   = 2
	 * StringUtil.indexOf("abc", "", 9)        = 3
	 *
	 * str 			-- Ҫɨ����ַ���
	 * searchStr 	-- Ҫ���ҵ��ַ���
	 * startPos		-- ��ʼ����������ֵ,���С��0,����0
	 *
	 * ��һ��ƥ�������ֵ.����ַ���ΪNULL��δ�ҵ�,�򷵻�-1
	 ***********************************************************************/
	public static int indexOf(String str, String searchStr, int startPos) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		// JDK1.3�����°汾��BUG:������ȷ������������
		if ((searchStr.length() == 0) && (startPos >= str.length())) {
			return str.length();
		}

		return str.indexOf(searchStr, startPos);
	}

	/***********************************************************************
	 * ȡָ���ַ������Ӵ�.
	 * 
	 * �������������β����ʼ����.����ַ���ΪNULL,�򷵻�NUL
	 * 
	 * StringUtil.substring(null, *, *)    = null
	 * StringUtil.substring("", * ,  *)    = "";
	 * StringUtil.substring("abc", 0, 2)   = "ab"
	 * StringUtil.substring("abc", 2, 0)   = ""
	 * StringUtil.substring("abc", 2, 4)   = "c"
	 * StringUtil.substring("abc", 4, 6)   = ""
	 * StringUtil.substring("abc", 2, 2)   = ""
	 * StringUtil.substring("abc", -2, -1) = "b"
	 * StringUtil.substring("abc", -4, 2)  = "ab"
	 *
	 * str 		-- �ַ���
	 * start 	-- ��ʼ����,���Ϊ����,��ʾ��β������
	 * end 		-- ��������(����),���Ϊ����,��ʾ��β������
	 *
	 * �Ӵ�,���ԭʼ��ΪNULL,�򷵻�NULL
	 ***********************************************************************/
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		if (end < 0) {
			end = str.length() + end;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return EMPTY_STRING;
		}

		if (start < 0) {
			start = 0;
		}

		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/***********************************************************************
	 * ����ַ������Ƿ����ָ�����ַ���������ַ���Ϊ<code>null</code>��������<code>false</code>��
	 * 
	 * <pre>
	 * StringUtil.contains(null, *)     = false
	 * StringUtil.contains(*, null)     = false
	 * StringUtil.contains("", "")      = true
	 * StringUtil.contains("abc", "")   = true
	 * StringUtil.contains("abc", "a")  = true
	 * StringUtil.contains("abc", "z")  = false
	 * </pre>
	 *
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchStr
	 *            Ҫ���ҵ��ַ���
	 *
	 * @return ����ҵ����򷵻�<code>true</code>
	 ***********************************************************************/
	public static boolean contains(String str, String searchStr) {
		if ((str == null) || (searchStr == null)) {
			return false;
		}

		return str.indexOf(searchStr) >= 0;
	}

	/***********************************************************************
	 * checks if the String contains only unicode digits. a decimal point is 
	 * not a unicode digit and returns false.
	 * 
	 * null will return false. an empty String ("") will return true.
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric("")     = true
	 * StringUtils.isNumeric("  ")   = false
	 * StringUtils.isNumeric("123")  = true
	 * StringUtils.isNumeric("12 3") = false
	 * StringUtils.isNumeric("ab2c") = false
	 * StringUtils.isNumeric("12-3") = false
	 * StringUtils.isNumeric("12.3") = false
	 * 
	 * str -- the String to check, may be null
	 * true if only contains digits, and is non-null
	 ***********************************************************************/
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static String assemble(char sep, Object... object) {//�ַ���ƴ��
		if (object == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (Object obj : object) {
			if (obj == null) {
				obj = "";
			}
			sb.append(obj.toString()).append(sep);
		}
		String str = "";
		if (sb.length() > 0) {
			str = sb.substring(0, sb.length() - 1);
		}
		return str;
	}

	private static String regex = "^[A-Za-z0-9]$";//6-16����ĸ���������

	public static boolean checkStringLegal(String user) {//����ַ����Ƿ���Ϲ���(6-16����ĸ���������,��Сд������)
		boolean isMatch = true;
		char[] userChars = user.toCharArray();
		for (char c : userChars) {
			isMatch = String.valueOf(c).matches(regex);
			if (!isMatch) {
				break;
			}
		}
		return isMatch;
	}

	public static String getString(String input) {
		return getString(input, true, "");
	}

	public static String getString(String input, boolean btrim, String dval) {
		if (input == null) {
			return dval;
		}
		try {
			if (btrim) {
				return trim(input);
			} else {
				return input;
			}
		} catch (Exception e) {
			return "";
		}
	}

	public static String Trim(String str) {
		return trim(str);
	}

	public static String[] Trim(String[] s) {
		return trim(s);
	}

	public static String trim(String str) {
		if (str == null)
			return "";
		else
			return str.trim();
	}

	public static String[] trim(String[] s) {
		if (s == null || s.length <= 0)
			return s;
		for (int i = 0; i < s.length; i++)
			s[i] = trim(s[i]);
		return s;
	}

	public static int getInt(String input, boolean btrim, int dval) {
		if (input == null) {
			return dval;
		}
		int val;
		try {
			String str = new String(input);
			if (btrim) {
				str = trim(str);
			}
			val = Integer.parseInt(str);
		} catch (Exception e) {
			val = dval;
		}
		return val;
	}

	public static int[] getInts(String input) {
		return getInts(input, ",");
	}

	public static int[] getInts(String input, String split) {
		
		if (input == null) {
			return null;
		}

		String[] ss = input.split(split);
		
		int[] ii = new int[ss.length];
		
		for (int i = 0; i < ii.length; i++) {
			ii[i] = getInt(ss[i]);
		}
		
		return ii;
	}

	public static int getInt(String input) {
		return getInt(input, true, 0);
	}
}

