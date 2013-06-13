package org.printstacktrace.logbook.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.printstacktrace.logbook.model.Server;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;
    @Version
    @Column(name = "version")
    private int version = 0;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToMany
    private Set<Server> servers = new HashSet<Server>();

    public Long getId() {
	return this.id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public int getVersion() {
	return this.version;
    }

    public void setVersion(final int version) {
	this.version = version;
    }

    @Override
    public boolean equals(Object that) {
	if (this == that) {
	    return true;
	}
	if (that == null) {
	    return false;
	}
	if (getClass() != that.getClass()) {
	    return false;
	}
	if (id != null) {
	    return id.equals(((Application) that).id);
	}
	return super.equals(that);
    }

    @Override
    public int hashCode() {
	if (id != null) {
	    return id.hashCode();
	}
	return super.hashCode();
    }

    public String getName() {
	return this.name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public String getDescription() {
	return this.description;
    }

    public void setDescription(final String description) {
	this.description = description;
    }

    @Override
    public String toString() {
	String result = getClass().getSimpleName() + " ";
	if (name != null && !name.trim().isEmpty())
	    result += "name: " + name;
	if (description != null && !description.trim().isEmpty())
	    result += ", description: " + description;
	return result;
    }

    public Set<Server> getServers() {
	return this.servers;
    }

    public void setServers(final Set<Server> servers) {
	this.servers = servers;
    }
}