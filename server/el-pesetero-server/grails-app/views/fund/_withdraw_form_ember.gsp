<div class="fieldcontain ${hasErrors(bean: expenseInstance, field: 'name', 'error')} ">
	<label for="name"><g:message code="withdraw.quantity.label" default="Sacar" />
	</label>
	{{view Ember.TextField valueBinding="content.quantity"}}€
	{{#if severalAccounts}} 
		<g:message code="expense.from.label" default="From" />
		{{view Ember.Select contentBinding="funds" optionValuePath="content.id"
	       optionLabelPath="content.name"  valueBinding="content.from"}}
    {{/if}}
</div>