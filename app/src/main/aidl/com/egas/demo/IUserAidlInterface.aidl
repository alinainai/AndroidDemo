package com.egas.demo;

import com.egas.demo.bean.User; //引入data类

interface IUserAidlInterface {
     List<User> getUsers();
     boolean addUser(in User user);
}