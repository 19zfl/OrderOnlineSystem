<%@page import="model.UserService,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/pages/head.jsp">
	<jsp:param value="修改用户信息" name="title" />
</jsp:include>
</head>
<!-- 获取登录用户对象 -->
<%
    String id=(String)session.getAttribute("loginID");
    UserService us=new UserService();
    Map<String,String> u=us.getUser(id);
%>
<body>
	<jsp:include page="/pages/user/user_nav.jsp">
		<jsp:param value="index" name="fun" />
	</jsp:include>
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title text-info">用户注册</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form method="post" action="../updateUser.action?id=<%=id%>">
				<div class="modal-body">
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">用户名</label>
						<div class="col-sm-10">
							<input class="form-control" id="username" name="un" value="<%=u.get("username") %>" type="text"
							 	required /> <span class="text-danger" id="checkInfo"></span>
						</div>

					</div>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">密码</label>
						<div class="col-sm-10">
							<input class="form-control" name="pw" type="password" required />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">电话</label>
						<div class="col-sm-10">
							<input class="form-control" name="tel" value="<%=u.get("telephone") %>" type="number" required />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">地址</label>
						<div class="col-sm-10">
							<input class="form-control" name="addr" value="<%=u.get("address") %>" type="text" required />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">修改</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>