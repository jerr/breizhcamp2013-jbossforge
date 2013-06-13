package org.printstacktrace.logbook.pages;

import org.openqa.selenium.WebElement;
import org.jboss.arquillian.graphene.spi.annotations.Root;
import org.openqa.selenium.support.FindBy;

public class IndexPage {

    @Root
    private WebElement root;
    @FindBy(linkText = "Servers")
    private WebElement serversLink;

    public WebElement getServersLink() {
	return this.serversLink;
    }
}