package com.sh.carexx.common.util;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Word2HtmlUtil {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(Word2HtmlUtil.class);

    public static String getPreviewContent(String destUrl) {
        HttpURLConnection httpUrl = null;
        URL url;
        String content;
        try {
            url = new URL(destUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            InputStream in = httpUrl.getInputStream();
            content = Word2HtmlUtil.Word2003ToHtml(in);
            return content;
        } catch (IOException e) {
            logger.info("IOException");
            return null;
        } catch (Exception e) {
            logger.info("Exception");
            return null;
        } finally {
            httpUrl.disconnect();
        }
    }

    public static String Word2007ToHtml(InputStream in) throws IOException {
        // 1) 加载word文档生成 XWPFDocument对象
        XWPFDocument document = new XWPFDocument(in);
        // 也可以使用字符数组流获取解析的内容
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XHTMLConverter.getInstance().convert(document, baos, null);
        String content = baos.toString();
        baos.close();
        return content;
    }

    public static String Word2003ToHtml(InputStream in) throws IOException, ParserConfigurationException, TransformerException {
        HWPFDocument wordDocument = new HWPFDocument(in);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());

        // 解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();

        // 也可以使用字符数组流获取解析的内容
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(baos);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);

        // 也可以使用字符数组流获取解析的内容
        String content = new String(baos.toByteArray());
        baos.close();
        System.out.println(content);
        return content;
    }
}