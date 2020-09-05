package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class Permission {

	String permName = "";
	String permDesc = "";
	int permID = -1;
	int permValue = 0;
	boolean negated = false;
	boolean skip = false;

	public Permission(String permName, int permID, int permValue, boolean negated, boolean skip) {
		this.permName = permName;
		this.permID = permID;
		this.permValue = permValue;
		this.negated = negated;
		this.skip = skip;
	}

	public Permission(int permID, int permValue, boolean negated, boolean skip) {
		this.permID = permID;
		this.permValue = permValue;
		this.negated = negated;
		this.skip = skip;
	}

	public Permission(String permName, int permValue, boolean negated, boolean skip) {
		this.permName = permName;
		this.permValue = permValue;
		this.negated = negated;
		this.skip = skip;
	}

	public Permission(String permName, int permID, int permValue) {
		this.permName = permName;
		this.permID = permID;
		this.permValue = permValue;
	}

	public Permission(int permID, int permValue) {
		this.permID = permID;
		this.permValue = permValue;
	}

	public Permission(String permName, int permValue) {
		this.permName = permName;
		this.permValue = permValue;
	}

	public Permission(int permID, String permName, String permDesc) {
		this.permID = permID;
		this.permName = permName;
		this.permDesc = permDesc;
	}

	public Permission(int permID, String permName) {
		this.permID = permID;
		this.permName = permName;
	}

	public String getPermName() {
		return this.permName;
	}

	public int getPermID() {
		return this.permID;
	}

	public int getPermValue() {
		return this.permValue;
	}

	public String getPermDesc() {
		return this.permDesc;
	}

	public boolean isNegated() {
		return this.negated;
	}

	public boolean isSkip() {
		return this.skip;
	}
}
