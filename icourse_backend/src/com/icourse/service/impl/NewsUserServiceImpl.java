package com.icourse.service.impl;

import com.icourse.dao.NewsUserDao;
import com.icourse.dao.impl.NewsUserDaoImpl;
import com.icourse.pojo.NewsUser;
import com.icourse.service.NewsUserService;
import com.icourse.util.MD5Util;

public class NewsUserServiceImpl implements NewsUserService {

    private NewsUserDao userDao =new NewsUserDaoImpl();
    @Override
    public NewsUser findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public NewsUser findByUid(Integer userId) {
        return userDao.findByUid(userId);
    }

    @Override
    public Integer registUser(NewsUser registUser) {
        // 处理增加数据的业务
        // 将明文密码转换成密文密码
        registUser.setUserPwd(MD5Util.encrypt(registUser.getUserPwd()));

        return userDao.insertUser(registUser);
    }
}
