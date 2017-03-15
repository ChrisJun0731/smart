package com.sumridge.smart.util;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import com.sumridge.smart.entity.FileViewer;

import java.util.Collection;
import java.util.List;


/**
 * Created by liu on 16/4/20.
 */
public class FileTypeUtil {

    static final ListMultimap<String,String> formatMap = new ImmutableListMultimap.Builder<String, String>()
                    .putAll("word", "doc", "docx")
                    .putAll("excel", "xls", "xlsx")
                    .putAll("powerpoint", "ppt", "pptx")
                    .putAll("archive", "zip", "7z", "rar", "tar")
                    .putAll("audio", "mp3")
                    .putAll("video", "mp4")
                    .putAll("image", "jpg","gif","png")
                    .putAll("pdf", "pdf")
                    .putAll("code", "html","java","py","js","css","json","sql","xml","jsp","php")
                    .build().inverse();

    static final ListMultimap<String, String> viewMap = new ImmutableListMultimap.Builder<String, String>()
                    .putAll("office","word","excel","powerpoint")
                    .putAll("audio","audio")
                    .putAll("video","video")
                    .putAll("image","image")
                    .putAll("pdf","pdf")
                    .putAll("text","text","code")
                    .putAll("application","file","archive")
                    .build().inverse();

    public static FileViewer getFileType(String contentType, String name) {
        FileViewer viewer = new FileViewer();
        String ext = getFileExtension(name);
        System.out.println("ext:"+ext);
        viewer.setType(ext);
        List<String> format = formatMap.get(ext);
        if(format != null && format.size() > 0) {
            viewer.setFormat(format.get(0));
        } else {
            viewer.setFormat(checkType(contentType));
        }

        List<String> view = viewMap.get(viewer.getFormat());
        viewer.setViewer(view.get(0));

        return viewer;
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex == -1?"":fileName.substring(dotIndex + 1);
    }

    private static String checkType(String contentType) {
        if(contentType.indexOf("text") == 0) {
            return "text";
        } else {
            return "file";
        }
    }
}
