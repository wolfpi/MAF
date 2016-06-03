package com.baidu.maf.com;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hanxin on 2016/5/17.
 */
public class BufferInputStream extends InputStream {
    private Buffer buffer;

    public BufferInputStream(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public int read() throws IOException {
        return buffer.readByte();
    }

    @Override
    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        return  this.buffer.read(buffer, byteOffset, byteCount);
    }
}
