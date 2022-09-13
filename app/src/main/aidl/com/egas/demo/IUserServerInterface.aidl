package com.egas.demo;

import com.egas.demo.bean.User; //引入data类

interface IUserServerInterface {
     List<User> getUsers();
     void addUser(User user);
}