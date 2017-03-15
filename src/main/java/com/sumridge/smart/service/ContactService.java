package com.sumridge.smart.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sumridge.smart.bean.ContactAggBean;
import com.sumridge.smart.bean.FieldBean;
import com.sumridge.smart.bean.ResultBean;
import com.sumridge.smart.controller.ContactController;
import com.sumridge.smart.dao.ContactRepository;
import com.sumridge.smart.dao.UserInviteRepository;
import com.sumridge.smart.entity.*;
import com.sumridge.smart.query.AccountQuery;
import com.sumridge.smart.query.ContactQuery;
import com.sumridge.smart.query.FileTraceQuery;
import com.sumridge.smart.util.FileUtil;
import com.sumridge.smart.util.StringUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.elasticsearch.search.aggregations.metrics.percentiles.hdr.InternalHDRPercentileRanks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * Created by liu on 16/5/11.
 */
@Service
public class ContactService {
    //add by zj
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactQuery contactQuery;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private BoardService boardService;

    @Autowired
    private UserInfoService userInfoService;

    //add by zj
    @Autowired
    private CacheService cacheService;
    @Autowired
    private FileTraceQuery fileTraceQuery;

    @Autowired
    private UserInviteRepository userInviteRepository;

    @Autowired
    private MailService mailService;

    public ResultBean saveContactInfo(UserInfo userInfo, Contact info) {
        info.setInitial(StringUtil.getInitChar(info.getFirstName()));
        if(info.getId() != null) {
//            contactQuery.updateContactInfo(info);
            contactRepository.save(info);
        }
        else {
            String visType = info.getVisibleList().get(0).getVisType();
            if("team".equals(visType)){
                List<Visible> visibleList = new ArrayList<>();
                Iterator<String> it = userInfo.getTeamIdSet().iterator();
                while(it.hasNext()){
                    Visible visible = new Visible();
                    String teamId = it.next();
                    visible.setVisId(teamId);
                    visible.setVisId(visType);
                    visibleList.add(visible);
                }
                info.setVisibleList(visibleList);
            }
            else if("company".equals(visType)){
                info.getVisibleList().get(0).setVisId(userInfo.getCompany());
            }
            else if("private".equals(visType)){
                info.getVisibleList().get(0).setVisId(userInfo.getId());
            }
            contactRepository.save(info);
            boardService.createContactBoard(info);
        }

        return ResultBean.getSuccessResult();
    }

    //add by zj
    public ResultBean saveFile(UserInfo userInfo, MultipartFile file, String privilege) {

        if(!checkValidUploadFile(file)) {
            //check file value
            try {
                Reader reader = new InputStreamReader(file.getInputStream());
                CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader().withDelimiter(','));
                List<Contact> contactList = new ArrayList<>();
                try {
                    for(final CSVRecord record : parser) {
                        LOGGER.info(record.toString());
                        //add by zj
                        Contact contact = new Contact();
                        contact.setFirstName(record.get("firstName"));
                        contact.setLastName(record.get("lastName"));
                        contact.setPhone(record.get("Phone"));
                        contact.setEmail(record.get("Email"));
                        contact.setInitial(StringUtil.getInitChar(contact.getFirstName()));
                        if("private".equals(privilege)){
                            Visible visible = new Visible();
                            visible.setVisType(privilege);
                            visible.setVisId(userInfo.getId());
                            contact.setVisibleList(Arrays.asList(visible));
                        }
                        else if("public".equals(privilege)){
                            Visible visible = new Visible();
                            visible.setVisType(privilege);
                            contact.setVisibleList(Arrays.asList(visible));
                        }
                        else if("team".equals(privilege)){
                            List<Visible> visibleList = new ArrayList<>();
                            Set<String> teamIdSet = userInfo.getTeamIdSet();
                            Iterator it = teamIdSet.iterator();
                            while(it.hasNext()){
                                Visible visible = new Visible();
                                String teamId = (String)it.next();
                                visible.setVisId(teamId);
                                visible.setVisType(privilege);
                                visibleList.add(visible);
                            }
                            contact.setVisibleList(visibleList);
                        }
                        else if("company".equals(privilege)){
                            Visible visible = new Visible();
                            visible.setVisId(userInfo.getCompany());
                            visible.setVisType(privilege);
                             contact.setVisibleList(Arrays.asList(visible));
                        }
                        contactList.add(contact);
                    }
                    for(Contact contact:contactList)
                    {
                        contactQuery.addContactInfo(contact);
                    }
                } finally {
                    parser.close();
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResultBean.getSuccessResult();

        } else {

            return ResultBean.geFailResult(null, "file upload failed");
        }

    }
    //add by zj
    private boolean checkValidUploadFile(MultipartFile file) {

        try {
            String md5Str = FileUtil.toMD5String(file.getInputStream());
            return fileTraceQuery.isExsit(md5Str, "sale-info", "s");

        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    public ResultBean getContact(String contactId) {

        return ResultBean.getSuccessResult(contactRepository.findOne(contactId));
    }

    public Contact getContactByMapping(String oppTrader, String brokerInitials, String ecnSource) {

        Contact contactId = contactQuery.getContactByMap(oppTrader, brokerInitials, ecnSource);
        return contactId;
    }

    public String getContactName(String id) {

        return contactQuery.getContactName(id);

    }

    //add by zj 16/9/18
    public ResultBean saveMapFile(UserInfo userInfo, MultipartFile file) {
        try
        {
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader().withDelimiter(','));
            try
            {
                for(final CSVRecord record:parser)
                {
                    String clientContactFirstName = record.get("ClientContactFirstName");
                    String clientContactLastName = record.get("ClientContactLastName");
                    String clientShortName = record.get("ClientShortName");
                    String mpId = record.get("mpid");
                    String userId = record.get("UserID");
                    String brokerInitial;
                    //TODO temp use only for import mx data
                    String source = "MX";
                    if(!"".equals(mpId))
                        brokerInitial = mpId;
                    else
                        brokerInitial = clientShortName.toUpperCase().replace("_","").replace(" ","");
                    Contact contact = new Contact();
                    contact.setFirstName(clientContactFirstName);
                    contact.setLastName(clientContactLastName);
                    AccountMapping mapping = new AccountMapping();
                    mapping.setId(userId);
                    mapping.setAccount(brokerInitial);
                    mapping.setSource(source);
                    contactQuery.updateContactInfo(contact, mapping);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {

            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return ResultBean.getSuccessResult();
    }

    public ResultBean inviteContactInfo(Contact info, UserInfo userInfo) {

        //step 1: check user exist
        info = contactRepository.findOne(info.getId());
        String email = info.getEmail();
        if(!checkContactUserExsit(email)) {

            //step 2: gen token and insert invite data
            String url = createInvite(info, userInfo);
            //step 3: send email
            sendInviteEmail(url, email);
        }



        return ResultBean.getSuccessResult();
    }

    private void sendInviteEmail(String url, String email) {

        Map<String, Object> map = new HashMap<>();
        map.put("url", url);
        String content = mailService.buildMailContent("invite-email", map);
        mailService.sendEmail(email, "invite join in crm", content);
    }

    private String createInvite(Contact info, UserInfo userInfo) {

        UserInvite invite = new UserInvite();
        invite.setEmail(info.getEmail());
        invite.setInviteDate(new Date());
        invite.setInviteUser(userInfo.getId());
        invite.setType("contact-join");
        invite.setFirstName(info.getFirstName());
        invite.setLastName(info.getLastName());
        invite.setPhone(info.getPhone());
        invite.setContactId(info.getId());
        userInviteRepository.save(invite);

        //TODO temp use
        String url = "http://localhost:8080/invite?id="+invite.getId();
        return url;
    }

    private boolean checkContactUserExsit(String email) {

        //TODO temp use
        return userInfoService.checkUserExsit(email);
    }
    //add by zj 16/11/04
    public ResultBean saveFormField(String customFields) {
        List<FieldBean> fields = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(customFields);
        for (int i = 0; i < jsonArray.size(); i++) {
            String[] fieldValue = {};
            JSONObject object = jsonArray.getJSONObject(i);
            FieldBean field = new FieldBean();
            field.setFieldName((String)object.get("fieldName"));
            field.setFieldType((String)object.get("fieldType"));
            field.setFieldValue(((JSONArray)object.get("fieldValue")).toArray(fieldValue));
            field.setState((String)object.get("state"));
            fields.add(field);
        }
        contactQuery.saveFormField(fields);
        return null;
    }

    //add by zj 17/01/03
    public ResultBean getContactNames(){
        List<Contact> contacts = contactQuery.getContactNames();
        List<String> contactNames = new ArrayList<>();
        for(Contact contact: contacts){
            String name = contact.getFirstName() + " " + contact.getLastName();
            contactNames.add(name);
        }
        return ResultBean.getSuccessResult(contactNames);
    }

    //query contact initial collection add by zj 17/03/08
    public List<Map> queryInitials(){
        List<Map> initials = contactQuery.getInitials();
        return initials;
    }

    //query contacts with special initial character
    public List<ContactAggBean> queryInitialContacts(String initial, UserInfo userInfo, int limit){
        List<ContactAggBean> initialContactList = contactQuery.getInitialContacts(initial, userInfo, limit);
        return initialContactList;
    }
}
