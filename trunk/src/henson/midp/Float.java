/*
 * $Id$
 * $URL$
 * $Author$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 *
 * Copyright (C) 2006-2007 by JVNGIS
 *
 * All copyright notices regarding JVNMobileGIS MUST remain
 * intact in the Java codes and resource files.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Support can be obtained from project homepage at:
 * http://jvnmobilegis.googlecode.com/
 *
 * Correspondence and Marketing Questions can be sent to:
 * khanh.lnq AT gmail.com
 * 
 * @version: 1.0
 * @author: Khanh Le
 * @Date Created: 22 Jun 2007
 */

package henson.midp;

/**
 * <p>
 * Title: Class for float-point calculations in J2ME applications (MIDP 1.0/CLDC
 * 1.0 where float or double types are not available)
 * </p>
 * <p>
 * Description: It makes float-point calculations via integer numbers
 * </p>
 * <p>
 * Copyright: Nikolay Klimchuk Copyright (c) 2002-2005
 * </p>
 * <p>
 * Company: UNTEH
 * </p>
 * <p>
 * License: Free for use only for non-commercial purpose
 * </p>
 * <p>
 * If you want to use all or part of this class for commercial applications then
 * take into account these conditions:
 * </p>
 * <p>
 * 1. I need a one copy of your product which includes my class with license key
 * and so on
 * </p>
 * <p>
 * 2. Please append my copyright information henson.midp.Float (C) by Nikolay
 * Klimchuk on �About� screen of your product
 * </p>
 * <p>
 * 3. If you have web site please append link <a
 * href=�http://henson.newmail.ru�>Nikolay Klimchuk</a> on the page with
 * description of your product
 * </p>
 * <p>
 * That's all, thank you!
 * </p>
 * 
 * @author Nikolay Klimchuk http://henson.newmail.ru
 * @version 1.01
 */

public class Float {

    /**
     * ERROR constant
     */
    final static private Float ERROR = new Float(Long.MAX_VALUE, Long.MAX_VALUE);
    /**
     * Number of itterations in sqrt method, if you want to make calculations
     * more precise set ITNUM=6,7,... or ITMUN=4,3,... to make it faster
     */
    final static private int ITNUM = 5;
    /**
     * Square root from 3
     */
    private final static Float SQRT3 = new Float(1732050807568877294L, -18L);
    /**
     * The Float value that is closer than any other to pi, the ratio of the
     * circumference of a circle to its diameter
     */
    private final static Float PI = new Float(3141592653589793238L, -18L);
    /**
     * Zero constant
     */
    private final static Float ZERO = new Float();
    /**
     * One constant
     */
    private final static Float ONE = new Float(1L);
    /**
     * The Float value that is closer than any other to e, the base of the
     * natural logarithms
     */
    final static public Float E = new Float(271828182845904512L, -17);
    /**
     * Log10 constant
     */
    private final static Float LOG10 = new Float(2302585092994045684L, -18);
    //
    /**
     * Pi/2 constant
     */
    private final static Float PIdiv2 = PI.Div(2L);
    /**
     * Pi/4 constant
     */
    final static public Float PIdiv4 = PIdiv2.Div(2L);
    /**
     * Pi/6 constant
     */
    private final static Float PIdiv6 = PIdiv2.Div(3L);
    /**
     * Pi/12 constant
     */
    private final static Float PIdiv12 = PIdiv6.Div(2L);
    /**
     * Pi*2 constant
     */
    private final static Float PImul2 = PI.Mul(2L);
    /**
     * Pi*4 constant
     */
    final static public Float PImul4 = PI.Mul(4L);
    /**
     * ln(0.5) constant
     */
    private final static Float LOGdiv2 = new Float(-6931471805599453094L, -19);
    /**
     * Mantissa
     */
    private long m_Val;
    /**
     * Exponent
     */
    private long m_E;
    /**
     * Limit of value
     */
    private long maxLimit = Long.MAX_VALUE / 100;

    //
    /**
     * Create object with zero inside
     */
    private Float() {
        m_Val = m_E = 0;
    }

    /**
     * Create object and makes initialization with value
     * 
     * @param value
     *            long - value
     */
    public Float(long value) {
        m_Val = value;
        m_E = 0;
    }

    /**
     * Create object and makes initialization both mantissa and exponent
     * 
     * @param value
     *            long - mantissa
     * @param e
     *            long - exponent
     */
    private Float(long value, long e) {
        m_Val = value;
        if (m_Val == 0)
            m_E = 0;
        else
            m_E = e;
    }

    /**
     * Create object and makes initialization by other Float object
     * 
     * @param value
     *            Float - source object
     */
    public Float(Float value) {
        m_Val = value.m_Val;
        if (m_Val == 0)
            m_E = 0;
        else
            m_E = value.m_E;
    }

    /**
     * Convert Float object to long number
     * 
     * @return long - number
     */
    public long toLong() {
        long tmpE = m_E;
        long tmpVal = m_Val;
        //
        while (tmpE != 0) {
            if (tmpE < 0) {
                tmpVal /= 10;
                tmpE++;
            } else {
                tmpVal *= 10;
                tmpE--;
            }
        }
        return tmpVal;
    }

    /**
     * Convert Float object to string without exponent
     * 
     * @return String - string
     */
    public String toShortString() {
        if (isError())
            return "NaN";
        //
        StringBuffer sb = new StringBuffer();
        sb.append(m_Val);
        int len = (int) m_E;
        if (len > 0) {
            for (int k = 0; k < len; k++)
                sb.append("0");
            len = 0;
        }
        //
        String str = sb.toString();
        len += str.length();
        //
        if (m_Val < 0L) {
            if (len > 1)
                return str.substring(0, len);
        } else {
            if (len > 0)
                return str.substring(0, len);
        }
        //
        return "0";
    }

    /**
     * Check value of current Float object is NaN
     * 
     * @return boolean - true-if NaN, false-if not
     */
    private boolean isError() {
        return (this.m_Val == ERROR.m_Val && this.m_E == ERROR.m_E);
    }

    /**
     * Convert Float object to string
     */
    public String toString() {
        if (isError())
            return "NaN";
        //
        RemoveZero();
        //
        Long l = new Long(m_Val);
        String str = l.toString();
        int len = str.length();
        boolean neg = false;
        if (m_Val < 0L) {
            neg = true;
            str = str.substring(1, len);
            len--;
        }
        //
        StringBuffer sb = new StringBuffer();
        //
        if (m_E < 0L) {
            int absE = (int) Math.abs(m_E);
            if (absE < len) {
                sb.append(str.substring(0, len - absE));
                sb.append(".");
                sb.append(str.substring(len - absE));
            } else {
                sb.append(str);
                for (int i = 0; i < (absE - len); i++)
                    sb.insert(0, "0");
                sb.insert(0, "0.");
            }
        } else {
            if (len + m_E > 6) {
                sb.append(str.charAt(0));
                if (str.length() > 1) {
                    sb.append(".");
                    sb.append(str.substring(1));
                } else
                    sb.append(".0");
                sb.append("E").append(len - 1 + m_E);
            } else {
                sb.append(str);
                for (int i = 0; i < m_E; i++)
                    sb.append("0");
            }
        }
        //
        str = sb.toString();
        sb = null;
        if (neg)
            str = "-" + str;
        //
        return str;
    }

    /**
     * Append value of argument to value of current Float object and return as
     * new Float object
     * 
     * @param value
     *            Float - argument
     * @return Float - current+value
     */
    public Float Add(Float value) {
        if (value.Equal(ZERO))
            return new Float(this);
        //
        long e1 = m_E;
        long e2 = value.m_E;
        long v1 = m_Val;
        long v2 = value.m_Val;
        // E must be equal in both operators
        while (e1 != e2) {
            if (e1 > e2) {
                if (Math.abs(v1) < maxLimit) {
                    v1 *= 10;
                    e1--;
                } else {
                    v2 /= 10;
                    e2++;
                }
            } else if (e1 < e2) {
                if (Math.abs(v2) < maxLimit) {
                    v2 *= 10;
                    e2--;
                } else {
                    v1 /= 10;
                    e1++;
                }
            }
        }
        //
        if ((v1 > 0 && v2 > Long.MAX_VALUE - v1)
                || (v1 < 0 && v2 < Long.MIN_VALUE - v1)) {
            v1 /= 10;
            e1++;
            v2 /= 10;
            e2++;
        }
        //
        if (v1 > 0 && v2 > Long.MAX_VALUE - v1)
            return new Float(ERROR);
        else if (v1 < 0 && v2 < Long.MIN_VALUE - v1)
            return new Float(ERROR);
        //
        return new Float(v1 + v2, e1);
    }

    /**
     * Subtract value of argument from value of current Float object and return
     * as new Float object
     * 
     * @param value
     *            Float - argument
     * @return Float - current-value
     */
    public Float Sub(Float value) {
        if (value.Equal(ZERO))
            return new Float(m_Val, m_E);
        return Add(new Float(-value.m_Val, value.m_E));
    }

    /**
     * Divide value of current Float object on argument and return as new Float
     * object
     * 
     * @param value
     *            Float - argument
     * @return Float - current/value
     */
    public Float Mul(long value) {
        return Mul(new Float(value, 0));
    }

    /**
     * Multiply value of current Float object on argument and return as new
     * Float object
     * 
     * @param value
     *            Float - argument
     * @return Float - current*value
     */
    public Float Mul(Float value) {
        if (value.Equal(ZERO) || this.Equal(ZERO))
            return new Float(ZERO);
        if (value.Equal(ONE))
            return new Float(this);
        //
        boolean negative1 = (m_Val < 0);
        if (negative1)
            m_Val = -m_Val;
        boolean negative2 = (value.m_Val < 0);
        if (negative2)
            value.m_Val = -value.m_Val;
        // Check overflow and underflow
        do {
            if (value.m_Val > m_Val) {
                if (Long.MAX_VALUE / m_Val < value.m_Val) {
                    value.m_Val /= 10;
                    value.m_E++;
                } else
                    break;
            } else {
                if (Long.MAX_VALUE / value.m_Val < m_Val) {
                    m_Val /= 10;
                    m_E++;
                } else
                    break;
            }
        } while (true);
        //
        if (negative1)
            m_Val = -m_Val;
        if (negative2)
            value.m_Val = -value.m_Val;
        //
        long e = m_E + value.m_E;
        long v = m_Val * value.m_Val;
        return new Float(v, e);
    }

    /**
     * Divide value of current Float object on argument and return as new Float
     * object
     * 
     * @param value
     *            Float - argument
     * @return Float - current/value
     */
    public Float Div(long value) {
        return Div(new Float(value, 0));
    }

    /**
     * Divide value of current Float object on argument and return as new Float
     * object
     * 
     * @param value
     *            Float - argument
     * @return Float - current/value
     */
    public Float Div(Float value) {
        if (value.Equal(ONE))
            return new Float(this);
        //
        long e1 = m_E;
        long e2 = value.m_E;
        long v2 = value.m_Val;
        if (v2 == 0L)
            return new Float(ERROR);
        long v1 = m_Val;
        if (v1 == 0L)
            return new Float(ZERO);
        //
        long val = 0L;
        while (true) {
            val += (v1 / v2);
            v1 %= v2;
            if (v1 == 0L || Math.abs(val) > (Long.MAX_VALUE / 10L))
                break;
            if (Math.abs(v1) > (Long.MAX_VALUE / 10L)) {
                v2 /= 10L;
                e2++;
            } else {
                v1 *= 10L;
                e1--;
            }
            val *= 10L;
        }
        //
        Float f = new Float(val, e1 - e2);
        f.RemoveZero();
        return f;
    }

    private void RemoveZero() {
        if (m_Val == 0)
            return;
        while (m_Val % 10 == 0) {
            m_Val /= 10;
            m_E++;
        }
    }

    /**
     * Is value of current Float object greater?
     * 
     * @param x
     *            Float - argument
     * @return boolean - true-if current value is greater x, false-if not
     */
    private boolean Great(Float x) {
        long e1 = m_E;
        long e2 = x.m_E;
        long v1 = m_Val;
        long v2 = x.m_Val;
        //
        while (e1 != e2) {
            if (e1 > e2) {
                if (Math.abs(v1) < maxLimit) {
                    v1 *= 10;
                    e1--;
                } else {
                    v2 /= 10;
                    e2++;
                }
            } else if (e1 < e2) {
                if (Math.abs(v2) < maxLimit) {
                    v2 *= 10;
                    e2--;
                } else {
                    v1 /= 10;
                    e1++;
                }
            }
        }
        //
        return v1 > v2;
    }

    /**
     * Is value of current Float object less?
     * 
     * @param x
     *            Float - argument
     * @return boolean - true-if current value is less x, false-if not
     */
    private boolean Less(long x) {
        return Less(new Float(x, 0));
    }

    /**
     * Is value of current Float object less?
     * 
     * @param x
     *            Float - argument
     * @return boolean - true-if current value is less x, false-if not
     */
    public boolean Less(Float x) {
        long e1 = m_E;
        long e2 = x.m_E;
        long v1 = m_Val;
        long v2 = x.m_Val;
        //
        while (e1 != e2) {
            if (e1 > e2) {
                if (Math.abs(v1) < maxLimit) {
                    v1 *= 10;
                    e1--;
                } else {
                    v2 /= 10;
                    e2++;
                }
            } else if (e1 < e2) {
                if (Math.abs(v2) < maxLimit) {
                    v2 *= 10;
                    e2--;
                } else {
                    v1 /= 10;
                    e1++;
                }
            }
        }
        //
        return v1 < v2;
    }

    /**
     * Equal with current Float object?
     * 
     * @param x
     *            Float - argument
     * @return boolean - true-if equal, false-if not
     */
    private boolean Equal(Float x) {
        long e1 = m_E;
        long e2 = x.m_E;
        long v1 = m_Val;
        long v2 = x.m_Val;
        //
        if ((v1 == 0 && v2 == 0) || (v1 == v2 && e1 == e2))
            return true;
        // Values with exponent differences more than 20 times never could be
        // equal
        /*
         * if(Math.abs(e1-e2)>20) return false;
         */
        long diff = e1 - e2;
        if (diff < -20 || diff > 20)
            return false;
        //
        while (e1 != e2) {
            if (e1 > e2) {
                if (Math.abs(v1) < maxLimit) {
                    v1 *= 10;
                    e1--;
                } else {
                    v2 /= 10;
                    e2++;
                }
            } else if (e1 < e2) {
                if (Math.abs(v2) < maxLimit) {
                    v2 *= 10;
                    e2--;
                } else {
                    v1 /= 10;
                    e1++;
                }
            }
        }
        //
        return (v1 == v2);
    }

    /**
     * Reverse sign of value in current Float object and return as new Float
     * object
     * 
     * @return Float - new Float object
     */
    private Float Neg() {
        return new Float(-m_Val, m_E);
    }

    /**
     * Returns the trigonometric sine of an angle. Special cases: If the
     * argument is NaN or an infinity, then the result is NaN. If the argument
     * is zero, then the result is a zero with the same sign as the argument. A
     * result must be within 1 ulp of the correctly rounded result. Results must
     * be semi-monotonic
     * 
     * @param x
     *            Float - an angle, in radians
     * @return Float - the sine of the argument
     */
    private static Float sin(Float x) {
        while (x.Great(PI))
            x = x.Sub(PImul2);
        while (x.Less(PI.Neg()))
            x = x.Add(PImul2);
        // x*x*x
        Float m1 = x.Mul(x.Mul(x));
        Float q1 = m1.Div(6L);
        // x*x*x*x*x
        Float m2 = x.Mul(x.Mul(m1));
        Float q2 = m2.Div(120L);
        // x*x*x*x*x*x*x
        Float m3 = x.Mul(x.Mul(m2));
        Float q3 = m3.Div(5040L);
        // x*x*x*x*x*x*x*x*x
        Float m4 = x.Mul(x.Mul(m3));
        Float q4 = m4.Div(362880L);
        // x*x*x*x*x*x*x*x*x*x*x
        Float m5 = x.Mul(x.Mul(m4));
        Float q5 = m5.Div(39916800L);
        //
        Float result = x.Sub(q1).Add(q2).Sub(q3).Add(q4).Sub(q5);
        // 1e-6
        if (result.Less(new Float(-999999, -6)))
            return new Float(-1L);
        // 1e-6
        if (result.Great(new Float(999999, -6)))
            return new Float(1L);
        // 5e-4
        if (result.Great(new Float(-5, -4)) && result.Less(new Float(5, -4)))
            return new Float(0L);
        //
        return result;
    }

    /**
     * Returns the trigonometric cosine of an angle. Special cases: If the
     * argument is NaN or an infinity, then the result is NaN. A result must be
     * within 1 ulp of the correctly rounded result. Results must be
     * semi-monotonic
     * 
     * @param x
     *            Float - an angle, in radians
     * @return Float - the cosine of the argument
     */
    private static Float cos(Float x) {
        while (x.Great(PI))
            x = x.Sub(PImul2);
        while (x.Less(PI.Neg()))
            x = x.Add(PImul2);
        // x*x
        Float m1 = x.Mul(x);
        Float q1 = m1.Div(2L);
        // x*x*x*x
        Float m2 = m1.Mul(m1);
        Float q2 = m2.Div(24L);
        // x*x*x*x*x*x
        Float m3 = m1.Mul(m2);
        Float q3 = m3.Div(720L);
        // x*x*x*x*x*x*x*x
        Float m4 = m2.Mul(m2);
        Float q4 = m4.Div(40320L);
        // x*x*x*x*x*x*x*x*x*x
        Float m5 = m4.Mul(m1);
        Float q5 = m5.Div(3628800L);
        Float result = ONE.Sub(q1).Add(q2).Sub(q3).Add(q4).Sub(q5);
        // 1e-6
        if (result.Less(new Float(-999999, -6)))
            return new Float(-1L);
        // 1e-6
        if (result.Great(new Float(999999, -6)))
            return new Float(1L);
        // 5e-4
        if (result.Great(new Float(-5, -4)) && result.Less(new Float(5, -4)))
            return new Float(0L);
        //
        return result;
    }

    /**
     * Returns the correctly rounded positive square root of a double value.
     * Special cases: If the argument is NaN or less than zero, then the result
     * is NaN. If the argument is positive infinity, then the result is positive
     * infinity. If the argument is positive zero or negative zero, then the
     * result is the same as the argument. Otherwise, the result is the double
     * value closest to the true mathematical square root of the argument value
     * 
     * @param x
     *            Float - a value
     * @return Float - the positive square root of a. If the argument is NaN or
     *         less than zero, the result is NaN
     */
    private static Float sqrt(Float x) {
        int sp = 0;
        boolean inv = false;
        Float a, b;
        //
        if (x.Less(ZERO))
            return new Float(ERROR);
        if (x.Equal(ZERO))
            return new Float(ZERO);
        if (x.Equal(ONE))
            return new Float(ONE);
        // argument less than 1 : invert it
        if (x.Less(ONE)) {
            x = ONE.Div(x);
            inv = true;
        }
        //
        long e = x.m_E / 2;
        // exponent compensation
        Float tmp = new Float(x.m_Val, x.m_E - e * 2);
        // process series of division by 16 until argument is <16
        while (tmp.Great(new Float(16L))) {
            sp++;
            tmp = tmp.Div(16L);
        }
        // initial approximation
        a = new Float(2L);
        // Newtonian algorithm
        for (int i = ITNUM; i > 0; i--) {
            b = tmp.Div(a);
            a = a.Add(b);
            a = a.Div(2L);
        }
        // multiply result by 4 : as much times as divisions by 16 took place
        while (sp > 0) {
            sp--;
            a = a.Mul(4L);
        }
        // exponent compensation
        a.m_E += e;
        // invert result for inverted argument
        if (inv)
            a = ONE.Div(a);
        return a;
    }

    /**
     * Returns the trigonometric tangent of an angle. Special cases: If the
     * argument is NaN or an infinity, then the result is NaN. If the argument
     * is zero, then the result is a zero with the same sign as the argument. A
     * result must be within 1 ulp of the correctly rounded result. Results must
     * be semi-monotonic
     * 
     * @param x
     *            Float - an angle, in radians
     * @return Float - the tangent of the argument
     */
    static public Float tan(Float x) {
        Float c = cos(x);
        if (c.Equal(ZERO))
            return new Float(ERROR);
        return (sin(x).Div(c));
    }

    /**
     * Returns a new Float object initialized to the value represented by the
     * specified String
     * 
     * @param str
     *            String - the string to be parsed
     * @param radix
     *            int - basement of number
     * @return Float - the Float object represented by the string argument
     */
    static public Float parse(String str, int radix) {
        // Abs
        boolean neg = false;
        if (str.charAt(0) == '-') {
            str = str.substring(1);
            neg = true;
        }
        //
        int pos = str.indexOf(".");
        long exp = 0;
        // Find exponent position
        int pos2 = str.indexOf('E');
        if (pos2 == -1)
            pos2 = str.indexOf('e');
        //
        if (pos2 != -1) {
            String tmp = str.substring(pos2 + 1);
            exp = Long.parseLong(tmp);
            str = str.substring(0, pos2);
        }
        //
        if (pos != -1) {
            for (int m = pos + 1; m < str.length(); m++) {
                if (Character.isDigit(str.charAt(m)))
                    exp--;
                else
                    break;
            }
            str = str.substring(0, pos) + str.substring(pos + 1);
            while (str.length() > 1 && str.charAt(0) == '0'
                    && str.charAt(1) != '.')
                str = str.substring(1);
        }
        //
        long result = 0L;
        int len = str.length();
        //
        StringBuffer sb = new StringBuffer(str);
        while (true) {
            // Long value can't have length more than 20
            while (len > 20) {
                // Very large number for Long
                sb = sb.deleteCharAt(len - 1);
                // Compensation of removed zeros
                if (len < pos || pos == -1)
                    exp++;
                //
                len--;
            }
            //
            try {
                result = Long.parseLong(sb.toString(), radix);
                if (neg)
                    result = -result;
                break;
            } catch (Exception e) {
                // Very large number for Long
                sb = sb.deleteCharAt(len - 1);
                // Compensation of removed zeros
                if (len < pos || pos == -1)
                    exp++;
                //
                len--;
            }
        }
        sb = null;
        //
        Float newValue = new Float(result, exp);
        newValue.RemoveZero();
        return newValue;
    }

    /**
     * Returns the arc cosine of an angle, in the range of 0.0 through pi.
     * Special case: If the argument is NaN or its absolute value is greater
     * than 1, then the result is NaN. A result must be within 1 ulp of the
     * correctly rounded result. Results must be semi-monotonic
     * 
     * @param x
     *            Float - the value whose arc cosine is to be returned
     * @return Float - the arc cosine of the argument
     */
    static public Float acos(Float x) {
        Float f = asin(x);
        if (f.isError())
            return f;
        return PIdiv2.Sub(f);
    }

    /**
     * Returns the arc sine of an angle, in the range of -pi/2 through pi/2.
     * Special cases: If the argument is NaN or its absolute value is greater
     * than 1, then the result is NaN. If the argument is zero, then the result
     * is a zero with the same sign as the argument. A result must be within 1
     * ulp of the correctly rounded result. Results must be semi-monotonic
     * 
     * @param x
     *            Float - the value whose arc sine is to be returned
     * @return Float - the arc sine of the argument
     */
    private static Float asin(Float x) {
        if (x.Less(ONE.Neg()) || x.Great(ONE))
            return new Float(ERROR);
        if (x.Equal(ONE.Neg()))
            return PIdiv2.Neg();
        if (x.Equal(ONE))
            return PIdiv2;
        return atan(x.Div(sqrt(ONE.Sub(x.Mul(x)))));
    }

    /**
     * Returns the arc tangent of an angle, in the range of -pi/2 through pi/2.
     * Special cases: If the argument is NaN, then the result is NaN. If the
     * argument is zero, then the result is a zero with the same sign as the
     * argument. A result must be within 1 ulp of the correctly rounded result.
     * Results must be semi-monotonic
     * 
     * @param x
     *            Float - the value whose arc tangent is to be returned
     * @return Float - the arc tangent of the argument
     */
    private static Float atan(Float x) {
        boolean signChange = false;
        boolean Invert = false;
        int sp = 0;
        Float x2, a;
        // check up the sign change
        if (x.Less(ZERO)) {
            x = x.Neg();
            signChange = true;
        }
        // check up the invertation
        if (x.Great(ONE)) {
            x = ONE.Div(x);
            Invert = true;
        }
        // process shrinking the domain until x<PI/12
        while (x.Great(PIdiv12)) {
            sp++;
            a = x.Add(SQRT3);
            a = ONE.Div(a);
            x = x.Mul(SQRT3);
            x = x.Sub(ONE);
            x = x.Mul(a);
        }
        // calculation core
        x2 = x.Mul(x);
        a = x2.Add(new Float(14087812, -7));
        a = new Float(55913709, -8).Div(a);
        a = a.Add(new Float(60310579, -8));
        a = a.Sub(x2.Mul(new Float(5160454, -8)));
        a = a.Mul(x);
        // process until sp=0
        while (sp > 0) {
            a = a.Add(PIdiv6);
            sp--;
        }
        // invertation took place
        if (Invert)
            a = PIdiv2.Sub(a);
        // sign change took place
        if (signChange)
            a = a.Neg();
        //
        return a;
    }

    /**
     * Converts rectangular coordinates (x,�y) to polar (r,�theta). This method
     * computes the phase theta by computing an arc tangent of y/x in the range
     * of -pi to pi. Special cases: If either argument is NaN, then the result
     * is NaN. If the first argument is positive zero and the second argument is
     * positive, or the first argument is positive and finite and the second
     * argument is positive infinity, then the result is positive zero. If the
     * first argument is negative zero and the second argument is positive, or
     * the first argument is negative and finite and the second argument is
     * positive infinity, then the result is negative zero. If the first
     * argument is positive zero and the second argument is negative, or the
     * first argument is positive and finite and the second argument is negative
     * infinity, then the result is the double value closest to pi. If the first
     * argument is negative zero and the second argument is negative, or the
     * first argument is negative and finite and the second argument is negative
     * infinity, then the result is the double value closest to -pi. If the
     * first argument is positive and the second argument is positive zero or
     * negative zero, or the first argument is positive infinity and the second
     * argument is finite, then the result is the double value closest to pi/2.
     * If the first argument is negative and the second argument is positive
     * zero or negative zero, or the first argument is negative infinity and the
     * second argument is finite, then the result is the double value closest to
     * -pi/2. If both arguments are positive infinity, then the result is the
     * double value closest to pi/4. If the first argument is positive infinity
     * and the second argument is negative infinity, then the result is the
     * double value closest to 3*pi/4. If the first argument is negative
     * infinity and the second argument is positive infinity, then the result is
     * the double value closest to -pi/4. If both arguments are negative
     * infinity, then the result is the double value closest to -3*pi/4. A
     * result must be within 2 ulps of the correctly rounded result. Results
     * must be semi-monotonic
     * 
     * @param y
     *            Float - the ordinate coordinate
     * @param x
     *            Float - the abscissa coordinate
     * @return Float - the theta component of the point (r,�theta) in polar
     *         coordinates that corresponds to the point (x,�y) in Cartesian
     *         coordinates
     */
    static public Float atan2(Float y, Float x) {
        // if x=y=0
        if (y.Equal(ZERO) && x.Equal(ZERO))
            return new Float(ZERO);
        // if x>0 atan(y/x)
        if (x.Great(ZERO))
            return atan(y.Div(x));
        // if x<0 sign(y)*(pi - atan(|y/x|))
        if (x.Less(ZERO)) {
            if (y.Less(ZERO))
                return Float.PI.Sub(atan(y.Div(x))).Neg();
            else
                return Float.PI.Sub(atan(y.Div(x).Neg()));
        }
        // if x=0 y!=0 sign(y)*pi/2
        if (y.Less(ZERO))
            return PIdiv2.Neg();
        else
            return new Float(PIdiv2);
    }

    // precise
    // x=-35 diff=1.48%
    // x=-30 diff=0.09%
    // x=30 diff=0.09%
    // x=31 diff=0.17%
    // x=32 diff=0.31%
    // x=33 diff=0.54%
    // x=34 diff=0.91%
    // x=35 diff=1.46%
    /**
     * Returns Euler's number e raised to the power of a double value. Special
     * cases: If the argument is NaN, the result is NaN. If the argument is
     * positive infinity, then the result is positive infinity. If the argument
     * is negative infinity, then the result is positive zero. A result must be
     * within 1 ulp of the correctly rounded result. Results must be
     * semi-monotonic
     * 
     * @param x
     *            Float - the exponent to raise e to
     * @return Float - the value e^x, where e is the base of the natural
     *         logarithms
     */
    private static Float exp(Float x) {
        if (x.Equal(ZERO))
            return new Float(ONE);
        //
        Float f = new Float(ONE);
        long d = 1;
        Float k = null;
        boolean isless = x.Less(ZERO);
        if (isless)
            x = x.Neg();
        k = new Float(x).Div(d);
        //
        for (long i = 2; i < 50; i++) {
            f = f.Add(k);
            k = k.Mul(x).Div(i);
        }
        //
        if (isless)
            return ONE.Div(f);
        else
            return f;
    }

    // precise
    // x=25 diff=0.12%
    // x=30 diff=0.25%
    // x=35 diff=0.44%
    // x=40 diff=0.67%
    /**
     * Internal log subroutine
     * 
     * @param x
     *            Float
     * @return Float
     */
    static private Float _log(Float x) {
        if (!x.Great(ZERO))
            return new Float(ERROR);
        //
        Float f = new Float(ZERO);
        // Make x to close at 1
        int appendix = 0;
        while (x.Great(ZERO) && x.Less(ONE)) {
            x = x.Mul(2);
            appendix++;
        }
        //
        x = x.Div(2);
        appendix--;
        //
        Float y1 = x.Sub(ONE);
        Float y2 = x.Add(ONE);
        Float y = y1.Div(y2);
        //
        Float k = new Float(y);
        y2 = k.Mul(y);
        //
        for (long i = 1; i < 50; i += 2) {
            f = f.Add(k.Div(i));
            k = k.Mul(y2);
        }
        //
        f = f.Mul(2);
        for (int i = 0; i < appendix; i++)
            f = f.Add(LOGdiv2);
        //
        return f;
    }

    /**
     * Returns the natural logarithm (base e) of a double value. Special cases:
     * If the argument is NaN or less than zero, then the result is NaN. If the
     * argument is positive infinity, then the result is positive infinity. If
     * the argument is positive zero or negative zero, then the result is
     * negative infinity. A result must be within 1 ulp of the correctly rounded
     * result. Results must be semi-monotonic
     * 
     * @param x
     *            Float - a number greater than 0.0
     * @return Float - the value ln(x), the natural logarithm of x
     */
    private static Float log(Float x) {
        if (!x.Great(ZERO))
            return new Float(ERROR);
        //
        if (x.Equal(ONE))
            return new Float(ZERO);
        //
        if (x.Great(Float.ONE)) {
            x = ONE.Div(x);
            return _log(x).Neg();
        }
        return _log(x);
    }

    static public Float log10(Float x) {
        if (!x.Great(ZERO))
            return new Float(ERROR);
        //
        if (x.Equal(ONE))
            return new Float(ZERO);
        //
        Float f = log(x);
        if (f.isError())
            return f;
        return f.Div(LOG10);
    }

    /*
     * static public Float log10(Float x) { if(!x.Great(ZERO)) return new
     * Float(ERROR); // boolean neg=false; if(x.m_Val<0) { neg=true;
     * x.m_Val=-x.m_Val; } // int index=0; if(x.Great(Float.ONE)) { // ������ 1
     * while(x.Great(Float.ONE)) { x=x.Div(10); index++; } } else { // ������
     * ��� ����� 1 while(x.Less(Float.ONE)) { x=x.Mul(10); index--; } } // Float
     * res=new Float(index); if(!x.Equal(ONE)) res=res.Add(log(x).Div(LOG10)); //
     * if(neg) return Float.ONE.Div(res); else return res; }
     */

    // precise y=3.5
    // x=15 diff=0.06%
    // x=20 diff=0.40%
    // x=25 diff=1.31%
    // x=30 diff=2.95%
    // if x negative y must be integer value
    /**
     * Returns the value of the first argument raised to the power of the second
     * argument. Special cases: If the second argument is positive or negative
     * zero, then the result is 1.0. If the second argument is 1.0, then the
     * result is the same as the first argument. If the second argument is NaN,
     * then the result is NaN. If the first argument is NaN and the second
     * argument is nonzero, then the result is NaN. If the absolute value of the
     * first argument is greater than 1 and the second argument is positive
     * infinity, or the absolute value of the first argument is less than 1 and
     * the second argument is negative infinity, then the result is positive
     * infinity. If the absolute value of the first argument is greater than 1
     * and the second argument is negative infinity, or the absolute value of
     * the first argument is less than 1 and the second argument is positive
     * infinity, then the result is positive zero. If the absolute value of the
     * first argument equals 1 and the second argument is infinite, then the
     * result is NaN. If the first argument is positive zero and the second
     * argument is greater than zero, or the first argument is positive infinity
     * and the second argument is less than zero, then the result is positive
     * zero. If the first argument is positive zero and the second argument is
     * less than zero, or the first argument is positive infinity and the second
     * argument is greater than zero, then the result is positive infinity. If
     * the first argument is negative zero and the second argument is greater
     * than zero but not a finite odd integer, or the first argument is negative
     * infinity and the second argument is less than zero but not a finite odd
     * integer, then the result is positive zero. If the first argument is
     * negative zero and the second argument is a positive finite odd integer,
     * or the first argument is negative infinity and the second argument is a
     * negative finite odd integer, then the result is negative zero. If the
     * first argument is negative zero and the second argument is less than zero
     * but not a finite odd integer, or the first argument is negative infinity
     * and the second argument is greater than zero but not a finite odd
     * integer, then the result is positive infinity. If the first argument is
     * negative zero and the second argument is a negative finite odd integer,
     * or the first argument is negative infinity and the second argument is a
     * positive finite odd integer, then the result is negative infinity. If the
     * first argument is finite and less than zero if the second argument is a
     * finite even integer, the result is equal to the result of raising the
     * absolute value of the first argument to the power of the second argument
     * if the second argument is a finite odd integer, the result is equal to
     * the negative of the result of raising the absolute value of the first
     * argument to the power of the second argument if the second argument is
     * finite and not an integer, then the result is NaN. If both arguments are
     * integers, then the result is exactly equal to the mathematical result of
     * raising the first argument to the power of the second argument if that
     * result can in fact be represented exactly as a double value. (In the
     * foregoing descriptions, a floating-point value is considered to be an
     * integer if and only if it is finite and a fixed point of the method ceil
     * or, equivalently, a fixed point of the method floor. A value is a fixed
     * point of a one-argument method if and only if the result of applying the
     * method to the value is equal to the value.) A result must be within 1 ulp
     * of the correctly rounded result. Results must be semi-monotonic.
     * 
     * @param x
     *            Float - the base
     * @param y
     *            Float - the exponent
     * @return Float - the value a^b
     */
    static public Float pow(Float x, Float y) {
        if (x.Equal(ZERO))
            return new Float(ZERO);
        if (x.Equal(ONE))
            return new Float(ONE);
        if (y.Equal(ZERO))
            return new Float(ONE);
        if (y.Equal(ONE))
            return new Float(x);
        //
        long l = y.toLong();
        boolean integerValue = y.Equal(new Float(l));
        //
        if (integerValue) {
            boolean neg = false;
            if (y.Less(0))
                neg = true;
            //
            Float result = new Float(x);
            for (long i = 1; i < (neg ? -l : l); i++)
                result = result.Mul(x);
            //
            if (neg)
                return ONE.Div(result);
            else
                return result;
        } else {
            if (x.Great(ZERO))
                return exp(y.Mul(log(x)));
            else
                return new Float(ERROR);
        }
    }

    /**
     * Returns the smallest (closest to negative infinity) double value that is
     * not less than the argument and is equal to a mathematical integer.
     * Special cases: If the argument value is already equal to a mathematical
     * integer, then the result is the same as the argument. If the argument is
     * NaN or an infinity or positive zero or negative zero, then the result is
     * the same as the argument. If the argument value is less than zero but
     * greater than -1.0, then the result is negative zero
     * 
     * @param x
     *            Float - a value
     * @return Float - the smallest (closest to negative infinity)
     *         floating-point value that is not less than the argument and is
     *         equal to a mathematical integer
     */
    static public Float ceil(Float x) {
        long tmpVal = x.m_Val;
        //
        if (x.m_E < 0) {
            long coeff = 1;
            //
            if (x.m_E > -19) {
                for (long i = 0; i < -x.m_E; i++)
                    coeff *= 10;
                tmpVal /= coeff;
                tmpVal *= coeff;
                if (x.m_Val - tmpVal > 0)
                    tmpVal += coeff;
            } else if (tmpVal > 0)
                return ONE;
            else
                return ZERO;
        }
        //
        return new Float(tmpVal, x.m_E);
    }

    /**
     * Returns the largest (closest to positive infinity) double value that is
     * not greater than the argument and is equal to a mathematical integer.
     * Special cases: If the argument value is already equal to a mathematical
     * integer, then the result is the same as the argument. If the argument is
     * NaN or an infinity or positive zero or negative zero, then the result is
     * the same as the argument
     * 
     * @param x
     *            Float - a value
     * @return Float - the largest (closest to positive infinity) floating-point
     *         value that is not greater than the argument and is equal to a
     *         mathematical integer
     */
    static public Float floor(Float x) {
        long tmpVal = x.m_Val;
        //
        if (x.m_E < 0) {
            long coeff = 1;
            //
            if (x.m_E > -19) {
                for (long i = 0; i < -x.m_E; i++)
                    coeff *= 10;
                tmpVal /= coeff;
                tmpVal *= coeff;
                if (x.m_Val - tmpVal < 0)
                    tmpVal -= coeff;
            } else if (tmpVal < 0)
                return ONE.Neg();
            else
                return ZERO;
        }
        //
        return new Float(tmpVal, x.m_E);
    }

    /**
     * Returns the absolute value of a Float object. If the argument is not
     * negative, the argument is returned. If the argument is negative, the
     * negation of the argument is returned. Special cases: If the argument is
     * positive zero or negative zero, the result is positive zero. If the
     * argument is infinite, the result is positive infinity. If the argument is
     * NaN, the result is NaN
     * 
     * @param x
     *            Float - the argument whose absolute value is to be determined
     * @return Float - the absolute value of the argument
     */
    static public Float abs(Float x) {
        if (x.m_Val < 0)
            return x.Neg();
        return new Float(x);
    }

    /**
     * Integer part of Float object
     * 
     * @param x
     *            Float - source Float object
     * @return Float - result Float object
     */
    private static Float Int(Float x) {
        long tmpVal = x.m_Val;
        //
        if (x.m_E < 0) {
            long coeff = 1;
            //
            if (x.m_E > -19) {
                for (long i = 0; i < -x.m_E; i++)
                    coeff *= 10;
                tmpVal /= coeff;
                tmpVal *= coeff;
            } else
                return Float.ZERO;
        }
        //
        return new Float(tmpVal, x.m_E);
    }

    /**
     * Fractional part of Float object
     * 
     * @param x
     *            Float - source Float object
     * @return Float - result Float object
     */
    static public Float Frac(Float x) {
        return x.Sub(Int(x));
    }

    /**
     * Converts an angle measured in degrees to an approximately equivalent
     * angle measured in radians. The conversion from degrees to radians is
     * generally inexact
     * 
     * @param x
     *            Float - an angle, in degrees
     * @return Float - the measurement of the angle x in radians
     */
    static public Float toRadians(Float x) {
        return x.Mul(PI).Div(180L);
    }

    /**
     * Converts an angle measured in radians to an approximately equivalent
     * angle measured in degrees. The conversion from radians to degrees is
     * generally inexact; users should not expect cos(toRadians(90.0)) to
     * exactly equal 0.0
     * 
     * @param x
     *            Float - an angle, in radians
     * @return Float - the measurement of the angle angrad in degrees
     */
    static public Float toDegrees(Float x) {
        return x.Mul(180L).Div(PI);
    }
}
