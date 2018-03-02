package com.ssm.dao;

import com.ssm.domain.User;

/**
 * Created by 蓝鸥科技有限公司  www.lanou3g.com.
 */
public interface UserDao {

    User selectByUserName(String userName);
}
