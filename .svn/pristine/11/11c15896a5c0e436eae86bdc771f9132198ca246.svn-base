package com.sumridge.smart.msdcache;

import com.alibaba.fastjson.JSON;
import com.sumridge.smart.util.DateUtil;
import com.sumridge.smart.util.StringUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liu on 16/10/11.
 */

@Component
public class MsdCache {

    // key set keep data of msd
    public static String MSD_DATA_KEY = "msd:data:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public MsdDataBean getMsdData(String cusip) {
        String fetchKey = MSD_DATA_KEY+ cusip;
        MsdDataBean bean = new MsdDataBean();


        if(stringRedisTemplate.hasKey(fetchKey)) {
            HashOperations<String, Object, Object> ops = stringRedisTemplate.opsForHash();
            bean.setCusip(cusip);
            bean.setAmountOuts(NumberUtils.toDouble(StringUtil.toString(ops.get(fetchKey, "amtIssued"))));
            String call = StringUtil.toString(ops.get(fetchKey, "callAble"));
            if (call != null) {
                bean.setCallable(call.equals("Y"));
            } else {
                bean.setCallable(false);
            }

            bean.setCoupon(NumberUtils.toDouble(StringUtil.toString(ops.get(fetchKey, "cpn"))));
            bean.setCouponType(StringUtil.toString(ops.get(fetchKey, "cpnType")));
            bean.setIndustrySector(StringUtil.toString(ops.get(fetchKey, "industrySector")));
            bean.setIssueDate(DateUtil.getDate(StringUtil.toString(ops.get(fetchKey, "issueDate"))));
            bean.setIssuer(StringUtil.toString(ops.get(fetchKey, "issuer")));
            bean.setMod1(StringUtil.toString(ops.get(fetchKey, "marketSectorDes")));
            bean.setMaturityDate(DateUtil.getDate(StringUtil.toString(ops.get(fetchKey, "maturity"))));
            bean.setMoodyRating(StringUtil.toString(ops.get(fetchKey, "moodyRating")));
            bean.setSecDesc(StringUtil.toString(ops.get(fetchKey, "securityDes")));
            bean.setSpRating(StringUtil.toString(ops.get(fetchKey, "spRating")));
            bean.setTicker(StringUtil.toString(ops.get(fetchKey, "ticker")));
        }
        return bean;
    }

}
