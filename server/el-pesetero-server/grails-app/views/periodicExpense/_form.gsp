<%@ page import="es.elpesetero.PeriodicExpense" %>



<div class="fieldcontain ${hasErrors(bean: periodicExpenseInstance, field: 'day', 'error')} required">
	<label for="day">
		<g:message code="periodicExpense.day.label" default="Day" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="day" required="" value="${periodicExpenseInstance.day}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicExpenseInstance, field: 'frequency', 'error')} required">
	<label for="frequency">
		<g:message code="periodicExpense.frequency.label" default="Frequency" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="frequency" from="${es.elpesetero.FrequencyType?.values()}" keys="${es.elpesetero.FrequencyType.values()*.name()}" required="" value="${periodicExpenseInstance?.frequency?.name()}"/>
</div>

