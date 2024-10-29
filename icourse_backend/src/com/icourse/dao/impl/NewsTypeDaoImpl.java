package com.icourse.dao.impl;

import com.icourse.dao.BaseDao;
import com.icourse.dao.NewsTypeDao;
import com.icourse.pojo.NewsType;

import java.util.List;

public class NewsTypeDaoImpl extends BaseDao implements NewsTypeDao {
    @Override
    public List<NewsType> findAll() {
        String sql = "select tid,tname from news_type";
        return baseQuery(NewsType.class,sql);
    }
}
