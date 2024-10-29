package com.icourse.service;

import com.icourse.pojo.NewsHeadline;
import com.icourse.pojo.vo.HeadlineDetailVo;
import com.icourse.pojo.vo.HeadlineQueryVo;

import java.util.Map;

public interface NewsHeadlineService {
    /**
     *
     * @param headlineQueryVo
     * @return
     */
    Map findPage(HeadlineQueryVo headlineQueryVo);

    /**
     *
     * @param hid
     * @return
     */
    HeadlineDetailVo findHeadlineDetail(int hid);

    /**
     *
     * @param newsHeadline
     */
    int addNewsHeadline(NewsHeadline newsHeadline);

    /**
     *
     * @param hid
     * @return
     */
    NewsHeadline findByHid(Integer hid);

    int update(NewsHeadline newsHeadline);


    /**
     *
     * @param hid
     * @return
     */
    int removeByHid(int hid);
}
