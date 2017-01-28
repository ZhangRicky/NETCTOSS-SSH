<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>达内－NetCTOSS</title>
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global_color.css" /> 
    </head>
    <body onload="initialYearAndMonth();">
        <!--Logo区域开始-->
        <div id="header">
            <img src="../images/logo.png" alt="logo" class="left"/>
            <a href="#">[退出]</a>            
        </div>
        <!--Logo区域结束-->
        <!--导航区域开始-->
        <div id="navi">     
        	<s:include value="/WEB-INF/main/menu.jsp"/>                   
            <!-- <ul id="menu">
                <li><a href="../index.html" class="index_off"></a></li>
                <li><a href="../role/role_list.html" class="role_off"></a></li>
                <li><a href="../admin/admin_list.html" class="admin_off"></a></li>
                <li><a href="../fee/fee_list.html" class="fee_off"></a></li>
                <li><a href="../account/account_list.html" class="account_off"></a></li>
                <li><a href="../service/service_list.html" class="service_off"></a></li>
                <li><a href="../bill/bill_list.html" class="bill_on"></a></li>
                <li><a href="../report/report_list.html" class="report_off"></a></li>
                <li><a href="../user/user_info.html" class="information_off"></a></li>
                <li><a href="../user/user_modi_pwd.html" class="password_off"></a></li>
            </ul> -->            
        </div>
        <!--导航区域结束-->
        <!--主要区域开始-->
        <div id="main">
            <form action="" method="">
                <!--查询-->
                <div class="search_add">                        
                    <div>
                    	账务账号：
                    	<span class="readonly width70"><s:property value="billItem.service.account.loginName"/></span>
                    </div>                            
                    <div>
                    	OS 账号：
                    	<span class="readonly width100"><s:property value="billItem.service.osUserName"/></span>
                    </div>
                    <div>
                    	服务器 IP：
                    	<span class="readonly width100"><s:property value="billItem.service.unixHost"/></span>
                    </div>
                    <div>
                    	计费时间：
                    	<span class="readonly width70">
                    		<s:property value="billItem.bill.billMonth.substring(0,4)+'年'+billItem.bill.billMonth.substring(4,6)+'月'"/>
                    	</span>
                    </div>
                    <div>
                    	费用：
                    	<span class="readonly width70"><s:property value="billItem.cost"/></span>
                    </div>
                    <input type="button" value="返回" class="btn_add" onclick="history.back();" />
                </div>  
                <!--数据区域：用表格展示数据-->     
                <div id="data">            
                    <table id="datalist">
                        <tr>
                            <th class="width150">客户登录 IP</th>
                            <th class="width150">登入时刻</th>
                            <th class="width150">登出时刻</th>
                            <th class="width100">时长（秒）</th>
                            <th class="width150">费用</th>
                            <th>资费</th>
                        </tr>
                        <s:iterator value="serviceDetails">
	                        <tr>
	                            <td><s:property value="clientHost"/></td>
	                            <td><s:date name="loginTime" format="yyyy-MM-dd hh24:mm:ss"/></td>
	                            <td><s:date name="logoutTime" format="yyyy-MM-dd hh24:mm:ss"/></td>
	                            <td><s:property value="duration"/></td>
	                            <td><s:property value="cost"/></td>
	                            <td><s:property value="billItem.service.cost.name"/></td>
	                        </tr>
                        </s:iterator>
                    </table>
                </div>
                <!--分页-->
                <!-- <div id="pages">
        	        <a href="#">上一页</a>
                    <a href="#" class="current_page">1</a>
                    <a href="#">2</a>
                    <a href="#">3</a>
                    <a href="#">4</a>
                    <a href="#">5</a>
                    <a href="#">下一页</a>
                </div>    -->                 
            </form>
        </div>
        <!--主要区域结束-->
        <div id="footer">
            <p>[源自北美的技术，最优秀的师资，最真实的企业环境，最适用的实战项目]</p>
            <p>版权所有(C)加拿大达内IT培训集团公司 </p>
        </div>
    </body>
</html>