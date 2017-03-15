package com.sumridge.smart.query;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sumridge.smart.bean.BestMktDataBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liu on 17/3/6.
 */
@Component
public class CusipMktDataQuery {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<BestMktDataBean> getMktDataSimpleByCusip(Set<String> cusipSet) {
        if(cusipSet != null && cusipSet.size() > 0) {

            Query query = Query.query(Criteria.where("cusip").in(cusipSet));
            query.fields().include("cusip").include("bidBestPrice").include("bidBestQty").include("sourceIdBestBid")
                    .include("askBestPrice").include("askBestQty").include("sourceIdBestAsk");
            List<BasicDBObject> list = mongoTemplate.find(query, BasicDBObject.class, "cusipMktData");

            return list.stream().map(dbObject -> {
                BestMktDataBean bestMktDataBean = new BestMktDataBean();
                bestMktDataBean.setCusip(dbObject.getString("cusip"));
                bestMktDataBean.setAskSourceId(dbObject.getInt("sourceIdBestAsk", 0));
                bestMktDataBean.setAskQty(dbObject.getInt("askBestQty", 0));
                bestMktDataBean.setAskPrice(dbObject.getDouble("askBestPrice", 0));
                bestMktDataBean.setBidPrice(dbObject.getDouble("bidBestPrice", 0));
                bestMktDataBean.setBidQty(dbObject.getInt("bidBestQty",0));
                bestMktDataBean.setBidSourceId(dbObject.getInt("sourceIdBestBid",0));
                return bestMktDataBean;
            }).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public List<BestMktDataBean> getMktDataByCusip(Set<String> cusipSet) {

        if(cusipSet != null && cusipSet.size() > 0) {

            //bid price
            AggregationResults<DBObject> resultsBid = mongoTemplate.aggregate(getBidAggregation(cusipSet), "cusipMktData", DBObject.class);


            //ask price
            AggregationResults<DBObject> resultsAsk = mongoTemplate.aggregate(getAskAggregation(cusipSet), "cusipMktData", DBObject.class);

            //set result set
            return setCusipDataResult(cusipSet, resultsAsk.getMappedResults(), resultsBid.getMappedResults());
        } else {
            return null;
        }
    }

    private List<BestMktDataBean> setCusipDataResult(Set<String> cusipSet, List<DBObject> mappedResultsAsk, List<DBObject> mappedResultsBid) {

        Map<String, BasicDBObject> mapAsk = getStringBasicDBObjectMap(mappedResultsAsk);
        Map<String, BasicDBObject> mapBid = getStringBasicDBObjectMap(mappedResultsBid);

        List<BestMktDataBean> list = cusipSet.stream().map(cusip -> {
            BestMktDataBean bestMktDataBean = new BestMktDataBean();
            bestMktDataBean.setCusip(cusip);
            BasicDBObject askObj = mapAsk.get(cusip);
            BasicDBObject bidObj = mapBid.get(cusip);

            setBestData(bestMktDataBean, askObj, bidObj);
            return bestMktDataBean;
        }).collect(Collectors.toList());

        return list;
    }

    private void setBestData(BestMktDataBean bestMktDataBean, BasicDBObject askObj, BasicDBObject bidObj) {

        if(askObj != null) {
            bestMktDataBean.setAskPrice(askObj.getDouble("price"));
            bestMktDataBean.setAskQty(askObj.getInt("qty"));
            bestMktDataBean.setAskSourceId(askObj.getInt("sourceId"));
        }

        if(bidObj != null) {
            bestMktDataBean.setBidPrice(bidObj.getDouble("price"));
            bestMktDataBean.setBidQty(bidObj.getInt("qty"));
            bestMktDataBean.setBidSourceId(bidObj.getInt("sourceId"));
        }
    }

    private Map<String, BasicDBObject> getStringBasicDBObjectMap(List<DBObject> mappedResults) {
        Comparator<BasicDBObject> qtyComparator = (o1, o2) -> {
            Integer qty1 = (Integer) o1.get("qty");
            Integer qty2 = (Integer) o2.get("qty");
            return qty1.compareTo(qty2);
        };

        return mappedResults.stream().map(dbObject -> {
            BasicDBList list = (BasicDBList) dbObject.get("data");
            String cusip = (String) dbObject.get("_id");
            Optional<BasicDBObject> smrdResult = list.stream().map(basic -> (BasicDBObject) basic).filter(basic -> (int) basic.get("sourceId") == 0).max(qtyComparator);
            if(smrdResult.isPresent()) {
                return Pair.of(cusip, smrdResult.get());
            } else {
                Optional<BasicDBObject> result = list.stream().map(basic -> (BasicDBObject) basic).max(qtyComparator);
                return Pair.of(cusip, result.get());
            }
        }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }


    private Aggregation getBidAggregation(Set<String> cusipSet) {
        return Aggregation.newAggregation(
                Aggregation.match(Criteria.where("cusip").in(cusipSet))
                , Aggregation.unwind("$mktDatas")
                , Aggregation.match(Criteria.where("mktDatas.bidQty").gt(0))
                , Aggregation.group("$cusip").max("$mktDatas.bidPrice").as("price")
                        .push(new BasicDBObject("bidQty", "$mktDatas.bidQty")
                                .append("bidPrice", "$mktDatas.bidPrice")
                                .append("sourceId", "$mktDatas.sourceId"))
                        .as("data")
                , Aggregation.unwind("$data")
                , Aggregation.project().andInclude("_id","price","data.bidQty","data.bidPrice","data.sourceId").and(ComparisonOperators.valueOf("$price").compareTo("$data.bidPrice")).as("cmp")
                , Aggregation.match(Criteria.where("cmp").is(0))
                , Aggregation.group("$_id")
                        .push(new BasicDBObject("qty", "$bidQty")
                                .append("price", "$bidPrice")
                                .append("sourceId", "$sourceId")).as("data")
        );
    }

    private Aggregation getAskAggregation(Set<String> cusipSet) {
        return Aggregation.newAggregation(
                Aggregation.match(Criteria.where("cusip").in(cusipSet))
                , Aggregation.unwind("$mktDatas")
                , Aggregation.match(Criteria.where("mktDatas.askQty").gt(0))
                , Aggregation.group("$cusip").min("$mktDatas.askPrice").as("price")
                        .push(new BasicDBObject("askQty", "$mktDatas.askQty")
                                .append("askPrice", "$mktDatas.askPrice")
                                .append("sourceId", "$mktDatas.sourceId"))
                        .as("data")
                , Aggregation.unwind("$data")
                , Aggregation.project().andInclude("_id","price","data.askQty","data.askPrice","data.sourceId").and(ComparisonOperators.valueOf("$price").compareTo("$data.askPrice")).as("cmp")
                , Aggregation.match(Criteria.where("cmp").is(0))
                , Aggregation.group("$_id")
                        .push(new BasicDBObject("qty", "$askQty")
                                .append("price", "$askPrice")
                                .append("sourceId", "$sourceId")).as("data")
        );
    }
}
