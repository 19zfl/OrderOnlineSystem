# 网络点餐系统

### 导包+配置
**eclipse**
-   import包
-   右键包名-Build Path-Configure Build Path里面
-   找到Java Build Path中的Libraries
-   JRE System Library改成自己系统的jdk版本
-   点击后再点击右边的edit
-   我一般选择Workspace default JRE
-   配置好之后Server Runtime也需要配置自己系统的Tomcat版本
-   这两个配置好后apply and close

**ItelliJ Idea**
-   idea我不建议导包，自己创建web项目后将相应的文件移过去就行了
-------
### 注意：

**src\jdbc\LoginServlet.java**

**src\jdbc\RegisterServlet.java**

**src\util\DBUtil.java** 

这三个文件中的 *jdbc:mysql://localhost:3306/test", "root", "123456"* 
tset和123456需要自己创建数据库和改成自己的密码

**创建好数据库之后：**

在数据库的表中创建dinnercar，food，foodtype，user

dinnercar：

| id | userid | foodid |
| --- | --- | --- |
| 1 | 3 | 7 |
| 2 | 4 | 1 |

多建一些比较好

food：

| id | foodname | feature | material | price | type | picture | hits | comment |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | name1 | null | null | null | null | null | null | null |
| 2 | name1 | null | null | null | null | null | null | null |

id:
| id | typename |
| --- | --- |
| 1 | name1 |
| 2 | name2 |

user:

| id | username | password | ident | telephone | address |
| --- | --- | --- | --- | --- | --- |
| 1 | name | 123 | 1 | xxxx | xxx |
| 2 | name | 123 | 0 | xxxx | xxx |
