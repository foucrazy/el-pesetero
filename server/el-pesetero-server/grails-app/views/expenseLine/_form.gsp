<%@ page import="es.elpesetero.ExpenseLine" %>

<div class="fieldcontain ${hasErrors(bean: expenseLineInstance, field: 'expenseDate', 'error')} required">
	<label for="expenseDate">
		<g:message code="expenseLine.expenseDate.label" default="Expense Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="expenseDate" precision="day"  value="${expenseLineInstance?.expenseDate}"  />
</div>

