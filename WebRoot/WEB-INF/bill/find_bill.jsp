<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>达内－NetCTOSS</title>
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global_color.css" /> 
         <script language="javascript" type="text/javascript" src="../js/jquery-1.4.3.js"></script>
        <script language="javascript" type="text/javascript">
            //写入下拉框中的年份和月份
            function initialYearAndMonth() {
                //写入最近3年
                var yearObj = document.getElementById("selYears");
                var year = (new Date()).getFullYear();
                var opObj = new Option("全部", "全部");
                yearObj.options[0] = opObj;
                for (var i = 1; i <= 5; i++) {
                    var opObj = new Option(year - i, year - i);
                    yearObj.options[i] = opObj;
                }
                //写入 12 月
                var monthObj = document.getElementById("selMonths");
                opObj = new Option("全部", "全部");
                monthObj.options[0] = opObj;
                for (var i = 1; i <= 12; i++) {
                    var opObj = new Option(i, i);
                    monthObj.options[i] = opObj;
                }
            }
            //分页查询
            function toPage(page){
            	document.getElementById("currPage").value=page;
            	document.forms[0].submit();
            }            
        </script>
    </head>
    <body>
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
            <form action="findBill" method="post">
            	<s:hidden name="condition.page" id="currPage"/>
                <!--查询-->
                <div class="search_add">                        
                    <div>
                    	身份证：
                    	<s:textfield name="condition.idcardNo" cssClass="text_search"/>
                    </div>
                    <div>
                    	账务账号：
                    	<s:textfield name="condition.loginName" cssClass="width100 text_search"/>
                    </div>                            
                    <div>
                    	姓名：
                    	<s:textfield name="condition.realName" cssClass="width70 text_search"/>
                    </div>
                    <div>
                        <s:select name="condition.year" list="years" headerKey="" headerValue="全部" cssClass="select_search"/>
                        	年
                        <s:select name="condition.month" list="#{'01':'1月','02':'2月','03':'3月','04':'4月','05':'5月','06':'6月','07':'7月','08':'8月','09':'9月','10':'10月','11':'11月','12':'12月' }" headerKey="" headerValue="全部" cssClass="select_search"/>
                        	月
                    </div>
                    <div><input type="button" value="搜索" class="btn_search" onclick="toPage(1);"/></div>
                </div>  
                <!--数据区域：用表格展示数据-->     
                <div id="data">            
                    <table id="datalist">
                    <tr>
                        <th class="width50">账单ID</th>
                        <th class="width70">姓名</th>
                        <th class="width150">身份证</th>
                        <th class="width150">账务账号</th>
                        <th>费用</th>
                        <th class="width100">月份</th>
                        <th class="width100">支付方式</th>
                        <th class="width100">支付状态</th>                                                        
                        <th class="width50"></th>
                    </tr>
                    <s:iterator value="bills">
	                    <tr>
	                        <td><s:property value="id"/></td>
	                        <td><s:property value="account.realName"/></td>
	                        <td><s:property value="account.idcardNo"/></td>
	                        <td><s:property value="account.loginName"/></td>
	                        <td><s:property value="cost"/></td>
	                        <td>
	                        	<s:property value="billMonth.substring(0,4)+'年'+billMonth.substring(4,6)+'月'"/>
	                        </td>
	                        <td>
	                        	<s:if test="paymentMode==0">现金</s:if>
	                        	<s:elseif test="paymentMode==1">银行转帐</s:elseif>
	                        	<s:elseif test="paymentMode==2">邮局汇款</s:elseif>
	                        	<s:else>其他</s:else>
	                        </td>
	                        <td>
	                        	<s:if test="payState==0">未支付</s:if>
	                        	<s:else>已支付</s:else>
	                        </td>                            
	                        <td><a href="findBillItem?billId=<s:property value="id"/>" title="账单明细">明细</a></td>
	                    </tr>
                   </s:iterator>
                </table>
                
                <p>业务说明：<br />
                1、设计支付方式和支付状态，为用户自服务中的支付功能预留；<br />
                2、只查询近 3 年的账单，即当前年和前两年，如2013/2012/2011；<br />
                3、年和月的数据由 js 代码自动生成；<br />
                4、由数据库中的ｊｏｂ每月的月底定时计算账单数据。</p>
                </div>                    
                <!--分页-->
                <div id="pages">
                    <a href="javascript:toPage(1);">首页</a>
                    <s:if test="condition.page==1">
	        	        <a href="#">上一页</a>
                    </s:if>
                    <s:else>
                    	<a href="javascript:toPage(<s:property value="condition.page-1"/>);">上一页</a>
                    </s:else>
                    
                    <s:iterator begin="1" end="totalPage" var="p">
                    	<s:if test="#p==condition.page">
                    		<a href="javascript:toPage(<s:property value="#p"/>);" class="current_page"><s:property value="#p"/></a>
                    	</s:if>
                    	<s:else>
                    		<a href="javascript:toPage(<s:property value="#p"/>);"><s:property value="#p"/></a>
                    	</s:else>
                    </s:iterator>
                    
                    <s:if test="condition.page==totalPage">
	        	        <a href="#">下一页</a>
                    </s:if>
                    <s:else>
                    	<a href="javascript:toPage(<s:property value="condition.page+1"/>);">下一页</a>
                    </s:else>
                    <a href="javascript:toPage(<s:property value="totalPage"/>);">末页</a>
                </div>                    
            </form>
        </div>
        <!--主要区域结束-->
        <div id="footer">
            <p>[源自北美的技术，最优秀的师资，最真实的企业环境，最适用的实战项目]</p>
            <p>版权所有(C)加拿大达内IT培训集团公司 </p>
        </div>
    </body>
</html>