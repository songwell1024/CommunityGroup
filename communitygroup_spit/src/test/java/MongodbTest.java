import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @program:
 * @description: 原生的java操作mongo
 * 也需要引入依赖
 * <dependency>
 * <groupId>org.mongodb</groupId>
 * <artifactId>mongodb‐driver</artifactId>
 * <version>3.6.3</version>
 * </dependency>
 * @author: Song
 * @create: Created in 2019-02-24 09:23
 * @Modified by:
 **/
public class MongodbTest {

    public static void main(String[] args) {
        //连接服务器
        MongoClient client = new MongoClient("121.248.48.100");
        //连接数据库
        MongoDatabase spitdb = client.getDatabase("spitdb");
        //连接数据库中的集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");
//      //查询
//        //相当于 db.spit.find({visits:{$gt:2000}})
//        BasicDBObject bson = new BasicDBObject("visits", new BasicDBObject("$gt", 2000));
//        FindIterable<Document> documents = spit.find(bson);
//
//        for (Document document : documents){
//            System.out.println(document.getString("content"));
//            System.out.println(document.getString("userid"));
//        }

//        //插入
//        Map<String, Object> map = new HashMap<>();
//        map.put("_id", 5);
//        map.put("content","我要吐槽");
//        map.put("userid","9999");
//        map.put("visits",123);
//        map.put("nickname","小花");
//        Document document = new Document(map);
//        spit.insertOne(document);

        //修改
//        BasicDBObject bson1 = new BasicDBObject("_id","5");
//        BasicDBObject bson2 = new BasicDBObject("userid","9999");
//        spit.updateOne(bson2, bson1);
        client.close();

    }

}
