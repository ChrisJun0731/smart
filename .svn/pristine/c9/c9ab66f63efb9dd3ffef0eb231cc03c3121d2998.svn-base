package com.sumridge.smart.query;

import com.mongodb.WriteResult;
import com.sumridge.smart.bean.DiskFileOptBean;
import com.sumridge.smart.entity.BoardFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by liu on 16/4/15.
 */

@Component
public class BoardFileQuery {
    @Autowired
    private MongoTemplate mongoTemplate;


    public List<BoardFileInfo> queryTopFileList(String boardId, int count, boolean isHome) {
        Query query = new Query();
        Criteria queryCriteria = Criteria.where("boardId").is(boardId).and("fileType").ne("folder").and("delete").ne(Boolean.TRUE);
        if(!isHome) {
            queryCriteria.and("status").is("public");
        }

        query.addCriteria(queryCriteria);
        query.fields().include("fileType").include("_id").include("fileName");
        query.with(new Sort(Sort.Direction.DESC,"updateDate"));
        query.limit(count);
        List<BoardFileInfo> list = mongoTemplate.find(query, BoardFileInfo.class);
        return list;
    }
    public BoardFileInfo getParentRef(String parent) {

        Query query = new Query(Criteria.where("_id").is(parent));
        query.fields().include("_id").include("pathSet");
        BoardFileInfo info = mongoTemplate.findOne(query, BoardFileInfo.class);
        return  info;
    }
    public List<BoardFileInfo> queryFileList(String boardId, String parent, boolean isHome) {


        Query query = new Query();
        Criteria queryCriteria = Criteria.where("boardId").is(boardId).and("delete").ne(Boolean.TRUE);
        if(!isHome) {
            queryCriteria.and("status").is("public");
        }
        if(parent != null) {
            queryCriteria.and("parent").is(parent);
        } else {
            queryCriteria.and("parent").exists(false);
        }

        query.addCriteria(queryCriteria);
        query.fields().exclude("shareSet");
        query.with(new Sort(Sort.Direction.DESC,"updateDate"));
        List<BoardFileInfo> list = mongoTemplate.find(query, BoardFileInfo.class);
        return list;
    }

    public boolean exsitFile(String boardId, String parent, String fileName) {
        Query query = new Query();
        Criteria queryCriteria = Criteria.where("boardId").is(boardId).and("fileName").is(fileName).and("delete").ne(Boolean.TRUE);
        if(parent != null) {
            queryCriteria.and("parent").is(parent);
        } else {
            queryCriteria.and("parent").exists(false);
        }
        query.addCriteria(queryCriteria);

        return  mongoTemplate.exists(query, BoardFileInfo.class);

    }

    public boolean addFolder(String boardId, String parent, String folderName, String creator, String status) {
        BoardFileInfo folderInfo = new BoardFileInfo();
        folderInfo.setBoardId(boardId);
        folderInfo.setCreateDate(new Date());
        folderInfo.setCreator(creator);
        folderInfo.setFileName(folderName);
        folderInfo.setFileType("folder");
        setPathSet(parent, folderInfo);
        folderInfo.setStatus(status);
        folderInfo.setUpdateDate(folderInfo.getCreateDate());

        mongoTemplate.save(folderInfo);

        return true;

    }

    public void setPathSet(String parent, BoardFileInfo fileInfo) {

        if(parent != null) {
            fileInfo.setParent(parent);
            BoardFileInfo parentInfo = getParentRef(parent);

            Set<String> pathSet = parentInfo.getPathSet();
            if(pathSet == null) {
                pathSet = new HashSet<>();

            }
            pathSet.add(parent);

            fileInfo.setPathSet(pathSet);
        }

    }

    public List<BoardFileInfo> queryPathInfo(String boardId, String root) {
        List<BoardFileInfo> list = new ArrayList<>();

        Query query = new Query(Criteria.where("_id").is(root));
        query.fields().include("parent");
        BoardFileInfo info = mongoTemplate.findOne(query, BoardFileInfo.class);
        if(info != null) {

            root = info.getParent();
        }

        while (root != null) {
            query = new Query(Criteria.where("_id").is(root));
            query.fields().include("_id").include("fileName").include("parent");
            info = mongoTemplate.findOne(query, BoardFileInfo.class);
            if(info != null) {
                list.add(info);
                root = info.getParent();
            } else {
                break;
            }
        }

        return list;

    }

    public BoardFileInfo getFileInfo(String id) {
        Query query = new Query(Criteria.where("_id").is(id));

        query.fields().include("fileId").include("fileType");
        BoardFileInfo info = mongoTemplate.findOne(query, BoardFileInfo.class);

        return info;
    }

    public boolean logicRemove(List<DiskFileOptBean> removeList) {
        Set<String> removeSet = new HashSet<>();
        removeList.forEach(diskFileOptBean -> {
            removeSet.add(diskFileOptBean.getId());
        });
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(criteria.orOperator(Criteria.where("_id").in(removeSet), Criteria.where("pathSet").in(removeSet)));

        Update update = Update.update("delete", Boolean.TRUE);


        WriteResult resultHome = mongoTemplate.updateMulti(query, update, BoardFileInfo.class);

        return resultHome.isUpdateOfExisting();

    }
}
