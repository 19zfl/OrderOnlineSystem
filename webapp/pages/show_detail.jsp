<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"  import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="head.jsp">
	<jsp:param value="菜品详细信息" name="title" />
</jsp:include>
<style>
table.table tr th {
	vertical-align: middle;
	text-align: center;
	width: 30%
}
</style>
</head>
<body>
  <%
  Map<String,String> food=(Map<String,String>)request.getAttribute("food");
  if(food!=null){
  %>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 col-0"></div>
			<div class="col-md-6 col-12"">
				<table class="table table-hover table-bordered">
					<tbody>
						<tr class="table-primary">
							<th scope="col">菜名</th>
							<td><%=food.get("foodname") %></td>
						</tr>
						<tr class="table-secondary">
							<th scope="row">特色</th>
							<td><%=food.get("feature") %></td>
						<tr class="table-success">
							<th scope="row">食材</th>
							<td><%=food.get("material") %></td>
						</tr>
						<tr class="table-danger">
							<th scope="row">类型</th>
							<td><%=food.get("typename") %></td>
						</tr>
						<tr class="table-warning">
							<th scope="row">价格</th>
							<td><%=food.get("price") %></td>
						<tr class="table-info">
							<th scope="row">图片</th>
							<td><img class="img-rounded" alt="Bootstrap Image Preview" src="<%=food.get("picture")%>"></td>
						</tr>
						<tr class="table-danger">
							<th scope="row">点餐率</th>
							<td><%=food.get("hits") %></td>
						</tr>
						<tr class="table-success">
							<th scope="row">备注</th>
							<td><%
							    if(food.get("comment").equals("0")) out.print("厨师推荐");
							    else if(food.get("comment").equals("-1")) out.print("");
							    else out.print("特价"+food.get("comment")+"元");
						        %>
						    </td>
						</tr>
					</tbody>
				</table>
				<!-- 表单按钮 “将菜品添加到点餐车” -->
				<form action="">
				<input type="hidden" name="ids" value="">
				<div class="form-group">
				<button type="submit" class="btn btn-danger btn-block">将菜品添加到点餐车</button>
				</div>
				</form>
			</div>
		</div>
	</div>
	<% } %>
</body>
</html>