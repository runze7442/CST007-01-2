package com.icourse.service.impl;

import com.icourse.dao.NewsTypeDao;
import com.icourse.dao.impl.NewsTypeDaoImpl;
import com.icourse.pojo.NewsType;
import com.icourse.service.NewsTypeService;

import java.util.List;

public class NewsTypeServiceImpl implements NewsTypeService {
    private NewsTypeDao typeDao =new NewsTypeDaoImpl();
    @Override
    public List<NewsType> findAll() {
        return typeDao.findAll();
    }
}
