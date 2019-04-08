package com.sh.carexx.common.util;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
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
import java.io.*;
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
//            content = Word2HtmlUtil.Word2007ToHtml(in);
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

    public static String Word2007ToHtml(InputStream in) throws IOException, ParserConfigurationException, TransformerException {
//        // 1) 加载word文档生成 XWPFDocument对象
//        XWPFDocument document = new XWPFDocument(in);
//        // 也可以使用字符数组流获取解析的内容
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        XHTMLOptions options = XHTMLOptions.create();
//        XHTMLConverter.getInstance().convert(document, baos, options);
//        String content = baos.toString();
//        baos.close();
//        return content;

        String filepath = "C:\\Users\\hasee\\Desktop";
        String sourceFileName =filepath+"1.docx";
        String targetFileName = filepath+"1496717486420.html";
        String imagePathStr = filepath+"/image/";
        OutputStreamWriter outputStreamWriter = null;
        try {
            XWPFDocument document = new XWPFDocument(in);
            XHTMLOptions options = XHTMLOptions.create();
            // 存放图片的文件夹
            options.setExtractor(new FileImageExtractor(new File(imagePathStr)));
            // html中图片的路径
            options.URIResolver(new BasicURIResolver("image"));
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(targetFileName), "utf-8");
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            xhtmlConverter.convert(document, outputStreamWriter, options);
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
        }
        return "";
    }

    public static String Word2003ToHtml(InputStream in) throws
            IOException, ParserConfigurationException, TransformerException {
//        XWPFDocument wordDocument2 = new XWPFDocument(in);
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