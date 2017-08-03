package com.gp.sync.web.socket;

import java.security.Principal;

public class TestPrincipal implements Principal {

	private final String name;
	private final String passcode;

	public TestPrincipal(String name, String passcode) {
		this.name = name;
		this.passcode = passcode;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public String getPasscode() {
		return passcode;
	}

	@Override
	public String toString() {
		return "TestPrincipal [name=" + name + ", passcode=" + passcode + "]";
	}

}
