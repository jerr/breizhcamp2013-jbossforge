breizhcamp2013-jbossforge
=========================

Live coding with JbossForge at the BreizhCamp 2013

Step 1 : Create project
=======================

```
	new-project --named logbook --topLevelPackage org.printstacktrace.logbook --projectFolder . --type war

	gitignore list-templates
	gitignore create Java Maven Eclipse
	cd ~~

	forge find-plugin format
	mkdir etc
	cp ../formatter.xml etc/
	formatter setup etc/formatter.xml
```

Step 2 : Enable CDI & JPA
=========================

```
	beans setup
	persistence setup --provider HIBERNATE --container JBOSS_AS7
```

Step 3 : Create entities
========================

```
	entity --named Server --idStrategy SEQUENCE
	field string --named name
	field string --named description
	
	entity --named  Application
	field string --named name
	field manyToOne --named server --fieldType org.printstacktrace.logbook.model.Server.java --inverseFieldName applications --fetchType LAZY

	entity --named Event
	field string --named description
	field temporal --type TIMESTAMP --named start
	field temporal --type TIMESTAMP --named end
	field manyToOne --named server --fieldType org.printstacktrace.logbook.model.Server.java --inverseFieldName events --fetchType LAZY

	validation setup --provider HIBERNATE_VALIDATOR
	constraint NotNull --onProperty start
	constraint Past --onProperty start

	cd ~~
	formatter . --recursive --skipComments
	build
```

Step 4 : Create the application (rest+angularjs)
================================================

```
	rest setup --activatorType WEB_XML
	rest endpoint-from-entity --contentType application/json ~.model.*
	
	list-scaffoldx-providers
	scaffold-x setup --scaffoldType angularjs --installTemplates
	scaffold-x from src/main/java/org/printstacktrace/logbook/model/*
	
	formatter . --recursive --skipComments
	build
	
	as7 setup 	
	as7 start
	as7 deploy
	as7 shutdown
```

Step 7 : Adding tests
=====================
*Configure arquillian with JBoss AS 7.1.1 container : (Be careful to choose the correct JBoss version)* 

```
	arquillian setup --containerType REMOTE --containerName JBOSS_AS_REMOTE_7.X
	
	forge find-plugin selenium
	arq-drone setup
	arq-drone configure-webdriver --browserCapabilities firefox
	arq-drone create-test --named Index
	cd ~~
	build --profile arq-jboss_as_remote_7.x
	
	arq-graphene setup
	arq-graphene new-page --named Index
	arq-graphene new-element --named serversLink --findby linkText --value Servers
	arq-graphene new-page --named Server
	arq-graphene new-element --named createButton --findby id --value Create
	arq-graphene new-element --named nameInput --findby id --value name
	arq-graphene new-element --named saveServerButton --findby id --value saveServer
	cd ~~
	formatter . --recursive --skipComments	
```

*Editing IndexTest.java to add pages like this :*

```java
    @Page
    IndexPage indexPage;

    @Page
    ServerPage serverPage;
```

*Editing IndexTest.java to modify the test method :*

```java
    @Test
    public void testIsDeployed() throws InterruptedException {
		browser.navigate().to(baseUrl);
		Thread.sleep(1000);
		indexPage.getServersLink().click();
		Thread.sleep(1000);
		serverPage.getCreateButton().click();
		Thread.sleep(1000);
		serverPage.getNameInput().sendKeys("myserver");
		Thread.sleep(1000);
		serverPage.getSaveServerButton().click();
		Thread.sleep(5000);
    }
```

*Run the test :*
```
  build --profile arq-jboss_as_remote_7.x
```