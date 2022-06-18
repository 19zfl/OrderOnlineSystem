<%@page import="model.UserService,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改用户信息</title>
<link rel="stylesheet" href="../bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<%
   String id=request.getParameter("ID");
   UserService us=new UserService();
   Map<String,String> u= us.getUser(id);
 
%>
<body>
<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title text-info">用户信息修改</h5>
				</div>
				<form method="post" action="../user_upd_info.action?Id=<%=id%>">
					<div class="modal-body">
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">用户名</label>
							<div class="col-sm-10">
								<input class="form-control" id="username" value="<%=u.get("username") %>" name="un" type="text"
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
						<button type="submit" class="btn btn-primary">确定</button>
					</div>
				</form>
			</div>
		</div>

</body>
</html>