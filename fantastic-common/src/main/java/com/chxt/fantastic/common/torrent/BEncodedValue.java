package com.chxt.fantastic.common.torrent;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BEncodedValue {

    /**
     * The B-encoded value can be a byte array, a Number, a List or a Map.
     * Lists and Maps contains BEValues too.
     */
    private final Object value;

    public BEncodedValue(byte[] value) {
        this.value = value;
    }

    public BEncodedValue(String value) throws UnsupportedEncodingException {
        this.value = value.getBytes("UTF-8");
    }

    public BEncodedValue(String value, String enc)
            throws UnsupportedEncodingException {
        this.value = value.getBytes(enc);
    }

    public BEncodedValue(int value) {
        this.value = new Integer(value);
    }

    public BEncodedValue(long value) {
        this.value = new Long(value);
    }

    public BEncodedValue(Number value) {
        this.value = value;
    }

    public BEncodedValue(List<BEncodedValue> value) {
        this.value = value;
    }

    public BEncodedValue(Map<String, BEncodedValue> value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    /**
     * Returns this BEValue as a String, interpreted as UTF-8.
     * @throws InvalidBEncodingException If the value is not a byte[].
     */
    public String getString() throws InvalidBEncodingException {
        return this.getString("UTF-8");
    }

    /**
     * Returns this BEValue as a String, interpreted with the specified
     * encoding.
     *
     * @param encoding The encoding to interpret the bytes as when converting
     * them into a {@link String}.
     * @throws InvalidBEncodingException If the value is not a byte[].
     */
    public String getString(String encoding) throws InvalidBEncodingException {
        try {
            return new String(this.getBytes(), encoding);
        } catch (ClassCastException cce) {
            throw new InvalidBEncodingException(cce.toString());
        } catch (UnsupportedEncodingException uee) {
            throw new InternalError(uee.toString());
        }
    }

    /**
     * Returns this BEValue as a byte[].
     *
     * @throws InvalidBEncodingException If the value is not a byte[].
     */
    public byte[] getBytes() throws InvalidBEncodingException {
        try {
            return (byte[]) this.value;
        } catch (ClassCastException cce) {
            throw new InvalidBEncodingException(cce.toString());
        }
    }

    /**
     * Returns this BEValue as a Number.
     *
     * @throws InvalidBEncodingException  If the value is not a {@link Number}.
     */
    public Number getNumber() throws InvalidBEncodingException {
        try {
            return (Number) this.value;
        } catch (ClassCastException cce) {
            throw new InvalidBEncodingException(cce.toString());
        }
    }

    /**
     * Returns this BEValue as short.
     *
     * @throws InvalidBEncodingException If the value is not a {@link Number}.
     */
    public short getShort() throws InvalidBEncodingException {
        return this.getNumber().shortValue();
    }

    /**
     * Returns this BEValue as int.
     *
     * @throws InvalidBEncodingException If the value is not a {@link Number}.
     */
    public int getInt() throws InvalidBEncodingException {
        return this.getNumber().intValue();
    }

    /**
     * Returns this BEValue as long.
     *
     * @throws InvalidBEncodingException If the value is not a {@link Number}.
     */
    public long getLong() throws InvalidBEncodingException {
        return this.getNumber().longValue();
    }

    /**
     * Returns this BEValue as a List of BEValues.
     *
     * @throws InvalidBEncodingException If the value is not an
     * {@link ArrayList}.
     */
    @SuppressWarnings("unchecked")
    public List<BEncodedValue> getList() throws InvalidBEncodingException {
        if (this.value instanceof ArrayList) {
            return (ArrayList<BEncodedValue>) this.value;
        } else {
            throw new InvalidBEncodingException("Excepted List<BEvalue> !");
        }
    }

    /**
     * Returns this BEValue as a Map of String keys and BEValue values.
     *
     * @throws InvalidBEncodingException If the value is not a {@link HashMap}.
     */
    @SuppressWarnings("unchecked")
    public Map<String, BEncodedValue> getMap() throws InvalidBEncodingException {
        if (this.value instanceof HashMap) {
            return (Map<String, BEncodedValue>) this.value;
        } else {
            throw new InvalidBEncodingException("Expected Map<String, BEValue> !");
        }
    }
}
