package com.communitygroup.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-25 09:21
 * @Modified by:
 **/
@Document(indexName="communitygroup_article",type="article")
public class Article implements Serializable {

    @Id
    private String id;//ID

    //是否索引 就是是否能被搜索到
    //是否分词 就是整体匹配还是分词匹配
    //是否存储 就是是否存储到索引库并展示
    //analyzer是存储时的分词器 searchAnalyzer是搜索时的分词器，必须前后一致，否则搜索出来的结果不一致

//  // 必须需要@Field属性，因为某些字段需要分词,在@field属性中再加上一个字段（type ），表明自己定义的该字段对应elasticsearch已近定义好的字段类型（出现该错误主要是由于该问题），如下
    @Field(index = true, analyzer="ik_max_word", searchAnalyzer="ik_max_word")
    private String title;//标题

    @Field(index= true , analyzer="ik_max_word", searchAnalyzer="ik_max_word")
    private String content;//文章正文

    private String state;//审核状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
