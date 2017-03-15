package com.sumridge.smart.service;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by liu on 16/3/31.
 */
@Service
public class GridFileService {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    public Object saveFile(MultipartFile file) throws IOException {

        GridFSFile result = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());

        return result.getId();

    }

    public void removeFile(String id){
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(id)));
    }

    public GridFSDBFile getFile(String id) {
        GridFSDBFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
        return file;
    }

}
