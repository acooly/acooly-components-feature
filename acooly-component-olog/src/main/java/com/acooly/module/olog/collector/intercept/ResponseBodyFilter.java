package com.acooly.module.olog.collector.intercept;

import com.acooly.core.utils.Strings;
import com.acooly.module.olog.collector.OlogForwarder;
import com.acooly.module.olog.facade.dto.OlogDTO;
import org.apache.commons.io.output.TeeOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static com.acooly.module.olog.collector.intercept.OlogHandleInterceptor.OLOG_DTO_KEY;

/**
 * @author qiubo@yiji.com
 */
public class ResponseBodyFilter extends OncePerRequestFilter {
    @Autowired
    private OlogForwarder ologClient;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (!requestURI.endsWith(".html")) {
            filterChain.doFilter(request, response);
            return;
        }
        ResponseBodyWrapper responseBodyWrapper = new ResponseBodyWrapper(response);
        try {
            filterChain.doFilter(request, responseBodyWrapper);
        } finally {
            if (OlogHandleInterceptor.responseBody(request)) {
                String body = new String(responseBodyWrapper.toByteArray(), "UTF-8");
                OlogDTO ologOrder = (OlogDTO) request.getAttribute(OLOG_DTO_KEY);
                if (ologOrder != null) {
                    ologOrder.setOperateMessage(Strings.abbreviate(body, 4096));
                    ologClient.logger(ologOrder);
                }
            }
        }
    }

    public class ResponseBodyWrapper extends HttpServletResponseWrapper {

        private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        private PrintWriter writer = new PrintWriter(bos);

        public ResponseBodyWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletResponse getResponse() {
            return this;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new ServletOutputStream() {
                private TeeOutputStream tee =
                        new TeeOutputStream(ResponseBodyWrapper.super.getOutputStream(), bos);

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                }

                @Override
                public void write(int b) throws IOException {
                    tee.write(b);
                }
            };
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new TeePrintWriter(super.getWriter(), writer);
        }

        public byte[] toByteArray() {
            return bos.toByteArray();
        }
    }

    public class TeePrintWriter extends PrintWriter {

        PrintWriter branch;

        public TeePrintWriter(PrintWriter main, PrintWriter branch) {
            super(main, true);
            this.branch = branch;
        }

        @Override
        public void write(char buf[], int off, int len) {
            super.write(buf, off, len);
            super.flush();
            branch.write(buf, off, len);
            branch.flush();
        }

        @Override
        public void write(String s, int off, int len) {
            super.write(s, off, len);
            super.flush();
            branch.write(s, off, len);
            branch.flush();
        }

        @Override
        public void write(int c) {
            super.write(c);
            super.flush();
            branch.write(c);
            branch.flush();
        }

        public void flush() {
            super.flush();
            branch.flush();
        }
    }
}
