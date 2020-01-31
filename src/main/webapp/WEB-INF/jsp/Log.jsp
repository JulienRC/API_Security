<jsp:include page="header.jsp" />

<div class="text-center">
	<h1 class="mt-4">Log</h1>
	<table>
		<tbody>
			<tr>
				<th>Id</th>
				<th>Connection</th>
			</tr>
			<c:forEach items="${user_information}" var="item">
				<tr>
					<td>${item.id}</td>
					<td>${item.date}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<p>This will be the page of the log's history</p>
</div>

<jsp:include page="bottom.jsp" />