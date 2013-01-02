<%@ page import="es.elpesetero.ExpenseLine" %>

<div class="fieldcontain ${hasErrors(bean: expenseLineInstance, field: 'expenseDate', 'error')} required">
	<label for="expenseDate">
		<g:message code="expenseLine.expenseDate.label" default="Expense Date" />
		<span class="required-indicator">*</span>
	</label>	
	{{view Ember.Select contentBinding="days" valueBinding="content.day"}}
	{{view Ember.Select contentBinding="months" optionValuePath="content.id"
       optionLabelPath="content.name"  valueBinding="content.month"}}
	{{view Ember.Select contentBinding="years" valueBinding="content.year"}}
</div>

