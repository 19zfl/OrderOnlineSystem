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
**src\jdbc\LoginServlet.java
src\jdbc\RegisterServlet.java
src\util\DBUtil.java** 

这三个文件中的 *jdbc:mysql://localhost:3306/test", "root", "123456"* 
tset和123456需要自己创建数据库和改成自己的密码

-------
