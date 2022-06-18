<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/pages/head.jsp">
	<jsp:param value="用户点餐列表" name="title" />
</jsp:include>
</head>

<body>
	<jsp:include page="/pages/admin/admin_nav.jsp">
		<jsp:param value="dc_s" name="param_fun" />
	</jsp:include>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 col-0"></div>
			<div class="col-md-6 col-12">
				<!-- 循环1所有用户点餐列表 -->
				<%
				Map<String, List<Map<String, String>>> dcs = (Map<String, List<Map<String, String>>>) request.getAttribute("dcs");
				 
				for(Map.Entry<String, List<Map<String, String>>> entry:dcs.entrySet()){
					String username=entry.getKey();
					 List<Map<String, String>> dc=entry.getValue();
				%>
				<div class="card border-primary">
					<div class="card-body">
						<ul class="list-group">
							<li class="list-group-item active"><%=username %></li>
							<!--循环2 显示该用户点餐列表  -->
							<% 
							  int sum=0;   
							for(Map<String, String> food:dc){
								sum+=Integer.parseInt(food.get("price"));
							%>
							<li class="list-group-item"><%=food.get("foodname") %> <span
								class="babge babge-pill babge-success p-2 float-right">$<%=food.get("price") %></span>
							</li>
							<!--循环2 显示该用户点餐列表end  -->
							<%} %>
							<!-- 合计价格 -->
							<li class="list-group-item active">合计 <span
								class="babge babge-pill babge-success p-2 float-right">$<%=sum %></span>
							</li>
						</ul>
					</div>
				</div>
				<!-- 循环1用户点餐列表end -->
				<%} %>
			</div>
		</div>
	</div>

</body>
</html>