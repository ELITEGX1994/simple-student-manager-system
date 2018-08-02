# simple-student-manager-system
//gx
简单的学生管理系统(自我学习用)
开发工具是eclipse+mysql
首先在mysql数据库中新建admin和student两个表，admin用于存储管理员信息，student用于存储学生信息。
主要包括5个模块
1.Main模块包含Constant和MainRun类：Constant类存储项目中用到的常量，MainRun类是入口main方法，新建一个登陆界面。
2.View模块:视图模块，包括登陆界面视图，包括登陆界面，主界面，增删改查界面
3.DAO模块：数据访问层，提供数据访问操作的接口函数
4.DBUtil模块：数据库工具类，包括增删改查操作，实现DAO模块的具体函数
5.Model模块：模型模块，包括admin和student类
