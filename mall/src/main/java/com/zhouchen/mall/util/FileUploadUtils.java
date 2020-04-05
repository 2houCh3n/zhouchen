package com.zhouchen.mall.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * User：zhouchen
 * Time: 2020/3/20  18:06
 * Description:
 */
public class FileUploadUtils {
    public static Map<String, Object> parseRequest(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");//避免读取的文件名出现乱码现象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = request.getServletContext();//获取相应的应用
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

        ServletFileUpload upload = new ServletFileUpload(factory);//创建一个用于上传文件的处理器
        upload.setHeaderEncoding("utf-8");
        Map<String, Object> params = new HashMap<>();

        try {
            List<FileItem> items = upload.parseRequest(request);//获取所有的form组件
            Iterator<FileItem> iterator = items.iterator();//获取List的迭代器

            while (iterator.hasNext()) {
                FileItem fileItem = iterator.next();

                if (fileItem.isFormField()) {
                    //表示当前组件是一个普通的form组件
                    processFromField(fileItem, params);
                } else {
                    processUploadFile(fileItem, params, request);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 处理file文件的数据
     * @param fileItem
     * @param params
     * @param request
     */
    private static void processUploadFile(FileItem fileItem, Map<String, Object> params, HttpServletRequest request) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String fieldName = fileItem.getFieldName();
        String fileName = fileItem.getName();
        if ("".equals(fileName)) {
            return;
        }

        fileName = uuid + "-" + fileName;
        String contentType = fileItem.getContentType();
        int hashCode = fileName.hashCode();

        String hexString = Integer.toHexString(hashCode);
        String basePath = "/upload";
        char[] charArray = hexString.toCharArray();

        for (char ch: charArray) {
            basePath = basePath + "/"  + ch;
        }

        String relativePath = basePath + "/" + fileName;
        String realPath = request.getServletContext().getRealPath(relativePath);
        File file = new File(realPath);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        fileItem.write(file);
        params.put("file", relativePath);
    }

    /**
     * 处理普通form组件的数据
     * @param fileItem
     * @param params
     */
    private static void processFromField(FileItem fileItem, Map<String, Object> params) throws UnsupportedEncodingException {
        String fieldName = fileItem.getFieldName();
        //为了避免表单内容出现乱码情况
        String value = fileItem.getString("utf-8");
        params.put(fieldName, value);
    }
}
