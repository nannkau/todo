package com.sgu.todo.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FileUtils {

    public static String upload(MultipartFile part, HttpServletRequest request, String path) throws IOException {
        System.out.println("uploadRootPath=" + path);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String strDate = dateFormat.format(date);


        File uploadRootDir = new File(path);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        String fileName = strDate+part.getOriginalFilename().replaceAll("\\s", "_");
        File file= new File(path +"/"+ fileName);
        try(InputStream is = part.getInputStream()){
            try(OutputStream os = new FileOutputStream(file)){
                int len = 0;
                byte[] bytes = new byte[1024];
                while ( ( len = is.read(bytes, 0, 1024)) > 0) {
                    os.write(bytes, 0, len);
                }
            }
        }
        return fileName;
    }
    public static List<String> upload(String path,HttpServletRequest request, MultipartFile[] parts) throws IOException {
        List<String> files = new ArrayList<String>(parts.length);
        for(MultipartFile part : parts) {
            String file = upload(part,request, path);
            files.add(file);
        }
        return files;
    }

}
