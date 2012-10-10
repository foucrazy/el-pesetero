
<%@ page import="es.elpesetero.PeriodicExpense" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'periodicExpense.label', default: 'PeriodicExpense')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-periodicExpense" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-periodicExpense" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list periodicExpense">
			
				<g:if test="${periodicExpenseInstance?.day}">
				<li class="fieldcontain">
					<span id="day-label" class="property-label"><g:message code="periodicExpense.day.label" default="Day" /></span>
					
						<span class="property-value" aria-labelledby="day-label"><g:fieldValue bean="${periodicExpenseInstance}" field="day"/></span>
					
				</li>
				</g:if>
								
				<g:if test="${periodicExpenseInstance?.frequency}">
				<li class="fieldcontain">
					<span id="frequency-label" class="property-label"><g:message code="periodicExpense.frequency.label" default="Frequency" /></span>
					
						<span class="property-value" aria-labelledby="frequency-label"><g:fieldValue bean="${periodicExpenseInstance}" field="frequency"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<ol class="property-list expense">
				<g:set var="expenseInstance" value="${periodicExpenseInstance?.expense}"/>
				<g:if test="${expenseInstance?.proRateType}">
				<li class="fieldcontain">
					<span id="proRateType-label" class="property-label"><g:message code="expense.proRateType.label" default="Pro Rate Type" /></span>
					
						<span class="property-value" aria-labelledby="proRateType-label"><g:fieldValue bean="${expenseInstance}" field="proRateType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${expenseInstance?.category}">
				<li class="fieldcontain">
					<span id="category-label" class="property-label"><g:message code="expense.category.label" default="Category" /></span>
					
						<span class="property-value" aria-labelledby="category-label"><g:link controller="expenseCategory" action="show" id="${expenseInstance?.category?.id}">${expenseInstance?.category?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${expenseInstance?.from}">
				<li class="fieldcontain">
					<span id="from-label" class="property-label"><g:message code="expense.from.label" default="From" /></span>
					
						<span class="property-value" aria-labelledby="from-label"><g:link controller="fund" action="show" id="${expenseInstance?.from?.id}">${expenseInstance?.from?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${expenseInstance?.quantity}">
				<li class="fieldcontain">
					<span id="quantity-label" class="property-label"><g:message code="expense.quantity.label" default="Quantity" /></span>
					
						<span class="property-value" aria-labelledby="quantity-label"><g:fieldValue bean="${expenseInstance}" field="quantity"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${periodicExpenseInstance?.id}" />
					<g:link class="edit" action="edit" id="${periodicExpenseInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
