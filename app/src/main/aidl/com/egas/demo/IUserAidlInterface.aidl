package com.egas.demo;

import com.egas.demo.bean.User; //引入data类

interface IUserAidlInterface {
     boolean addUser(in User user);
}