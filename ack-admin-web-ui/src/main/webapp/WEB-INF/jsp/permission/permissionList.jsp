<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../public/header.jsp"%>
<script
	src="${pageContext.request.contextPath}/static/3part/ace/js/ace-extra.min.js"></script>
<title>权限列表</title>
</head>
<body>
	<!-- table2 -->
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">权限管理</h3>
			<table id="simple-table" class="table  table-bordered table-hover">
				<thead>
					<tr role="row">
						<th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">序号</th>
						<th class="sorting" tabindex="0" aria-controls="dynamic-table"
							rowspan="1" colspan="1">权限名称</th>
						<th class="hidden-480 sorting" tabindex="0"
							aria-controls="dynamic-table" rowspan="1" colspan="1">权限标识</th>
						<th class="sorting" rowspan="1" colspan="1"
							aria-label="dynamic-table">备注</th>
						<th class="sorting" tabindex="0" aria-controls="dynamic-table"
							rowspan="1" colspan="1"><i
							class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 创建时间</th>
						<th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">操作</th>
					</tr>
				</thead>

				<tbody id='tab-body'>

				</tbody>
			</table>
			<div class='col-xs-12'>
				<div class="dataTables_paginate paging_simple_numbers" id="page">

				</div>
			</div>
		</div>

		<!-- /.span -->
		<div class='col-xs-12'>
			<button class="btn btn-primary" id="ack-add-btn" data-toggle="modal"
				onclick="Permission.eidtUI();">新建权限</button>
		</div>
	</div>

	<%@include file="../public/footer.jsp"%>
	<%@include file="../public/table.jsp"%>
	<script type="text/javascript"
		src="/static/js/lib/simple-paginator-1.0.js"></script>
	<script type="text/javascript" src="/static/js/permission/permission.js"></script>
	<script type="text/javascript">
        $(document).ready(function(){
        	Permission.init();
        });
    </script>
</body>
</html>