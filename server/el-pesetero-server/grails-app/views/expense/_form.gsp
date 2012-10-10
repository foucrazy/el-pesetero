<%@ page import="es.elpesetero.Expense" %>



<div class="fieldcontain ${hasErrors(bean: expenseInstance, field: 'proRateType', 'error')} ">
	<label for="proRateType">
		<g:message code="expense.proRateType.label" default="Pro Rate Type" />
		
	</label>
	<g:select name="proRateType" from="${es.elpesetero.FrequencyType?.values()}" keys="${es.elpesetero.FrequencyType.values()*.name()}" value="${expenseInstance?.proRateType?.name()}" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expenseInstance, field: 'category', 'error')} required">
	<label for="category">
		<g:message code="expense.category.label" default="Category" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="category" name="category.id" from="${es.elpesetero.ExpenseCategory.list()}" optionKey="id" required="" value="${expenseInstance?.category?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expenseInstance, field: 'from', 'error')} required">
	<label for="from">
		<g:message code="expense.from.label" default="From" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="from" name="from.id" from="${es.elpesetero.Fund.list()}" optionKey="id" required="" value="${expenseInstance?.from?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expenseInstance, field: 'quantity', 'error')} required">
	<label for="quantity">
		<g:message code="expense.quantity.label" default="Quantity" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="quantity" step="any" required="" value="${expenseInstance.quantity}"/>
</div>

