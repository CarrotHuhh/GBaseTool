<div style="text-align: center;"><h1>GBase Debug Tool</h1></div>

# 1. 项目目标

​		开发一个Java命令行工具，用以确定用户使用Gbase集群过程中产生的错误源于用户端或是集群端。实现连接排查、字符集排查、切换驱动连接测试以及自定义SQL语句查询测试等功能。对于不同的测试需求，通过指定参数的方式选择不同的模式进行测试。

# 2. 项目需求分析

## 2.1 运行环境

​		本项目最终为一个由Java工程打包生成的可运行Jar，当用户在使用集群产生错误时，可在本地系统直接运行Jar包以辅助定位问题产生位置。

## 2.2 问题排查

1. 连接排查
   	对于用户连接问题的定位，需要设计一个可运行的简单JDBC程序，在修改外部参数文件中用户登录参数后使用该程序进行登录尝试。若登录成功，则可判断用户端配置出现问题；若登录失败，则考虑集群端问题。

- 集群端问题报错
  未启动：Communications link failure

- 用户端问题报错

​		密码问题：Access denied for user 'gbase'@'172.16.34.14' (using password: YES)
​		网络问题：Communications link failure

2. 字符集排查
   	对于用户字符集问题的排查，需要先读取下图中关于集群如下选项的字符集配置，后在终端中隐式输出这些配置。为了确认集群端运行正常，在项目工程中应准备各类编码文件，根据集群中字符集配置进行选择加载。，在加载成功后对查询结果进行输出。

   <center><img src = "https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/%E6%88%AA%E5%B1%8F2023-05-12%2014.19.58.jpg" style="zoom:50%"/></center>

<div style="text-align: center;">图 2-1</div>

3. 切换驱动连接

  ​		该模块用以测试用户环境对各种JDBC驱动的支持情况。项目需要对需要测试的驱动文件进行识别，并根据不同驱动实现对应的连接配置。

4. 自定义SQL语句查询

  ​		该模块用以测试客户端与集群端连接中各类SQL语句的工作情况，项目需读取外部输入的SQL语句，并针对不同类型SQL语句实现对应代码，并返回SQL语句结果。

# 3. 项目设计

## 3.1 UML时序图

### 3.1.1 连接故障排查时序图

<center><img src="https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/%E5%BE%AE%E4%BF%A1%E6%89%AB%E6%8F%8F%E4%BA%8C%E7%BB%B4%E7%A0%81%E6%94%AF%E4%BB%98%E6%97%B6%E5%BA%8F%E5%9B%BE-%E5%AF%BC%E5%87%BA.png" style="zoom:60%"/></center>
<div style="text-align: center;">图 3-1</div>

### 3.1.2 字符集错误排查时序图

<center><img src="https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/%E5%BE%AE%E4%BF%A1%E6%89%AB%E6%8F%8F%E4%BA%8C%E7%BB%B4%E7%A0%81%E6%94%AF%E4%BB%98%E6%97%B6%E5%BA%8F%E5%9B%BE.png" style="zoom:60%"/></center>
<div style="text-align: center;">图 3-2</div>

### 3.1.3 切换驱动测试时序图

<center><img src="https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/3751684133438_.pic-20230515145305560.jpg" style="zoom:50%"/></center>
<div style="text-align: center;">图 3-3</div>

### 3.1.4 自定义SQL语句测试时序图

<center><img src="https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/3771684134647_.pic.jpg" style="zoom:70%"/></center>
<div style="text-align: center;">图 3-4</div>

## 3.2 UML包图

<center><img src="https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/0.png" style="zoom:30%"/></center>
<div style="text-align: center;">图 3-5</div>

系统主体分为Controller、Service、Dao三层，作用分别为：

- Controller: 负责向主函数暴露各业务接口，调用对应业务层对应业务逻辑。
- Service: 负责完整业务线中主要的逻辑，对Dao层返回的数据进行处理。
- Dao: 负责提供与数据库的不同交互接口，是系统与数据库直接发生联系的一层，Connection对象在此层进行构造，构造时从配置文件中读取用户的配置。



## 3.3 UML类图



## 3.4 打包方式以及项目运行方式

1) 在项目根目录下创建dist文件夹，用于存放打包后的jar文件。
2) 在项目的构建路径中添加所有需要打包的类和资源文件。
3) 在项目的主类中添加一个入口点，用于在执行jar文件时启动应用程序。
4) 对项目进行打包。
5) 通过命令如java -jar GBaseTool.jar mode1 mode2运行jar包，其中mode1、mode2为传入函数主入口的参数，项目通过对参数的识别以启动对应的模式。

# 4.项目结构

```
GBaseToolDemo
├── GbaseToolDemo.iml
├── README.md
├── dist
├── lib
│   ├── gbase-connector-java-9.5.0.1-build1-bin.jar
│   └── mysql-connector-java-8.0.28.jar
├── out
├── resource
│   └── connection.properties
└── src
    └── com
        └── gbase
            ├── Main.java
            ├── controller
            │   ├── CharacterSetController.java
            │   ├── ConnectionController.java
            │   ├── DriversController.java
            │   └── SqlController.java
            ├── service
            │   ├── CharacterSetService.java
            │   ├── ConnectionService.java
            │   ├── DriversService.java
            │   ├── SqlService.java
            │   └── impl
            │       ├── CharacterSetServiceImpl.java
            │       ├── ConnectionServiceImpl.java
            │       ├── DriversServiceImpl.java
            │       └── SqlServiceImpl.java
            ├── dao
            │   ├── CharacterSetDao.java
            │   ├── ConnectionDao.java
            │   ├── DriversDao.java
            │   └── SqlDao.java
            └── util
                └── Preparations.java
```

# 5. 测试模式

测试连接正常：
	模式一：读取配置文件配置->尝试连接->返回连接结果->完成
测试连接与sql语句执行正常：
	模式二：设置配置文件中sql与连接配置->进行连接->执行sql->完成
测试连接、sql语句执行、驱动切换加载正常
	模式三：进入循环->设置配置文件中驱动种类->读取配置进行连接->执行sql->释放连接->循环等待重新配置驱动种类->刷新读取配置进行连接->执行sql->完成
测试连接、sql语句执行、字符集加载正常
	模式四：读取配置->进行连接->执行sql读取数据库中字符集配置->显式输出读取结果->插入指定编码数据->执行sql查询插入数据->返回终端进行比对->完成
测试连接、sql语句执行、驱动切换加载、字符集加载正常
	模式五：驱动切换->读取配置->进行连接->执行sql读取数据库中字符集配置->显式输出读取结果->插入指定编码数据->执行sql查询插入数据->返回终端进行比对->完成
