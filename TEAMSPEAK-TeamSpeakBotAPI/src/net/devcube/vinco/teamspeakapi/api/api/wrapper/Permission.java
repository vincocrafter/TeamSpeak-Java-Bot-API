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
		this(null, permID, permValue, negated, skip);
	}

	public Permission(String permName, int permValue, boolean negated, boolean skip) {
		this(permName, -1, permValue, negated, skip);
	}

	public Permission(String permName, int permID, int permValue) {
		this(permName, permID, permValue, false, false);
	}

	public Permission(int permID, int permValue) {
		this(null, permID, permValue, false, false);
	}

	public Permission(String permName, int permValue) {
		this(permName, -1, permValue, false, false);
	}

	public Permission(int permID, String permName) {
		this(permName, permID, 0, false, false);
	}

	public Permission(String permName, int permID, int permValue, boolean negated, boolean skip, String permDesc) {
		this.permName = permName;
		this.permID = permID;
		this.permValue = permValue;
		this.negated = negated;
		this.skip = skip;
		this.permDesc = permDesc;
	}

	public Permission(String permName, int permID, String permDesc) {
		this(permName, permID, -1, false, false, permDesc);
	}
	
	public Permission(String permName, int permID, int permValue, String permDesc) {
		this(permName, permID, permValue, false, false, permDesc);
	}

	public Permission(String permName, String permDesc) {
		this(permName, -1, -1, false, false, permDesc);
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
