<!DOCTYPE html PUBLIC "-//W3C//DTD "XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<title>Login Page</title>

<style type="text/css">
body {
	margin: 0;
	padding: 0;
	text-align: center;
	vertical-align: middle;
/* 	background: url(../img/login/bar2bg.png) no-repeat;
	background-size: 100%;
 */}

form span {
	font-family: Verdana, Geneva, sans-serif;
	font-size: 12px;
}

form input {
	font-family: Verdana, Geneva, sans-serif;
	font-size: 12px;
}

.centered {
	margin: auto;
	text-align: left;
	width: 300px;
	position: relative;
	top: 35%;
}

.login {
	border: 1px solid;
	border-radius: 4px;
	border-top: solid 1px #4FC6D3;
	border-bottom: solid 1px #0A7582;
	border-left: 0;
	border-right: 0;
	color: white;
	background: url(../img/login/bar4bg.png);
}

table {
	width: 100%;
	text-align: center;
	color: white;
	border: 0;
}

.title {
	border-radius: 4px;
	background: url(../img/login/bar2bg.png);
	color: black;
	text-align: center;
}

.title span {
	font-family: Verdana, Geneva, sans-serif;
	font-size: 14px;
	font-weight: bold;
}

.button {
	background: url(../img/login/buttonbg.png);
	border: 1px solid;
	border-color: black;
	border-radius: 2px;
	height: 20px;
	padding: 2 5 2 5;
}

.error {
	color: #DF0101;
	font-weight: bold;
	text-align: center;
}
</style>

<script type="text/javascript">
	function submitForm() {
		document.forms[0].action = '<c:url value="/j_spring_security_check" />';
		document.forms[0].submit();
		return false;
	}
</script>

</head>

<body onload='document.f.j_username.focus();'>
	<div class="centered">
		<form name='f' method='POST' onsubmit="return false;">
			<div class="login">
				<table>
					<tr>
						<td colspan="2" class="title"><span>Acceso al sistema</span></td>
					</tr>
					<tr>
						<td><span>Usuario:</span></td>
						<td><input type='text' name='j_username' value=''></td>
					</tr>
					<tr>
						<td><span>Password:</span></td>
						<td><input type='password' name='j_password' /></td>
					</tr>
					<tr>
						<td colspan='2' style='text-align: right;'>
							<div onclick="javascript:submitForm();" style="margin-top: 5px;">
								<span class="button"><spring:message code="button.accept" /></span>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan='2'></td>
					</tr>
				</table>
			</div>
			<div class="error">
				<c:if test="${ !empty SPRING_SECURITY_LAST_EXCEPTION }">
					<c:choose>
						<c:when
							test="${SPRING_SECURITY_LAST_EXCEPTION.class.name == 'com.topgroup.commons.security.exception.UsuarioNoVigenteAcegyException'}">
							<spring:message code="security.login.usuarioNoVigente" />
						</c:when>
						<c:when
							test="${SPRING_SECURITY_LAST_EXCEPTION.class.name == 'org.springframework.security.authentication.BadCredentialsException'}">
							<spring:message code="security.login.badCredentials" />
						</c:when>
						<c:when
							test="${SPRING_SECURITY_LAST_EXCEPTION.class.name == 'org.springframework.security.core.userdetails.UsernameNotFoundException'}">
							<spring:message code="security.login.userNotFound" />
						</c:when>
						<c:when
							test="${SPRING_SECURITY_LAST_EXCEPTION.class.name == 'org.springframework.security.authentication.LockedException'}">
							<spring:message code="security.login.userLocked" />
							<br />
							<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
						</c:when>
						<c:when
							test="${SPRING_SECURITY_LAST_EXCEPTION.class.name == 'com.topgroup.commons.security.exception.ParticipationDeletedException'}">
							<spring:message code="security.login.userNotFound" />
						</c:when>
						<c:when
							test="${SPRING_SECURITY_LAST_EXCEPTION.class.name == 'com.topgroup.commons.security.exception.ExpiredAccountBadException'}">
							<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
						</c:when>
						<c:otherwise>
							<spring:message code="security.login.errorUnknown" />
						</c:otherwise>
					</c:choose>
					<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session" />
				</c:if>
			</div>
		</form>
	</div>
</body>
</html>