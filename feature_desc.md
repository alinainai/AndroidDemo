## 分支名称
feature_transform_v1
## 分支内容
介绍自定义 Gradle Plugin 的方法和代码内容
## 操作
1、第一次编译不通过，在 app 的 build.gradle 中注释掉

```groovy
//id 'com.gas.gradleplugin'
```
2、发布 gradleplugin 中的 自定义 CusPlugin 

点击 右侧 gradle 侧边栏中的 pulishing->publish 即可。

3、放开注释，重新build
```groovy
 id 'com.gas.gradleplugin'
```
4、查看日志
```shell
> Configure project :app
Hello CustomPlugin
```