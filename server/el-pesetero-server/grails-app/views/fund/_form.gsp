<%@ page import="es.elpesetero.Fund" %>



<div class="fieldcontain ${hasErrors(bean: fundInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="fund.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${fundInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fundInstance, field: 'quantity', 'error')} required">
	<label for="quantity">
		<g:message code="fund.quantity.label" default="Quantity" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="quantity" step="any" required="" value="${fundInstance.quantity}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fundInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="fund.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="type" from="${es.elpesetero.FundType?.values()}" keys="${es.elpesetero.FundType.values()*.name()}" required="" value="${fundInstance?.type?.name()}"/>
</div>