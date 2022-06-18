<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/pages/head.jsp">
	<jsp:param value="添加菜品信息" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/pages/admin/admin_nav.jsp">
		<jsp:param value="food_m" name="param_fun" />
	</jsp:include>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 col-0"></div>
			<div class="col-md-6 col-12"">
				<div class="card border-warning">
					<div class="card-header border-warning bg-warning">
					 <h5 class="text-center">添加菜品</h5>
					</div>
				<form role="form" method="post" action="admin_add_food_do.action"
						enctype="multipart/form-data">
					<div class="card-body">
					  <div class="form-group">
					  <label for="foodname">菜品名称</label>
					  <input type="text" class="form-control" name="fn" id="foodname" required="required" value="">
					  </div>
					 <div class="form-group">
					  <label for="feature">菜品特色</label>
					  <textarea class="form-control" row="3" name="fea" id="feature" required="required"></textarea>
					  </div>
					 <div class="form-group">
					  <label for="material">主要原料</label>
					  <textarea class="form-control" row="3" name="mat" id="material" required="required"></textarea>
					  </div>
					   <div class="form-group">
					  <label for="type">所属分类</label>
					   <select class="form-control mr-sm-2" name="type">
						<option value="">所有分类</option>
						<%
							List<Map<String, String>> types = (List<Map<String, String>>) request.getAttribute("types");
							for (Map<String, String> type : types) {
						%>
						<option value="<%=type.get("id")%>">
							<%=type.get("typename")%></option>
						<%
							}
						%>
					</select>
					  </div>
					  <div class="form-group">
					  <label for="price">菜品价格</label>
					  <input type="text" class="form-control" name="price" id="price" required="required" value=""/>
					  <p class="">单位：元</p>
					  </div>
					  <div class="form-group">
					  <label for="img">菜品图片</label>
					  <input type="file" class="form-control" name="img" id="img">
					  <p class="">请选择上传的菜品图片，大小应小于5M，扩展名为jpg,png或gif。</p>
					  
					  </div>
					   <div class="form-group">
					  <label for="comment">菜品备注</label>
					  <input type="text" class="form-control" name="com" id="comment" required="required" value="">
					  <p class="">-1代表正常菜品，0代表厨师推荐，正整数代表特价菜价格。</p>
					  </div>
					</div>
					 
					 <div class="card-footer border-warning text-center">
				<button type="submit" class="btn btn-warning">确认添加</button>
				<a role="button" class="btn btn-default" href="javascript:history.back();">放弃返回</a>
				</div>
					</form>
				</div>
				<div class="col-md-2 col-0"></div>
			</div>
		</div>
	</div>


</body>
</html>