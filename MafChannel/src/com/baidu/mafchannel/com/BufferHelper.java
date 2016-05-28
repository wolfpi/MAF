/**
 * 
 */
package com.baidu.mafchannel.com;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author wangqiying
 * 
 */
public class BufferHelper {

	private static long ZigZagEncode32(int n) {
		// Note: the right-shift must be arithmetic
		return (n << 1) ^ (n >> 31);
		// return n;
	}

	private static long ZigZagDecode32(long n) {
		return (n >> 1) ^ -(n & 1);
		// return n;
	}

	private static long ZigZagEncode64(long n) {
		// Note: the right-shift must be arithmetic
		return (n << 1) ^ (n >> 63);
		// return n;
	}

	private static long ZigZagDecode64(long n) {
		return (n >> 1) ^ -(n & 1);
		// return n;
	}

	public static void writeByte(Buffer buffer, final byte value) {
		buffer.writeByte(value);
	}

	public static void writeByte(Buffer buffer, final int value) {
		writeByte(buffer, (byte) value);
	}

	public static void writeBoolean(Buffer buffer, boolean value) {
		writeByte(buffer, value ? 1 : 0);
	}

	private static void writeVarInt(Buffer buffer, int value, boolean zigzag) {
		if (zigzag) {
			value = (int) ZigZagEncode32(value);
		}

		buffer.ensureWritableBytes(5);
		byte[] bytes = buffer.getRawBuffer();
		int offset = buffer.getWriteIndex();
		int size = 0;
		while (value > 0x7F) {
			bytes[size + offset] = (byte) ((value & 0x7F) | 0x80);
			size++;
			value >>= 7;
		}
		bytes[offset + size] = (byte) ((value) & 0x7F);
		size++;
		buffer.advanceWriteIndex(size);
	}

	public static void writeVarInt(Buffer buffer, int value) {
		writeVarInt(buffer, value, true);
	}

	public static void writeVarUnsignedInt(Buffer buffer, int value) {
		writeVarInt(buffer, value, false);
	}

	public static void writeVarShort(Buffer buffer, int value) {
		writeVarShort(buffer, (short) value);
	}

	public static void writeVarShort(Buffer buffer, short value) {
		value = (short) ZigZagEncode32(value);
		if (value >= 0) {
			while (true) {
				if ((value & ~0x7FL) == 0) {
					writeByte(buffer, (int) value);
					return;
				} else {
					writeByte(buffer, ((int) value & 0x7F) | 0x80);
					value >>>= 7;
				}
			}
		} else {
			writeVarInt(buffer, value);
		}
	}

	public static void writeVarChar(Buffer buffer, int value) {
		writeVarChar(buffer, (char) value);
	}

	public static void writeVarChar(Buffer buffer, char value) {
		writeVarShort(buffer, value);
	}

	public static void writeVarLong(Buffer buffer, long value) {
		value = ZigZagEncode64(value);
		buffer.ensureWritableBytes(10);
		byte[] bytes = buffer.getRawBuffer();
		int offset = buffer.getWriteIndex();
		int size = 0;
		while (value > 0x7FL) {
			bytes[offset + size] = (byte) (((value) & 0x7F) | 0x80);
			size++;
			value >>= 7;
		}
		bytes[offset + size] = (byte) ((value) & 0x7F);
		size++;
		buffer.advanceWriteIndex(size);
	}

	private static void writeRawBigEndian16(Buffer buffer, final short value)
	{
		writeByte(buffer, (byte) (value >> 8));
		writeByte(buffer, (byte) (value));
	}

	private static void writeRawLittleEndian16(Buffer buffer, final short value)
	{
		writeByte(buffer, (value) & 0xFF);
		writeByte(buffer, (value >> 8) & 0xFF);
	}

	public static void writeFixInt16(Buffer buffer, short v,
			boolean networkOrder) {
		if (networkOrder) {
			writeRawBigEndian16(buffer, v);
		} else {
			writeRawLittleEndian16(buffer, v);
		}
	}

	private static void writeRawBigEndian32(Buffer buffer, final int value)

	{
		writeByte(buffer, (byte) (value >> 24));
		writeByte(buffer, (byte) (value >> 16));
		writeByte(buffer, (byte) (value >> 8));
		writeByte(buffer, (byte) (value));
	}

	private static void writeRawLittleEndian32(Buffer buffer, final int value)

	{
		writeByte(buffer, (value) & 0xFF);
		writeByte(buffer, (value >> 8) & 0xFF);
		writeByte(buffer, (value >> 16) & 0xFF);
		writeByte(buffer, (value >> 24) & 0xFF);
	}

	public static void writeFixInt32(Buffer buffer, int v, boolean networkOrder) {
		if (networkOrder) {
			writeRawBigEndian32(buffer, v);
		} else {
			writeRawLittleEndian32(buffer, v);
		}
	}

	private static void writeRawBigEndian64(Buffer buffer, final long x) {
		writeByte(buffer, (byte) (x >> 56));
		writeByte(buffer, (byte) (x >> 48));
		writeByte(buffer, (byte) (x >> 40));
		writeByte(buffer, (byte) (x >> 32));
		writeByte(buffer, (byte) (x >> 24));
		writeByte(buffer, (byte) (x >> 16));
		writeByte(buffer, (byte) (x >> 8));
		writeByte(buffer, (byte) (x));
	}

	private static void writeRawLittleEndian64(Buffer buffer, final long value) {
		writeByte(buffer, (int) (value) & 0xFF);
		writeByte(buffer, (int) (value >> 8) & 0xFF);
		writeByte(buffer, (int) (value >> 16) & 0xFF);
		writeByte(buffer, (int) (value >> 24) & 0xFF);
		writeByte(buffer, (int) (value >> 32) & 0xFF);
		writeByte(buffer, (int) (value >> 40) & 0xFF);
		writeByte(buffer, (int) (value >> 48) & 0xFF);
		writeByte(buffer, (int) (value >> 56) & 0xFF);
	}

	public static void writeFixInt64(Buffer buffer, long v, boolean networkOrder) {
		if (networkOrder) {
			writeRawBigEndian64(buffer, v);
		} else {
			writeRawLittleEndian64(buffer, v);
		}
	}

	public static void writeVarFloat(Buffer buffer, final float value) {
		writeVarInt(buffer, Float.floatToRawIntBits(value));
	}

	public static void writeVarDouble(Buffer buffer, final double value) {
		writeVarLong(buffer, Double.doubleToRawLongBits(value));
	}

	public static void writeVarString(Buffer buffer, String s) {
		int len = null != s ? s.length() : 0;
		writeVarUnsignedInt(buffer, len);
		if (null != s && !s.isEmpty()) {
			try {
				buffer.write(s.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				//
			}
		}
	}

	// public static int readRawLittleEndian32(Buffer buffer)
	// {
	// final byte b1 = buffer.readByte();
	// final byte b2 = buffer.readByte();
	// final byte b3 = buffer.readByte();
	// final byte b4 = buffer.readByte();
	// return (((int) b1 & 0xff)) | (((int) b2 & 0xff) << 8)
	// | (((int) b3 & 0xff) << 16) | (((int) b4 & 0xff) << 24);
	// }
	//
	// public long readRawLittleEndian64(Buffer buffer)
	// {
	// final byte b1 = buffer.readByte();
	// final byte b2 = buffer.readByte();
	// final byte b3 = buffer.readByte();
	// final byte b4 = buffer.readByte();
	// final byte b5 = buffer.readByte();
	// final byte b6 = buffer.readByte();
	// final byte b7 = buffer.readByte();
	// final byte b8 = buffer.readByte();
	// return (((long) b1 & 0xff)) | (((long) b2 & 0xff) << 8)
	// | (((long) b3 & 0xff) << 16) | (((long) b4 & 0xff) << 24)
	// | (((long) b5 & 0xff) << 32) | (((long) b6 & 0xff) << 40)
	// | (((long) b7 & 0xff) << 48) | (((long) b8 & 0xff) << 56);
	// }

	public static int readBytes(Buffer buffer, byte[] b) {
		return buffer.read(b);
	}

	public static int readBytes(Buffer buffer, byte[] b, int off, int len) {
		return buffer.read(b, off, len);
	}

	public static boolean readBoolean(Buffer buffer) {
		return buffer.readByte() != 0;
	}

	private static int unsingedbyte(byte v) {
		int iv = v;
		if (iv < 0) {
			iv = 256 + iv;
		}
		return iv;
	}

	// private static long unsingedLong(long v)
	// {
	//
	// }

	public static long readVarLong(Buffer buffer) throws IOException {
		return readVarLong(buffer, true);
	}

	private static long readVarLong(Buffer buffer, boolean zigzag)
			throws IOException {
		long result = 0;
		int count = 0;
		long b;
		byte ch;
		do {
			if (count == 10) {
				throw new IOException("encountered a malformed varint");
			}
			if (!buffer.readable()) {
				throw new IOException(
						"encountered a malformed varint with no sufficient space");
			}
			ch = buffer.readByte();

			b = unsingedbyte(ch);
			result |= ((b & 0x7FL) << (7 * count));
			++count;
		} while ((b & 0x80L) != 0);
		if (zigzag) {
			return ZigZagDecode64(result);
		} else {
			return result;
		}
	}

	public static short readVarShort(Buffer buffer) throws IOException {
		int shift = 0;
		short result = 0;
		while (shift < 16) {
			final byte b = buffer.readByte();
			result |= (short) (b & 0x7F) << shift;
			if ((b & 0x80) == 0) {
				result = (short) ZigZagDecode32(result);
				return result;
			}
			shift += 7;
		}
		throw new IOException("encountered a malformed varint");
	}

	public static char readVarChar(Buffer buffer) throws IOException {
		int shift = 0;
		char result = 0;
		while (shift < 16) {
			final byte b = buffer.readByte();
			result |= (short) (b & 0x7F) << shift;
			if ((b & 0x80) == 0) {
				return result;
			}
			shift += 7;
		}
		throw new IOException("encountered a malformed varint");
	}

	public static int readVarInt(Buffer buffer) throws IOException {
		long v = readVarLong(buffer, true);
		return (int) v;
	}

	public static int readVarUnsignedInt(Buffer buffer) throws IOException {
		long v = readVarLong(buffer, false);
		// v = ZigZagDecode32(v);
		return (int) v;
	}

	public static boolean readBool(Buffer buffer) {
		return buffer.readByte() == 1;
	}

	public static double readVarDouble(Buffer buffer) throws IOException {
		return Double.longBitsToDouble(readVarLong(buffer));
	}

	public static float readVarFloat(Buffer buffer) throws IOException {
		return Float.intBitsToFloat(readVarInt(buffer));
	}

	private static short readRawBigEndian16(Buffer buffer)

	{
		byte b0 = buffer.readByte();
		byte b1 = buffer.readByte();
		return (short) ((b0 << 8) | (b1 & 0xff));
	}

	private static short readRawLittleEndian16(Buffer buffer)

	{
		byte b0 = buffer.readByte();
		byte b1 = buffer.readByte();
		return (short) ((b1 << 8) | (b0 & 0xff));
	}

	public static short readFixInt16(Buffer buffer, boolean networkOrder) {
		if (networkOrder) {
			return readRawBigEndian16(buffer);
		} else {
			return readRawLittleEndian16(buffer);
		}
	}

	private static int readRawBigEndian32(Buffer buffer) {
		byte b0 = buffer.readByte();
		byte b1 = buffer.readByte();
		byte b2 = buffer.readByte();
		byte b3 = buffer.readByte();
		return (((b0) << 24) | ((b1 & 0xff) << 16) | ((b2 & 0xff) << 8) | ((b3 & 0xff)));
	}

	private static int readRawLittleEndian32(Buffer buffer)

	{
		byte b0 = buffer.readByte();
		byte b1 = buffer.readByte();
		byte b2 = buffer.readByte();
		byte b3 = buffer.readByte();
		return (((b3) << 24) | ((b2 & 0xff) << 16) | ((b1 & 0xff) << 8) | ((b0 & 0xff)));
	}

	public static int readFixInt32(Buffer buffer, boolean networkOrder) {
		if (networkOrder) {
			return readRawBigEndian32(buffer);
		} else {
			return readRawLittleEndian32(buffer);
		}
	}

	private static long readRawBigEndian64(Buffer buffer) {
		byte b0 = buffer.readByte();
		byte b1 = buffer.readByte();
		byte b2 = buffer.readByte();
		byte b3 = buffer.readByte();
		byte b4 = buffer.readByte();
		byte b5 = buffer.readByte();
		byte b6 = buffer.readByte();
		byte b7 = buffer.readByte();
		return ((((long) b0) << 56) | (((long) b1 & 0xff) << 48)
				| (((long) b2 & 0xff) << 40) | (((long) b3 & 0xff) << 32)
				| (((long) b4 & 0xff) << 24) | (((long) b5 & 0xff) << 16)
				| (((long) b6 & 0xff) << 8) | (((long) b7 & 0xff)));
	}

	private static long readRawLittleEndian64(Buffer buffer) {
		byte b0 = buffer.readByte();
		byte b1 = buffer.readByte();
		byte b2 = buffer.readByte();
		byte b3 = buffer.readByte();
		byte b4 = buffer.readByte();
		byte b5 = buffer.readByte();
		byte b6 = buffer.readByte();
		byte b7 = buffer.readByte();

		return ((((long) b7) << 56) | (((long) b6 & 0xff) << 48)
				| (((long) b5 & 0xff) << 40) | (((long) b4 & 0xff) << 32)
				| (((long) b3 & 0xff) << 24) | (((long) b2 & 0xff) << 16)
				| (((long) b1 & 0xff) << 8) | (((long) b0 & 0xff)));
	}

	public static long readFixInt64(Buffer buffer, boolean networkOrder) {
		if (networkOrder) {
			return readRawBigEndian64(buffer);
		} else {
			return readRawLittleEndian64(buffer);
		}
	}

	public static String readVarString(Buffer buffer) throws IOException {
		int len = readVarUnsignedInt(buffer);
		if (len > 0) {
			byte[] buf = new byte[len];
			readBytes(buffer, buf);
			return new String(buf, "UTF-8");
		} else {
			return null;
		}
	}

	public static void writeObject(Buffer buffer, Object obj) {
		if (obj instanceof Integer) {
			writeVarInt(buffer, (Integer) obj);
		} else if (obj instanceof Long) {
			writeVarLong(buffer, (Long) obj);
		} else if (obj instanceof String) {
			writeVarString(buffer, (String) obj);
		} else if (obj instanceof List) {
			writeList(buffer, (List) obj);
		} else if (obj instanceof Set) {
			writeSet(buffer, (Set) obj);
		}
	}

	public static void writeList(Buffer buffer, List list) {
		if (null == list) {
			writeVarInt(buffer, 0);
			return;
		}
		writeVarInt(buffer, list.size());
		for (Object obj : list) {
			writeObject(buffer, obj);
		}
	}

	public static void writeSet(Buffer buffer, Set set) {
		if (null == set) {
			writeVarInt(buffer, 0);
			return;
		}
		writeVarInt(buffer, set.size());
		for (Object obj : set) {
			writeObject(buffer, obj);
		}
	}

	private static <T> T readObject(Buffer buffer, Class<T> clazz)
			throws IOException {
		if (clazz.equals(Integer.class)) {
			Integer v = readVarInt(buffer);
			return (T) v;
		} else if (clazz.equals(Long.class)) {
			Long v = readVarLong(buffer);
			return (T) v;
		} else if (clazz.equals(String.class)) {
			String s = readVarString(buffer);
			return (T) s;
		} else if (clazz.equals(String.class)) {
			String s = readVarString(buffer);
			return (T) s;
		} else {
			throw new IOException("Unsupported class:" + clazz.getName());
		}
	}

	public static <T> List<T> readList(Buffer buffer, Class<T> clazz)
			throws IOException {
		LinkedList<T> list = new LinkedList<T>();
		int len = readVarInt(buffer);
		for (int i = 0; i < len; i++) {
			T obj = readObject(buffer, clazz);
			list.add(obj);
		}
		return list;
	}

	public static <T> Set<T> readSet(Buffer buffer, Class<T> clazz)
			throws IOException {
		Set<T> set = new HashSet<T>();
		int len = readVarInt(buffer);
		for (int i = 0; i < len; i++) {
			T obj = readObject(buffer, clazz);
			set.add(obj);
		}
		return set;
	}

}