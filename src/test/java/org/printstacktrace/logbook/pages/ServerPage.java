package org.printstacktrace.logbook.pages;

import org.openqa.selenium.WebElement;
import org.jboss.arquillian.graphene.spi.annotations.Root;
import org.openqa.selenium.support.FindBy;

public class ServerPage {

    @Root
    private WebElement root;
    @FindBy(id = "Create")
    private WebElement createButton;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "saveServer")
    private WebElement saveServerButton;

    public WebElement getCreateButton() {
	return this.createButton;
    }

    public WebElement getNameInput() {
	return this.nameInput;
    }

    public WebElement getSaveServerButton() {
	return this.saveServerButton;
    }
}