package com.acooly.module.obs.client.oss;

import com.acooly.module.obs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

/**
 * @author shuijing
 */
@Slf4j
public class RepeatableFileInputStream extends InputStream {

    private File file = null;
    private FileInputStream fis = null;
    private FileChannel fileChannel = null;
    private long markPos = 0;

    public RepeatableFileInputStream(File file) throws IOException {
        this(new FileInputStream(file), file);
    }

    public RepeatableFileInputStream(FileInputStream fis) throws IOException {
        this(fis, null);
    }

    public RepeatableFileInputStream(FileInputStream fis, File file) throws IOException {
        this.file = file;
        this.fis = fis;
        this.fileChannel = fis.getChannel();
        this.markPos = fileChannel.position();
    }

    public void reset() throws IOException {
        fileChannel.position(markPos);
        log.trace("Reset to position " + markPos);
    }

    public boolean markSupported() {
        return true;
    }

    public void mark(int readlimit) {
        try {
            markPos = fileChannel.position();
        } catch (IOException e) {
            throw new ClientException("Failed to mark file position", e);
        }
        log.trace("File input stream marked at position " + markPos);
    }

    public int available() throws IOException {
        return fis.available();
    }

    public void close() throws IOException {
        fis.close();
    }

    public int read() throws IOException {
        return fis.read();
    }

    @Override
    public long skip(long n) throws IOException {
        return fis.skip(n);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        return fis.read(b, off, len);
    }

    public InputStream getWrappedInputStream() {
        return this.fis;
    }

    public File getFile() {
        return this.file;
    }
}
