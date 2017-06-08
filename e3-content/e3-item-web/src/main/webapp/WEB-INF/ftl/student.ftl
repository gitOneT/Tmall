<html>
<head>
	<title>student</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>序号</th>
			<th>学号</th>
			<th>姓名</th>
			<th>年龄</th>
			<th>日期</th>
			<th>时间</th>
			<th>自定义格式时间</th>
		</tr>
			<tr>
		<#list studentList as stu>
		<#if stu_index % 2 == 0>
		<tr bgcolor="blue">
		<#else>
		<tr bgcolor="red">
		</#if>
			<td>${stu_index}</td>
			<td>${stu.id}</td>
			<td>${stu.name}</td>
			<td>${stu.age}</td>
			<td>${date?date}</td>
			<td>${date?datetime}</td>
			<td>${date?string("yyyy-MM-dd HH:mm:ss")}</td>
		</tr>
		</#list>
	</table>
	<br>
	list 循环遍历
	<br>
	stu_index 为循环的下标
	<br>
	if else  进行判断
	<br>

</body>
</html>