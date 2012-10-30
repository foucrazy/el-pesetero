<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Bienvenido a El Pesetero</title>
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
	<body>
		<img src="${resource(dir: 'images', file: 'coins-banner3.png')}" alt="Coins"/>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="status" role="complementary">
			<h1>Application Status</h1>
			<ul>
				<li>App version: <g:meta name="app.version"/></li>
				<li>Grails version: <g:meta name="app.grails.version"/></li>
				<li>Groovy version: ${org.codehaus.groovy.runtime.InvokerHelper.getVersion()}</li>
				<li>JVM version: ${System.getProperty('java.version')}</li>
				<li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
				<li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
				<li>Domains: ${grailsApplication.domainClasses.size()}</li>
				<li>Services: ${grailsApplication.serviceClasses.size()}</li>
				<li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
			</ul>
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
				<h1>Bienvenido, ${userInstance.username}</h1>
				<div id="summary">
					<div id="userData" class="summaryBox">
						<strong>Balance</strong> <g:formatNumber number="${userInstance?.totalBalance}" type="currency" currencyCode="EUR" />
						<p><strong>Fondos:</strong>
						<ul>						
							<g:each var="fund" in="${userInstance.funds}">
							<li>${fund} <g:link elementClass="link" controller="fund" action="show" id="${fund.id}">Ver></g:link></li>
							</g:each>
						</ul>
						</p>
					</div>
					<div  class="summaryBox">
						<strong>Categorías:</strong>
							<ul>
								<g:each var="category" in="${userInstance.topCategories }">
									<li>${category} <g:link elementClass="link" controller="expenseCategory" action="show" id="${category.id}">Ver></g:link></li>
								</g:each>
							</ul>
					</div>
					<div id="expenseSummary" class="summaryBox">
						<strong>Últimos gastos</strong>			
						<ul>	
							<g:each var="line" in="${userInstance.lastExpenses()}">
								<li class="controller">
									${line} <g:link elementClass="link" controller="expenseLine" action="show" id="${line.id}">Ver></g:link>
								</li>
							</g:each>
						</ul>
					</div>
				</div>
				 
			</g:if>
	
			<div id="controller-list" role="navigation">
				<h2>Available Controllers:</h2>
				<ul>
					<g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
						<li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
					</g:each>
				</ul>
			</div>
		</div>
	</body>
</html>
