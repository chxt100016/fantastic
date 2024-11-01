package com.chxt.fantastic.common.torrent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.*;

public class BEncoder {

    @SuppressWarnings("unchecked")
    public static void encode(Object o, OutputStream out)
            throws IOException, IllegalArgumentException {
        if (o instanceof BEncodedValue) {
            o = ((BEncodedValue) o).getValue();
        }

        if (o instanceof String) {
            encode((String) o, out);
        } else if (o instanceof byte[]) {
            encode((byte[]) o, out);
        } else if (o instanceof Number) {
            encode((Number) o, out);
        } else if (o instanceof List) {
            encode((List<BEncodedValue>) o, out);
        } else if (o instanceof Map) {
            encode((Map<String, BEncodedValue>) o, out);
        } else {
            throw new IllegalArgumentException("Cannot bencode: " +
                    o.getClass());
        }
    }

    public static void encode(String s, OutputStream out) throws IOException {
        byte[] bs = s.getBytes("UTF-8");
        encode(bs, out);
    }

    public static void encode(Number n, OutputStream out) throws IOException {
        out.write('i');
        String s = n.toString();
        out.write(s.getBytes("UTF-8"));
        out.write('e');
    }

    public static void encode(List<BEncodedValue> l, OutputStream out)
            throws IOException {
        out.write('l');
        for (BEncodedValue value : l) {
            encode(value, out);
        }
        out.write('e');
    }

    public static void encode(byte[] bs, OutputStream out) throws IOException {
        String l = Integer.toString(bs.length);
        out.write(l.getBytes("UTF-8"));
        out.write(':');
        out.write(bs);
    }

    public static void encode(Map<String, BEncodedValue> m, OutputStream out)
            throws IOException {
        out.write('d');

        // Keys must be sorted.
        Set<String> s = m.keySet();
        List<String> l = new ArrayList<String>(s);
        Collections.sort(l);

        for (String key : l) {
            Object value = m.get(key);
            encode(key, out);
            encode(value, out);
        }

        out.write('e');
    }

    public static ByteBuffer encode(Map<String, BEncodedValue> m)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BEncoder.encode(m, baos);
        baos.close();
        return ByteBuffer.wrap(baos.toByteArray());
    }
}
