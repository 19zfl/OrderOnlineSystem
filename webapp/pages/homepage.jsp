<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="head.jsp">
<jsp:param value="首页" name="title"/>
</jsp:include>
</head>
<body>
	<jsp:include page="nav.jsp"/>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4 col-12">
				<h4 class="list-group-item active">热点菜品</h4>
				<%
				   List<Map<String,String>> hot=(List<Map<String,String>>)request.getAttribute("hot");
				  if(hot!=null){
				%>
				<ul class="list-group">
				    <%  for(Map<String,String> m:hot) { %>
					<li class="list-group-item"><img class="img-rounded"
						src="<%=request.getContextPath() %>/<%=m.get("picture") %>" /> <a
						href="<%=request.getContextPath() %>/show_detail.action?id=<%=m.get("id") %>" target="_blank"> <%=m.get("foodname") %></a>
						&nbsp;&nbsp; <span><%=m.get("price") %>元</span></li>
						<%} %>
				</ul>
				<%} %>
			</div>
			<div class="col-md-4 col-12">
				<h4 class="list-group-item active">今日特价</h4>
				<%
				   List<Map<String,String>> special=(List<Map<String,String>>)request.getAttribute("special");
				  if(hot!=null){
				%>
				<ul class="list-group">
				    <%  for(Map<String,String> m:special) { %>
					<li class="list-group-item"><img class="img-rounded"
						src="<%=request.getContextPath() %>/<%=m.get("picture") %>" /> <a
						href="<%=request.getContextPath() %>/show_detail.action?id=<%=m.get("id") %>" target="_blank"> <%=m.get("foodname") %></a>
						&nbsp;&nbsp; <span><%=m.get("comment") %>元</span></li>
						<%} %>
				</ul>
				<%} %>
			</div>
			<div class="col-md-4 col-12">
				<h4 class="list-group-item active">厨师推荐</h4>
				<%
				   List<Map<String,String>> recomm=(List<Map<String,String>>)request.getAttribute("recomm");
				  if(hot!=null){
				%>
				<ul class="list-group">
				    <%  for(Map<String,String> m:recomm) { %>
					<li class="list-group-item"><img class="img-rounded"
						src="<%=request.getContextPath() %>/<%=m.get("picture") %>" /> <a
						href="<%=request.getContextPath() %>/show_detail.action?id=<%=m.get("id") %>" target="_blank"> <%=m.get("foodname") %></a>
						&nbsp;&nbsp; <span><%=m.get("price") %>元</span></li>
						<%} %>
				</ul>
				<%} %>
			</div>
		</div>
	</div>

<!-- Modal -->
	<div class="modal" id="loginModal" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title text-info">用户登录</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form method="post" action="<%=request.getContextPath() %>/login.action">
					<div class="modal-body">
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">用户名</label>
							<div class="col-sm-10">
								<input class="form-control" name="un" type="text" required />
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">密码</label>
							<div class="col-sm-10">
								<input class="form-control" name="pw" type="password" required />
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
	</div>
	<!-- Modal -->
	<div class="modal fade" id="registerModal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title text-info">用户注册</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form method="post" action="<%=request.getContextPath() %>/register.action">
					<div class="modal-body">
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">用户名</label>
							<div class="col-sm-10">
								<input class="form-control" id="username" name="un" type="text"
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
								<input class="form-control" name="tel" type="number" required />
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">地址</label>
							<div class="col-sm-10">
								<input class="form-control" name="addr" type="text" required />
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
	</div>
</body>
</html>