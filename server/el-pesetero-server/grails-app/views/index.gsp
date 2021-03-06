<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Bienvenido a El Pesetero</title>
<%--		 <r:require modules="dialog, datePicker"/>--%>
		<style type="text/css" media="screen">
			#status {
				background-color: #eee;
				border: .2em solid #fff;
				margin: 2em 2em 1em;
				padding: 1em;
				width: 12em;
				float: left;
				-moz-box-shadow: 0px 0px 1.25em #ccc;
				-webkit-box-shadow: 0px 0px 1.25em #ccc;
				box-shadow: 0px 0px 1.25em #ccc;
				-moz-border-radius: 0.6em;
				-webkit-border-radius: 0.6em;
				border-radius: 0.6em;
			}

			.ie6 #status {
				display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
			}

			#status ul {
				font-size: 0.9em;
				list-style-type: none;
				margin-bottom: 0.6em;
				padding: 0;
			}
            
			#status li {
				line-height: 1.3;
			}

			#status h1 {
				text-transform: uppercase;
				font-size: 1.1em;
				margin: 0 0 0.3em;
			}

			#page-body {
				margin: 2em 1em 1.25em 18em;
			}

			h2 {
				margin-top: 1em;
				margin-bottom: 0.3em;
				font-size: 1em;
			}

			p {
				line-height: 1.5;
				margin: 0.25em 0;
			}

			#controller-list ul {
				list-style-position: inside;
			}

			#controller-list li {
				line-height: 1.3;
				list-style-position: inside;
				margin: 0.25em 0;
			}

			@media screen and (max-width: 480px) {
				#status {
					display: none;
				}

				#page-body {
					margin: 0 1em 1em;
				}

				#page-body h1 {
					margin-top: 0;
				}
			}
		</style>
		
	</head>
	<body >
		<img class="banner" src="${resource(dir: 'images', file: 'coins-banner3.png')}" alt="Coins"/>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="status" role="complementary">
			<h1>Acciones rápidas</h1>
			<ul id="fast-actions">
				<li><i class="foundicon-cart"><g:link elementId="add-expense-link" controller='expenseLine' action='create'>Nuevo gasto</g:link></i></li>
				<li><i class="foundicon-address-book"><g:link controller="securityUser" action="edit" params='[id:"${userInstance.securityUser.id}"]'>Editar perfil</g:link></i></li>
			</ul>			
			<div id="expense-dialog-form" class="el-pesetero-dialog" title="Añadir gasto">
				<g:formRemote name="newExpenseForm" url="[controller: 'expenseLine', action: 'save']" method="post" update="lastExpenses">
				<fieldset class="form">
					<g:render template="/expenseLine/form" model="['expenseLineInstance': new es.elpesetero.ExpenseLine()]"/>
				</fieldset>
				<fieldset class="expense_form">
					<g:render template="/expense/form" model="['expenseInstance':new es.elpesetero.Expense()]"/>
				</fieldset>
				</g:formRemote>
			</div>
			<h1>Installed Plugins</h1>
			<ul>
				<g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
					<li>${plugin.name} - ${plugin.version}</li>
				</g:each>
			</ul>
		</div>				
		<div id="page-body" role="main">			
			<g:if test="${!userInstance}">
				<p>Bienvenido al pesetero si tienes una cuenta en google puedes <g:link controller="openId">logarte</g:link> en El Pesetero</p>				
			</g:if>
			<g:if test="${userInstance}">
				<div id="appHolder"></div>
				<script type="text/x-handlebars" data-template-name="application">
					<h1>Bienvenido, {{currentUser.username}}</h1>
					{{#if IndexApp.lastMessage}}
					<div class="message" role="status">{{IndexApp.lastMessage}}</div>
					{{/if}}
					{{#if IndexApp.lastError}}
					<div class="errors" role="status">{{IndexApp.lastError}}</div>
					{{/if}}
					<div id="summary">
					{{outlet}}
					</div>
				</script>
				<script type="text/x-handlebars" data-template-name="me">
					{{view IndexApp.FundsView}}
					<div  class="summaryBox">
						<strong>Categorías:</strong>
							<ul>
								<g:each var="category" in="${userInstance.topCategories }">
									<li>${category} <g:link elementClass="link" controller="expenseCategory" action="show" id="${category.id}">Ver></g:link></li>
								</g:each>
							</ul>
					</div>
					{{view IndexApp.ExpensesView}}														
				</script>
				<script type="text/x-handlebars" data-template-name="funds">
					<div id="userData" class="summaryBox">
						{{#unless IndexApp.isMain}}
						<a {{action goToMe href=true}} class="foundicon-left-arrow"></a>
					    {{/unless}}
						<p><strong>Fondos: </strong><a {{action withdraw href=true}} class="foundicon-inbox link">Sacar dinero</a>
						{{outlet}}
						<ul class="table-list">	
							{{#each fund in me.funds}}												
							<li><label>{{fund.name}} : </label><span>{{fund.quantity}}€</span> 
								<a {{action goToFund fund href=true}} class="foundicon-right-arrow"></a>
							</li>
							{{/each}}
							<li class="total"><label>Balance : </label><span>{{me.totalBalance}}€</span></li>
						</ul>
						</p>
					</div>
				</script>
				<script type="text/x-handlebars" data-template-name="expenses">
					<div id="expenseSummary" class="summaryBox">
						{{#unless IndexApp.isMain}}
						<a {{action goToMe href=true}} class="foundicon-left-arrow"></a>
					    {{/unless}}
						<strong>Últimos gastos:</strong><a {{action addExpense href=true}} class="foundicon-plus link">Nuevo gasto</a>
						{{outlet expenseEdit}}
						<ul class="table-list" id="lastExpenses">
							{{#each expense in me.lastExpenses}}
								{{view IndexApp.ExpenseView}}															
							{{/each}}
						</ul>
					</div>
				</script>
				<script type="text/x-handlebars" data-template-name="expense">
					<li>
						<label>{{expense.expenseDate}} - {{expense.name}} : </label><span>{{expense.quantity}}€</span> from <a {{action goToFund expense.from href=true}}>{{expense.from.name}}</a>						
					</li>
				</script>
				<script type="text/x-handlebars" data-template-name="expenseAdd">
					<hr/>
					<fieldset class="form">
						<g:render template="/expenseLine/form_ember" model="['expenseLineInstance': new es.elpesetero.ExpenseLine()]"/>
					</fieldset>
					<fieldset class="expense_form">
						<g:render template="/expense/form_ember" model="['expenseInstance':new es.elpesetero.Expense()]"/>
						<input type="submit" {{action submitExpense on="click"}} value="Añadir gasto"/>
					</fieldset>
					<hr/>					
				</script>
				<script type="text/x-handlebars" data-template-name="withdraw">
					<hr/>					
					<fieldset class="form">
						<g:render template="/fund/withdraw_form_ember"/>						
						<input type="submit" {{action submitWithdraw on="click"}} value="Sacar Dinero"/>
					</fieldset>
					<hr/>					
				</script>
			</g:if>
			<script type="text/x-handlebars" data-template-name="fund">
				<div {{bindAttr id="fundElementId"}} class="summaryBox">
					<a {{action goToMe href=true}} class="foundicon-left-arrow"></a>
					<strong>{{content.name}}</strong>
					Saldo: {{content.quantity}}€
					<ul class="table-list">Últimos gastos:
						{{#each expense in content.lastExpenses}}
							<li><label>{{expense.expenseDate}} - {{expense.name}} : </label><span>{{expense.quantity}}€</span> </li>
				
						{{/each}}
					</ul>
				</div>
			</script>
			<div id="controller-list" role="navigation">
				<h2>Available Controllers:</h2>
				<ul>
					<g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
						<li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
					</g:each>
				</ul>
			</div>
		</div>
		<r:script>
			
			var submitNuevoGasto = function() {
				jQuery("#newExpenseForm").submit();
				jQuery("#expense-dialog-form").dialog("close");
			}
			jQuery(document).ready(function(){				
				jQuery("#expense-dialog-form").dialog({
					autoOpen: false,					
					model: true,
					width: 420,
					buttons: {
						"Añadir gasto": submitNuevoGasto
					}
				});
				jQuery( "#add-expense-link" ).click(function() {
    				jQuery( "#expense-dialog-form" ).dialog( "open" );
    				return false;
   				});
			})
		</r:script>
		<g:javascript library="index"/>
	</body>
</html>
