package com.egas.demo;

import com.egas.demo.bean.User; //引入data类
import com.egas.demo.OnUserAddListener;

interface IUserAidlInterface {

     boolean addUser(in User user);

     // 1、out 和 inputout
     boolean addUserOut(out User user);
     boolean addUserInout(inout User user);

     // 2、oneway 关键字，可以把 oneway 去掉试试，看下时间
//      void addPersonOneway(in User user);
     oneway void addPersonOneway(in User user);

     // 3、数据变化监听器注册和反注册
    void addListener(OnUserAddListener listener);
    void removeListener(OnUserAddListener listener);

}
