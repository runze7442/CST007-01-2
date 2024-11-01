package com.icourse.service.impl;

import com.icourse.dao.NewsHeadlineDao;
import com.icourse.dao.impl.NewsHeadlineDaoImpl;
import com.icourse.pojo.NewsHeadline;
import com.icourse.pojo.vo.HeadlineDetailVo;
import com.icourse.pojo.vo.HeadlinePageVo;
import com.icourse.pojo.vo.HeadlineQueryVo;
import com.icourse.service.NewsHeadlineService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsHeadlineServiceImpl implements NewsHeadlineService {
    private NewsHeadlineDao headlineDao =new NewsHeadlineDaoImpl();
    /*
        totalPage:1,
        totalSize:1
        pageData:[
                    {
                       "hid":"1",                     // id
                        "title":"尚硅谷宣布 ... ...",   // 标题
                        "type":"1",                    // 所属类别编号
                        "pageViews":"40",              // 浏览量
                        "pastHours":"3",              // 发布时间已过小时数
                        "publisher":"1"
                   }
                 ],
        pageNum:1,
        pageSize:1,


     */
    @Override

    public Map findPage(HeadlineQueryVo headlineQueryVo) {
        int pageNum = headlineQueryVo.getPageNum();
        int pageSize = headlineQueryVo.getPageSize();
        List<HeadlinePageVo> pageData = headlineDao.findPageList(headlineQueryVo);
        int totalSize = headlineDao.findPageCount(headlineQueryVo);

        int totalPage =totalSize % pageSize ==0 ? totalSize/pageSize: totalSize/pageSize+1;
        Map pageInfo= new HashMap();
        pageInfo.put("pageNum",pageNum);
        pageInfo.put("pageSize",pageSize);
        pageInfo.put("totalSize",totalSize);
        pageInfo.put("totalPage",totalPage);
        pageInfo.put("pageData",pageData);
        return pageInfo;
    }

    @Override
    public HeadlineDetailVo findHeadlineDetail(int hid) {
        // 修改该头条的浏览量  +1
        headlineDao.incrPageViews(hid);
        // 查询头条的详情
        return headlineDao.findHealineDetail(hid);
    }

    @Override
    public int addNewsHeadline(NewsHeadline newsHeadline) {
        return headlineDao.addNewsHeadline(newsHeadline);
    }

    @Override
    public NewsHeadline findByHid(Integer hid) {
        return headlineDao.findByHid(hid);
    }

    @Override
    public int update(NewsHeadline newsHeadline) {
        return headlineDao.update(newsHeadline);
    }

    @Override
    public int removeByHid(int hid) {
        return headlineDao.removeByHid(hid);
    }
}
