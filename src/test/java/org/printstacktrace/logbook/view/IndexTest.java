package org.printstacktrace.logbook.view;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.spi.annotations.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.printstacktrace.logbook.pages.IndexPage;
import org.printstacktrace.logbook.pages.ServerPage;

@RunWith(Arquillian.class)
public class IndexTest {

    private static String WEBAPP_SRC = "src/main/webapp";
    @Drone
    private WebDriver browser;
    @ArquillianResource
    private URL baseUrl;

    @Page
    IndexPage indexPage;

    @Page
    ServerPage serverPage;

    @Deployment(testable = false)
    static public WebArchive createDeployment() {
	return ShrinkWrap
		.create(WebArchive.class, "indextest.war")
		.addPackages(true, "org.printstacktrace.logbook")
		.addAsResource("META-INF/persistence.xml",
			"META-INF/persistence.xml")
		.addAsResource("META-INF/validation.xml",
			"META-INF/validation.xml").as(ExplodedImporter.class)
		.importDirectory(WEBAPP_SRC).as(WebArchive.class);
    }

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
}