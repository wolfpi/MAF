/**
 * 
 */
package com.baidu.maf.com;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wangqiying
 *
 */
public class BufferOutputStream  extends OutputStream {

	private Buffer buffer;
	
	public BufferOutputStream(Buffer buf)
	{
		buffer = buf;
	}
	
	public void write(byte b[], int off, int len) throws IOException {
		buffer.write(b, off, len);
	}
	
	@Override
	public void write(int b) throws IOException {
		buffer.writeByte((byte) b);
	}

}
