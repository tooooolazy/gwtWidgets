/**
 * 
 */
package com.tooooolazy.gwt.widgets.client;

import com.google.gwt.user.client.Command;

/**
 * @author tooooolazy
 *
 */
public abstract class NamedCommand implements Command {
	protected String name;
	
	public NamedCommand() {
		name = getClass().getName();
		name = name.substring( name.lastIndexOf('$')+1 );
		String s = getClass().getSuperclass().getName();
	}
	public NamedCommand(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
