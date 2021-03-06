<%@ page import="es.elpesetero.ExpenseCategory" %>

<div class="fieldcontain ${hasErrors(bean: expenseCategoryInstance, field: 'user', 'error')} ">
	<label for="user">
		<g:message code="expenseCategory.user.label" default="User" />
	</label>
	${theUser?.username}	
</div>

<div class="fieldcontain ${hasErrors(bean: expenseCategoryInstance, field: 'parent', 'error')} ">
	<label for="parent">
		<g:message code="expenseCategory.parent.label" default="Parent" />
		
	</label>
	<g:select id="parent" name="parent.id" from="${es.elpesetero.ExpenseCategory.findAllByUser(theUser)}" optionKey="id" value="${expenseCategoryInstance?.parent?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>


<div class="fieldcontain ${hasErrors(bean: expenseCategoryInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="expenseCategory.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${expenseCategoryInstance?.name}"/>
</div>

