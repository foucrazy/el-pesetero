<%@ page import="es.elpesetero.Expense" %>


<div class="fieldcontain ${hasErrors(bean: expenseInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="expense.name.label" default="Name" />
		
	</label>
	{{view Ember.TextField valueBinding="content.name"}}
</div>

<div class="fieldcontain ${hasErrors(bean: expenseInstance, field: 'category', 'error')} required">
	<label for="category">
		<g:message code="expense.category.label" default="Category" />
		<span class="required-indicator">*</span>
	</label>
	{{view Ember.Select contentBinding="categories" optionValuePath="content.id"
       optionLabelPath="content.name"  valueBinding="content.category"}}	
</div>

<div class="fieldcontain ${hasErrors(bean: expenseInstance, field: 'from', 'error')} required">
	<label for="from">
		<g:message code="expense.from.label" default="From" />
		<span class="required-indicator">*</span>
	</label>
	{{view Ember.Select contentBinding="funds" optionValuePath="content.id"
       optionLabelPath="content.name"  valueBinding="content.from"}}
</div>

<div class="fieldcontain ${hasErrors(bean: expenseInstance, field: 'quantity', 'error')} required">
	<label for="quantity">
		<g:message code="expense.quantity.label" default="Quantity" />
		<span class="required-indicator">*</span>
	</label>
	{{view Ember.TextField valueBinding="content.quantity"}}	
</div>


<div class="fieldcontain ${hasErrors(bean: expenseInstance, field: 'proRateType', 'error')} ">
	<label for="proRateType">
		<g:message code="expense.proRateType.label" default="Pro Rate Type" />
	</label>
	<g:select name="proRateType" from="${es.elpesetero.FrequencyType?.values()}" keys="${es.elpesetero.FrequencyType.values()*.name()}" value="${expenseInstance?.proRateType?.name()}" noSelection="['': '']"/>
</div>
