package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.property.TSPermission;

public class Permission {

	private String name = "";
	private String desc = "";
	private int id = -1;
	private int value = 0;
	private boolean negated = false;
	private boolean skip = false;
	
	public Permission(String permName, int permID, int permValue, boolean negated, boolean skip, String permDesc) {
		this.name = permName;
		this.id = permID;
		this.value = permValue;
		this.negated = negated;
		this.skip = skip;
		this.desc = permDesc;
	}
	
	public Permission(String permName, int permID, int permValue, boolean negated, boolean skip) {
		this(permName, permID, permValue, negated, skip, null);
	}
	
	public Permission(int permID, int permValue, boolean negated, boolean skip) {
		this(null, permID, permValue, negated, skip, null);
	}
	
	public Permission(TSPermission permission, int permValue, boolean negated, boolean skip) {
		this(permission.getName(), permission.getValue(), permValue, negated, skip, null);
	}
	
	public Permission(String permName, int permValue, boolean negated, boolean skip) {
		this(permName, -1, permValue, negated, skip, null);
	}

	public Permission(String permName, int permID, int permValue) {
		this(permName, permID, permValue, false, false, null);
	}

	public Permission(int permID, int permValue) {
		this(null, permID, permValue, false, false, null);
	}
	
	public Permission(TSPermission permission, int permValue) {
		this(permission.getName(), permission.getValue(), permValue, false, false, null);
	}

	public Permission(String permName, int permValue) {
		this(permName, -1, permValue, false, false, null);
	}

	public Permission(int permID, String permName) {
		this(permName, permID, 0, false, false, null);
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

	public String getName() {
		return this.name;
	}

	public int getPermID() {
		return this.id;
	}
	
	public int getID() {
		return getPermID();
	}
	
	public int getValue() {
		return this.value;
	}

	public String getDescription() {
		return this.desc;
	}

	public boolean isNegated() {
		return this.negated;
	}

	public boolean isSkip() {
		return this.skip;
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("Permission[");
		resultBuilder.append("ID=").append(getPermID());
		resultBuilder.append(",Value=").append(getValue());
		resultBuilder.append(",Negated=").append(isNegated());
		resultBuilder.append(",Skip=").append(isSkip());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
