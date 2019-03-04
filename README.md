# generator-share
代码生成器(可根据数据库表信息生成controller，service，dao，bean等)

概览：
1该工程使用的是spring boot +spring +spring cloud +mybatis 框架来curd，太简单，so如果框架搭建等问题请私信我，本人提交的工程在本地测试是能curd的。
2该工程的特点是代码生成器，它的原理是读取数据库中的字段定义和名称，结合模板引擎velocity来生成工程中的controller，service，dao，bean等，现阶段
只做到了单表的增删改查。

入门：
1修改generator.properties中的包名和驱动（该代码生成器可以连接mysql,oracle）。
2根据框架的业务代码修改resources中的template文件。
3修改generator.java 文件中的表名和包名，这样就可以一键运行生成代码了。

总结：
1现阶段的代码生成器还是初级阶段，但针对一般公司的简单业务是不用写单表的curd了，我们要做的是就是写复杂逻辑了。
2模板的思想也可以扩展到生成交付文档，前端统一化结构代码等等。
3有利于代码规范和数据库规范。

展望：
现阶段的代码生成器功能比较少，我的想法是把数据库中的各个表读取到页面上，根据页面上的拖拽勾选来动态生成controller，service，dao接口，mybatis/sql 代码.同时提供简单的数据操作算法，
比如二分查找，数据去重等算法，自动取别名；从而实现界面化编程。
