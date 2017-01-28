<%@taglib uri="/struts-tags" prefix="s" %>
<ul id="menu">
	<li>
		<s:if test="#session.currPrivilege==0">
			<a href="/NETCTOSS-SSH/login/toIndex" class="index_on"></a>
		</s:if>
		<s:else>
			<a href="/NETCTOSS-SSH/login/toIndex" class="index_off"></a>
		</s:else>
	</li>
	<s:iterator value="#session.privilegeIds" var="p">
		<s:if test="#p==1">
			<li>
				<s:if test='#session.currPrivilege==1'>
					<a href="/NETCTOSS-SSH/role/findRole" class="role_on"></a>
				</s:if>
				<s:else>
					<a href="/NETCTOSS-SSH/role/findRole" class="role_off"></a>
				</s:else>	
			</li>
		</s:if>
		<s:if test="#p==2">
			<li>
				<s:if test='#session.currPrivilege==2'>
					<a href="/NETCTOSS-SSH/admin/findAdmin" class="admin_on"></a>
				</s:if>
				<s:else>
					<a href="/NETCTOSS-SSH/admin/findAdmin" class="admin_off"></a>
				</s:else>
			</li>
		</s:if>
		<s:if test="#p==3">
			<li>
				<s:if test='#session.currPrivilege==3'>
					<a href="/NETCTOSS-SSH/cost/findCost" class="fee_on"></a>
				</s:if>
				<s:else>
					<a href="/NETCTOSS-SSH/cost/findCost" class="fee_off"></a>
				</s:else>				
			</li>
		</s:if>
		<s:if test="#p==4">
			<li>
				<s:if test='#session.currPrivilege==4'>
					<a href="/NETCTOSS-SSH/account/findAccount" class="account_on"></a>
				</s:if>
				<s:else>
					<a href="/NETCTOSS-SSH/account/findAccount" class="account_off"></a>
				</s:else>
			</li>
		</s:if>
		<s:if test="#p==5">
			<li>
				<s:if test='#session.currPrivilege==5'>
					<a href="/NETCTOSS-SSH/service/findService" class="service_on"></a>
				</s:if>
				<s:else>
					<a href="/NETCTOSS-SSH/service/findService" class="service_off"></a>
				</s:else>
			</li>
		</s:if>
		<s:if test="#p==6">
			<li>
				<s:if test='#session.currPrivilege==6'>
					<a href="/NETCTOSS-SSH/bill/findBill" class="bill_on"></a>
				</s:if>
				<s:else>
					<a href="/NETCTOSS-SSH/bill/findBill" class="bill_off"></a>
				</s:else>
			</li>
		</s:if>
		<s:if test="#p==7">
			<li>
				<s:if test='#session.currPrivilege==7'>
					<a href="/NETCTOSS-SSH/report/report" class="report_on"></a>
				</s:if>
				<s:else>
					<a href="/NETCTOSS-SSH/report/report" class="report_off"></a>
				</s:else>
			</li>
		</s:if>
	</s:iterator>
	<li>
		<a href="#" class="information_off"></a>
	</li>
	<li>
		<a href="#" class="password_off"></a>
	</li>
</ul>
