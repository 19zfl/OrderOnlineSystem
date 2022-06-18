<%@page import="model.UserService,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<<jsp:include page="/pages/head.jsp">
	<jsp:param value="管理员管理菜品类型" name="title" />
</jsp:include>
</head>
<%
List<Map<String, String>> list = (List<Map<String, String>>)request.getAttribute("types");
%>
<body>
	<jsp:include page="/pages/admin/admin_nav.jsp">
		<jsp:param value="food_t_m" name="param_fun" />
	</jsp:include>
	<div class="container-fluid">
		<div class="card border">
			
			<!-- 显示表格 -->
			<div class="card-body">
				<div class="table-responsive-xl">
					<table  class="table table-striped table-hover table-sm" border="1px" width="40%">
						<!-- 表头 -->
						<thead>
						<tr>
							<th>序号</th>
							<th>菜品类型</th>
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
							<td><%=m.get("typename")%></td>
							<td><a class="btn btn-sm btn-outline-warning"
									href="<%=request.getContextPath()%>/admin/admin_edit_food.action?id=<%=m.get("id") %>"
									role="button">修改</a>
									<button class="btn btn-sm btn-outline-danger" 
									onclick="delConfirm('确定要删除该类型吗？','<%=request.getContextPath()%>/admin/admin_del_user.action',
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