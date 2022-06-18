<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/pages/head.jsp">
	<jsp:param value="用户首页" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/pages/user/user_nav.jsp">
		<jsp:param value="index" name="fun" />
	</jsp:include>
	<!-- 搜索栏 -->
	<div class="container-fluid">
		<div class="card border">
			<div class="card-header border">
			<form class="form-inline" action="<%=request.getContextPath() %>/user/user_index.action" method="post">
			 <input type="search" class="form-control mr-sm-2" placeholder="按菜名搜索" name="s_fn" value="">
			 <select class="form-control mr-sm-2" name="s_type">
			  <option value="">所有分类</option>
			  <!-- 获取所有foodtype，循环遍历 -->
			  <%
			  List<Map<String,String>> types= (List<Map<String,String>>)request.getAttribute("types");
			  for(Map<String,String> type:types){
			  %>
			  <option value="<%=type.get("id")%>" ><%=type.get("typename") %></option>
			  <%} %>
			 </select>
			 <button type="submit" class="btn btn-online-success my-2 my-sm-0">搜索</button>
			</form>
			</div>
			<!-- 菜品选择列表 -->
			<%
			List<Map<String,String>> foods= (List<Map<String,String>>)request.getAttribute("foods");
			if(foods!=null){
			%>
			<div class="card-body">
			<form action="<%=request.getContextPath() %>/user/user_add_dc.action" method="post">
			<div class="table-responsive-xl">
			  <table class="table table-striped table-hover table-sm">
			    <thead>
			      <tr><th scope="col">#</th><th scope="col">菜名</th>
			      <th scope="col">特色</th><th scope="col">主料</th>
			      <th scope="col">价格</th><th scope="col">分类</th>
			      <th scope="col">图片</th><th scope="col">点餐率</th>
			      <th scope="col">备注</th><th scope="col">选择</th>
			      </tr>
			    </thead>
			  <%
			     int num=0; 
			    for(Map<String,String> food:foods){
			    	num++;
			  %>
			     <tr>
			      <th scope="row"><%=num %></th><td><%=food.get("foodname") %></td>
			      <td><%=food.get("feature") %></td><td><%=food.get("material") %></td>
			      <td><%=food.get("price") %></td><td><%=food.get("typename") %></td>
			      <td><img class="img-rounded"  src="<%=request.getContextPath() %>/<%=food.get("picture")%>"></td>
			      <td><%=food.get("hits") %></td>
			      <td>
			      <%
							    if(food.get("comment").equals("0")) out.print("厨师推荐");
							    else if(food.get("comment").equals("-1")) out.print("");
							    else out.print("特价"+food.get("comment")+"元");
					 %>
                  </td>
			      <td><input type="checkbox" name="ids" value="<%=food.get("id")%>"></td>
			      </tr>
			  <%} %>
			  </table>
			  </div>
			  <div class="form-group">
			   <button type="submit" class="btn btn-success btn-block">将菜品添加到点餐车</button>
			  </div>
			</form>
			</div>
			<%} %>
		</div>
	</div>
</body>
</html>