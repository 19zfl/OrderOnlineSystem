<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12 col-12">
			<nav
				class="navbar navbar-expand-lg navbar-light bg-light justify-content-between">
				<a class="navbar-brand" href="homepage.action"><span
					class="text-info">网络点餐系统</span></a>
				<%
					String ident = (String) session.getAttribute("ident");
				if (ident == null) {
				%>
				<ul class="nav justify-content-end">
					<li class="nav-item"><a class="nav-link" href="#"
						data-toggle="modal" data-target="#loginModal">登录</a></li>
					<li class="nav-item"><a class="nav-link" href="#"
						data-toggle="modal" data-target="#registerModal">注册</a></li>
				</ul>
				<%
					} else if (ident.equals("0")) {
				%>
				<ul class="navbar-nav mr-auto">
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/user/user_index.action">正在点餐</a>
					</li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/user/user_show_dc.action">我的点餐</a></li>
				</ul>

				<ul class="nav justify-content-end">
					<li class="nav-item dropdown"><a class="nav-link" href="#"
						data-toggle="dropdown"><%=session.getAttribute("loginName")%></a>
						<div class="dropdown-menu dropdown-menu-right">
							<a class="dropdown-item"
								href="<%=request.getContextPath()%>/updateUser.jsp">修改个人资料信息</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item"
								href="<%=request.getContextPath()%>/logout.action">退出登录</a>
						</div></li>

				</ul>
				<%
					} else {
				%>
				
					<ul class="navbar-nav mr-auto">
						<li
							class="nav-item<%="user_m".equals(request.getParameter("param_fun")) ? "active" : ""%>">
							<a class="nav-link"
							href="<%=request.getContextPath()%>/admin/admin_list_user.action">用户管理</a>
						</li>
						<li
							class="nav-item<%="food_t_m".equals(request.getParameter("param_fun")) ? "active" : ""%>">
							<a class="nav-link"
							href="<%=request.getContextPath()%>/admin/admin_list_foodtype.action">菜品分类管理</a>
						</li>

						<li
							class="nav-item<%="food_m".equals(request.getParameter("param_fun")) ? "active" : ""%>">
							<a class="nav-link"
							href="<%=request.getContextPath()%>/admin/admin_list_food.action">菜品管理</a>
						</li>

						<li
							class="nav-item<%="dc_s".equals(request.getParameter("param_fun")) ? "active" : ""%>">
							<a class="nav-link"
							href="<%=request.getContextPath()%>/admin/admin_show_dc.action">查看用户点餐情况</a>
						</li>
					</ul>

					<ul class="nav justify-content-end">
						<li class="nav-item dropdown"><a class="nav-link" href="#"
							data-toggle="dropdown"><%=session.getAttribute("loginName")%></a>
							<ul class="dropdown-menu dropdown-menu-right">
								<li><a class="nav-link"
									href="<%=request.getContextPath()%>/logout.action">退出登录</a></li>
							</ul></li>

					</ul>

				<%
					}
				%>
			</nav>
		</div>
	</div>
</div>
