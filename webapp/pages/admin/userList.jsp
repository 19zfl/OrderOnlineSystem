<%@page import="model.UserService,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<<jsp:include page="/pages/head.jsp">
	<jsp:param value="管理员管理用户信息" name="title" />
</jsp:include>
</head>
<%
List<Map<String, String>> list = (List<Map<String, String>>)request.getAttribute("users");
%>
<body>
	<jsp:include page="/pages/admin/admin_nav.jsp">
		<jsp:param value="user_m" name="param_fun" />
	</jsp:include>
	<div class="container-fluid">
		<div class="card border">
			<div class="card-header border">
				<form class="form-inline" action="" method="post">
					<input class="form-control mr-sm-2" type="text" name="un"
						placeholder="请输入用户名" value="<%=request.getParameter("un")== null ? "" : request.getParameter("un")%>">
					<input type="submit" class="btn btn-outline-success my-2 my-sm-0"
						value="搜索">
				</form>
			</div>
			<!-- 显示表格 -->
			<div class="card-body">
				<div class="table-responsive-xl">
					<table  class="table table-striped table-hover table-sm" border="1px" width="70%">
						<!-- 表头 -->
						<thead>
						<tr>
							<th>序号</th>
							<th>用户名</th>
							<th>角色</th>
							<th>电话</th>
							<th>地址</th>
							<th>操作</th>
						</tr>
						</thead>
						<tbody>
						<%
						int num = 0;
						for (Map<String, String> m : list) {
							num++;
						%>
						<tr>
							<td><%=num%></td>
							<td><%=m.get("username")%></td>
							<td><%=m.get("ident").equals("1") ? "管理员" : "普通用户"%></td>
							<td><%=m.get("telephone")%></td>
							<td><%=m.get("address")%></td>
							<td>
									<button class="btn btn-sm btn-outline-danger" 
									onclick="delConfirm('确定要删除该用户吗？','<%=request.getContextPath()%>/admin/admin_del_user.action',
									'<%=m.get("id")%>','ajax_no')">删除</button>
							</td>
						</tr>
						<%
						}
						%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/pages/del_modal.jsp"/>
</body>
</html>