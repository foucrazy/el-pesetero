<%@ page import="es.elpesetero.ExpenseLine" %>


<%--<div class="fieldcontain ${hasErrors(bean: expenseLineInstance, field: 'expense', 'error')} required">--%>
<%--	<label for="expense">--%>
<%--		<g:message code="expenseLine.expense.label" default="Expense" />--%>
<%--		<span class="required-indicator">*</span>--%>
<%--	</label>--%>
<%--	<g:select id="expense" name="expense.id" from="${es.elpesetero.Expense.list()}" optionKey="id" required="" value="${expenseLineInstance?.expense?.id}" class="many-to-one"/>--%>
<%--</div>--%>

<div class="fieldcontain ${hasErrors(bean: expenseLineInstance, field: 'expenseDate', 'error')} required">
	<label for="expenseDate">
		<g:message code="expenseLine.expenseDate.label" default="Expense Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="expenseDate" precision="day"  value="${expenseLineInstance?.expenseDate}"  />
</div>

