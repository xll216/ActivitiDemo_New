package com.ssm.service;

import com.ssm.domain.User;

/**
 * Created by 蓝鸥科技有限公司  www.lanou3g.com.
 */
public interface UserService {

    User selectByUserName(String userName);
}
