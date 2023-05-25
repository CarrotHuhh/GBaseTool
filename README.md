<div style="text-align: center;"><h1>GBase Tool开发文档</h1></div>

# 1. 项目目标

开发一个Java命令行工具，用以确定用户使用Gbase集群过程中产生的错误源于用户端或是集群端。实现连接排查、字符集排查、切换驱动连接测试以及自定义SQL语句查询测试等功能。对于不同的测试需求，通过指定参数的方式选择不同的模式进行测试。

# 2. 项目需求分析


## 2.1 运行环境

本项目最终为一个由Java工程打包生成的可运行Jar，当用户在使用集群产生错误时，可在本地系统直接运行Jar包以辅助定位问题产生位置。

## 2.2 功能需求

### 2.2.1 连接排查
   对于用户连接问题的定位，需要设计一个可运行的简单JDBC程序，在修改外部参数文件中用户登录参数后使用该程序进行登录尝试。若登录成功，则可判断用户端配置出现问题；若登录失败，则考虑集群端问题。

### 2.2.2 字符集排查
   对于用户字符集问题的排查，需要先读取下图中关于集群如下选项的字符集配置，后在终端中显式输出这些配置。为了确认数据库端运行正常，在输出数据库字符集编码配置之后，向数据库中插入对应编码字符，并在进行查询之后输出至终端，以进行比对。

   <center><img src = "https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/%E6%88%AA%E5%B1%8F2023-05-12%2014.19.58.jpg" style="zoom:50%"/></center>

<div style="text-align: center;">图 2-1</div>

### 2.2.3 切换驱动连接

该模块用以测试用户环境对各种JDBC驱动的支持情况。项目需要对需要测试的驱动文件进行识别，并根据不同驱动实现对应的连接配置。

### 2.2.4 自定义SQL语句查询

该模块用以测试客户端与集群端连接中各类SQL语句的工作情况，项目需读取外部输入的SQL语句，并针对不同类型SQL语句实现对应代码，并返回SQL语句结果。

# 3. 项目设计

## 3.1 UML时序图

### 3.1.1 Mode5时序图

<center><img src="https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/00.png" style="zoom:60%"/></center>
<div style="text-align: center;">图 3-1</div>

## 3.2 UML类图

![类图](https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/%E7%B1%BB%E5%9B%BE.png)

## 3.3 测试模式

### 模式一：测试连接正常

读取配置文件配置->尝试连接->返回连接结果->完成

![1](https://github.com/CarrotHuhh/Pics/blob/main/img/1.png?raw=true)

### 模式二：测试连接与sql语句执行正常

设置配置文件中sql与连接配置->进行连接->执行sql->完成

![2](https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/2.png)

### 模式三：测试连接、sql语句执行、驱动切换加载正常

进入循环->终端输入驱动种类->读取配置进行连接->执行sql->释放连接->循环等待重新配置驱动种类->
刷新读取配置进行连接->执行sql->完成

![5881684981344_.pic](https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/5881684981344_.pic.jpg)

### 模式四：测试连接、sql语句执行、字符集加载正常

读取配置->进行连接->执行sql读取数据库中字符集配置->显式输出读取结果->插入指定编码数据->执行sql查询插入数据->
返回终端进行比对->完成

![4](https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/4.png)

### 模式五：测试连接、sql语句执行、驱动切换加载、字符集加载正常

选择驱动->读取配置->进行连接->执行sql读取数据库中字符集配置->显式输出读取结果->插入指定编码数据->
执行sql查询插入数据->返回终端进行比对->完成

![5](https://cdn.jsdelivr.net/gh/CarrotHuhh/Pics@main/img/5.png)


## 3.4 打包方式以及项目运行方式

1) 在项目根目录下创建dist文件夹，用于存放打包后的jar文件。
2) 在项目的构建路径中添加所有需要打包的类和资源文件。
3) 在项目的主类中添加一个入口点，用于在执行jar文件时启动应用程序。
4) 对项目进行打包。
5) 通过命令如```java -jar GBaseTool.jar mode```运行jar包，其中mode为传入函数主入口的参数，项目通过对参数的识别以启动对应的模式。在程序启动前，用户需在配置文件中完成数据库的相关登录配置，程序启动后，控制台会进行对应操作的引导。


# 4.项目结构
## 4.1 源代码工程结构

```
GBaseToolDemo
├── README.md
├── dist---------------------------------------项目打包输出目录
│   ├── GbaseToolDemo.jar
│   ├── README.md
│   ├── connection.properties
│   └── jar
│       ├── gbase-connector-java-9.5.0.1-build1-bin.jar
│       └── mysql-connector-java-8.0.28.jar
└── src
    └── com
        └── gbase
            ├── Exceptions----------------------自定义异常类
            │   └── LoadJarException.java
            ├── Main.java
            ├── mode----------------------------模式类
            │   ├── Mode1.java
            │   ├── Mode2.java
            │   ├── Mode3.java
            │   ├── Mode4.java
            │   └── Mode5.java
            └── utils----------------------------功能方法及配置封装类
                ├── CharacterSetUtils.java
                ├── ConnectionUtils.java
                ├── JarUtils.java
                └── SqlUtils.java

```


# 5. 工具目录结构说明

```
.
├── GbaseToolDemo.jar----------------------工具主体可运行Jar包
├── README.md------------------------------说明文档
├── connection.properties------------------配置文件
└── jar------------------------------------导入驱动Jar包目录
    ├── gbase-connector-java-9.5.0.1-build1-bin.jar
    └── mysql-connector-java-8.0.28.jar
```

## 5.1 配置文件说明

### 5.1.1配置文件示例

```
jarName=gbase-connector-java-9.5.0.1-build1-bin.jar
driver=com.gbase.jdbc.Driver
#gbase相关配置
url-gbase=jdbc:gbase://172.16.34.20:5258/db1?vcName=vc1
user-gbase=gbase
password-gbase=123456
#mysql相关配置
url-mysql=jdbc:mysql://localhost:3306/db1?
user-mysql=root
password-mysql=123456
```

### 5.1.2驱动文件配置

在配置文件中，若要指定数据库所用驱动，需先将驱动jar包导入jar目录，并在jarName属性中指明jar包文件名以及在driver属性中指明驱动的类名。

### 5.1.3数据库连接的登录配置

数据库连接时，需指定连接用url，用户名以及密码。配置参照示例文件：“属性-数据库名”，数据库名需与包名对应。例如url-gbase中的gbase与com.gbase.jdbc.Driver中的gbase对应。

# 6.工具运行说明

## 6.1模式说明

模式一、模式二、模式四中，在启动工具与与数据库建立连接前需在配置文件中指定驱动并完成登录配置；若进行模式三及模式五无需提前指定驱动，但需要完成登录配置。

### 6.1.1模式一

简单用于测试客户端与数据库连接的模式，可检测客户端与数据库连接是否正常。
启动命令：

```bash
java -jar GbaseToolDemo.jar mode1
```

### 6.1.2模式二

用于检测数据库对不同SQL语句执行情况的模式，可检测数据库执行SQL语句是否正常。
启动命令：

```bash
java -jar GbaseToolDemo.jar mode2
```

### 6.1.3模式三

用于测试客户端切换驱动连接不同数据库，并检测数据库对不同SQL语句执行情况的模式，可检测数据库执行SQL语句是否正常以及客户端对不同数据库的连接是否正常。
启动命令：

```bash
java -jar GbaseToolDemo.jar mode3
```

### 6.1.4模式四

用于测试数据库对不同SQL语句的处理情况，以及字符集相关配置是否正常。
启动命令：

```bash
java -jar GbaseToolDemo.jar mode4
```

### 6.1.5模式五

本类集成了其他四个模式的测试，通过Mode5可覆盖连接、切换驱动、自定义SQL语句以及字符集配置情况。
启动命令：

```bash
java -jar GbaseToolDemo.jar mode5
```