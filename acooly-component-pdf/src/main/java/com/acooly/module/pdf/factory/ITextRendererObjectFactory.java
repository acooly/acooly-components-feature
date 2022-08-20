package com.acooly.module.pdf.factory;

import com.acooly.core.common.boot.Apps;
import com.acooly.module.pdf.PdfProperties;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * ITextRenderer对象工厂,因为增加默认中文字体集(一个20M)，所以增加对象池
 *
 * @author shuijing
 */
@Slf4j
public class ITextRendererObjectFactory extends BasePooledObjectFactory<ITextRenderer> {
    private static final String BASE_PATH =
            System.getProperty("user.home") + File.separator + "appdata" + File.separator + "pdf";
    public static final String FONTS_PATH = BASE_PATH + File.separator + "fonts" + File.separator;
    private static final String IMAGE_PATH = BASE_PATH + File.separator + "image" + File.separator;
    private static final String CUSTOM_FONTS_PATH =
            BASE_PATH + File.separator + "customfonts" + File.separator;
    private static GenericObjectPool itextRendererObjectPool = null;
    private PdfProperties pdfProperties;
    private Map<String, Boolean> fontsCopy = Maps.newHashMap();

    public ITextRendererObjectFactory(PdfProperties pdfProperties) {
        this.pdfProperties = pdfProperties;
    }

    /**
     * 获取对象池,配置对象工厂
     */
    public static GenericObjectPool<ITextRenderer> getObjectPool(PdfProperties pdfProperties) {
        synchronized (ITextRendererObjectFactory.class) {
            if (itextRendererObjectPool == null) {
                itextRendererObjectPool =
                        new GenericObjectPool<>(new ITextRendererObjectFactory(pdfProperties));
                GenericObjectPoolConfig config = new GenericObjectPoolConfig();
                config.setLifo(false);
                config.setMaxTotal(15);
                config.setMaxIdle(15);
                config.setMinIdle(1);
                itextRendererObjectPool.setConfig(config);
            }
        }
        return itextRendererObjectPool;
    }

    private static File getFontsDataFile(String fontsName) throws IOException {
        final String s = FONTS_PATH + fontsName;
        Files.createParentDirs(new File(s));
        return new File(s);
    }

    @Override
    public ITextRenderer create() throws Exception {
        ITextRenderer renderer = createTextRenderer();
        return renderer;
    }

    @Override
    public PooledObject wrap(ITextRenderer obj) {
        return new DefaultPooledObject<>(obj);
    }

    /**
     * 初始化ITextRenderer对象
     *
     * <p>由于项目是spring boot，在linux运行会以jar运行，ITextRenderer api必须读取文件夹 jar中的文件不能获取绝对路径，所以拷贝出来添加
     */
    public synchronized ITextRenderer createTextRenderer() throws DocumentException, IOException {
        ITextRenderer renderer = new ITextRenderer();
        setSharedContext(renderer, pdfProperties);
        addFonts(renderer, pdfProperties);
        return renderer;
    }

    private void setSharedContext(ITextRenderer iTextRenderer, PdfProperties pdfProperties)
            throws IOException {
        Resource imageResource =
                pdfProperties.getResourceLoader().getResource(pdfProperties.getImagePath());
        if (imageResource.exists()) {
            String baseImageUrl;
            if (Apps.isDevMode()) {
                baseImageUrl = imageResource.getFile().toURI().toURL().toExternalForm();
                iTextRenderer.getSharedContext().setBaseURL(baseImageUrl);
            } else {
                copyFilesFromJarWithDirPath(pdfProperties, pdfProperties.getImagePath(), IMAGE_PATH);
                baseImageUrl = new File(IMAGE_PATH).toURI().toURL().toExternalForm();
            }
            log.info("base image url is {}", baseImageUrl);
            iTextRenderer.getSharedContext().setBaseURL(baseImageUrl);
        }
    }

    /**
     * 添加字体
     */
    public ITextFontResolver addFonts(ITextRenderer iTextRenderer, PdfProperties pdfProperties)
            throws DocumentException, IOException {
        //添加默认中文字体
        ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
        //在jar中的文件不能获取绝对路径，拷贝到临时文件夹中加载 (字体太大，不提供组件默认字体，如有需要，请采用配置自定义字体：classpath:/pdf/fonts/)
//        addFonts("classpath:META-INF/fonts/SourceHanSerifSC-Regular.otf", fontResolver);
//        addFonts("classpath:META-INF/fonts/SourceHanSerifSC-SemiBold.otf", fontResolver);
        //添加自定义字体
        Resource customFontsResource =
                pdfProperties.getResourceLoader().getResource(pdfProperties.getFontsPath());
        if (customFontsResource.exists()) {
            if (Apps.isDevMode()) {
                addFonts(new File(customFontsResource.getFile().getAbsolutePath()), fontResolver);
            } else {
                copyFilesFromJarWithDirPath(pdfProperties, pdfProperties.getFontsPath(), CUSTOM_FONTS_PATH);
                addFonts(new File(CUSTOM_FONTS_PATH), fontResolver);
            }
        }
        return fontResolver;
    }

    private void addFonts(String jarFontsPath, ITextFontResolver fontResolver)
            throws IOException, DocumentException {
        InputStream is = null;
        BufferedOutputStream writer = null;
        FileOutputStream fos = null;
        try {
            is = pdfProperties.getResourceLoader().getResource(jarFontsPath).getInputStream();
            File fontsDataFile =
                    getFontsDataFile(jarFontsPath.substring(jarFontsPath.lastIndexOf("/") + 1));
            Boolean isCopyExists = fontsCopy.get(jarFontsPath);
            if (isCopyExists == null || !isCopyExists) {
                fos = new FileOutputStream(fontsDataFile);
                writer = new BufferedOutputStream(fos);
                copyBytes(is, writer);
                fontsCopy.put(jarFontsPath, Boolean.TRUE);
            }
            fontResolver.addFont(
                    fontsDataFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            log.info("add default fonts {}", fontsDataFile.getAbsolutePath());
        } finally {
            if (is != null) {
                is.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    private void addFonts(File fontsDir, ITextFontResolver fontResolver)
            throws IOException, DocumentException {
        if (fontsDir != null && fontsDir.isDirectory()) {
            File[] files = fontsDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f == null || f.isDirectory()) {
                    break;
                }
                fontResolver.addFont(f.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                log.info("add custom fonts {}", f.getAbsolutePath());
            }
        }
    }

    public void copyFilesFromJarWithDirPath(
            PdfProperties pdfProperties, String srcDir, String descDir) throws IOException {

        ResourceLoader resourceLoader = pdfProperties.getResourceLoader();
        Resource imageResource = resourceLoader.getResource(srcDir);
        String sourcePath = imageResource.getURL().toString();
        if (log.isDebugEnabled()) {
            log.debug("source path is {}", sourcePath);
        }
        String jarPath = sourcePath.substring(0, sourcePath.indexOf("!/") + 2);
        if (log.isDebugEnabled()) {
            log.debug("jar path is {}", jarPath);
        }
        URL jarURL = new URL(jarPath);
        JarURLConnection jarCon = (JarURLConnection) jarURL.openConnection();
        JarFile jarFile = jarCon.getJarFile();
        Enumeration<JarEntry> jarEntrys = jarFile.entries();

        if (!jarEntrys.hasMoreElements()) {
            if (log.isDebugEnabled()) {
                log.debug("jar entrys is empty");
            }
            return;
        }
        while (jarEntrys.hasMoreElements()) {
            JarEntry entry = jarEntrys.nextElement();
            String name = entry.getName();
            //name "BOOT-INF/classes/pdf/images/logo.png"
            String srcDirPathSub =
                    srcDir.contains("classpath:/")
                            ? srcDir.substring(srcDir.indexOf("classpath:/") + 10)
                            : srcDir;
            if (!entry.isDirectory() && name.contains(srcDirPathSub)) {
                if (log.isDebugEnabled()) {
                    log.debug("need copy file is {}", name);
                }
                InputStream is = this.getClass().getClassLoader().getResourceAsStream(name);
                if (is != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("inputStream exists,name is {}", name);
                    }
                }
                copyResource(is, descDir + name.substring(name.lastIndexOf("/") + 1));
            }
        }
    }

    private void copyResource(InputStream is, String copyPath) throws IOException {
        Boolean isCopyExists = fontsCopy.get(copyPath);
        if (isCopyExists == null || !isCopyExists) {
            Files.createParentDirs(new File(copyPath));
            copyBytes(is, new FileOutputStream(copyPath));
            fontsCopy.put(copyPath, Boolean.TRUE);
            if (log.isDebugEnabled()) {
                log.debug("copy file success,copy path is :{}", copyPath);
            }
        }
    }

    private void copyBytes(InputStream in, OutputStream out) throws IOException {
        copyBytes(in, out, 2048);
    }

    private void copyBytes(InputStream in, OutputStream out, int buffSize) throws IOException {
        PrintStream ps = out instanceof PrintStream ? (PrintStream) out : null;
        byte buf[] = new byte[buffSize];
        int bytesRead = in.read(buf);
        while (bytesRead >= 0) {
            out.write(buf, 0, bytesRead);
            if ((ps != null) && ps.checkError()) {
                throw new IOException("Unable to write to output stream.");
            }
            bytesRead = in.read(buf);
        }
        out.flush();
    }
}
