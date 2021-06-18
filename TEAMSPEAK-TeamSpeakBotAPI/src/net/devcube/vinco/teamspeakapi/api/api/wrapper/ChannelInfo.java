package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.io.File;

import net.devcube.vinco.teamspeakapi.api.api.property.ChannelCodec;

public class ChannelInfo extends DefaultInfo {

	private int cid;

	public ChannelInfo(String[] infos, int chid) {
		super(infos);
		this.cid = chid;
	}

	public String[] getInfos() {
		return infos;
	}

	public void printInfos() {
		int i = 0;

		for (String s : infos) {
			System.out.println(i + " : " + s);
			++i;
		}

	}

	public int getChannelID() {
		return cid;
	}

	public String getChannelOrginalName() {
		return get(1);
	}

	public String getChannelName() {
		String s = get(1);
		s = s.replace("\\s", " ");
		s = s.replace("\\p", "|");
		return s;
	}

	public String getChannelTopic() {
		return get(2);
	}

	public String getChannelDescription() {
		return get(3);
	}

	public String getChannelPassword() {
		return get(4);
	}

	private int getChannelQuality() {
		return Integer.parseInt(get(6));
	}

	public ChannelCodec getChannelCodec() {
		for (ChannelCodec channelcodec : ChannelCodec.values()) {
			if (channelcodec.getValue() == getChannelQuality()) {
				return channelcodec;
			}
		}

		return null;
	}

	public int getChannelMaxClients() {
		return Integer.parseInt(get(7));
	}

	public int getChannelMaxFamilyClients() {
		return Integer.parseInt(get(8));
	}

	public int getNeededTalkPower() {
		return Integer.parseInt(get(22));
	}

	public boolean isPermanent() {
		return toBol(toInt(get(10)));
	}

	public boolean isSemiPermanent() {
		return toBol(toInt(get(11)));
	}

	public boolean isDefault() {
		return toBol(toInt(get(12)));
	}

	public boolean hasPassword() {
		return toBol(toInt(get(13)));
	}

	public File getFilePath() {
		return new File(get(21).replace("\\/", "/"));
	}

	public int getDeleteDelay() {
		return toInt(get(17));
	}

	public int getSecondsEmpty() {
		return toInt(get(27));
	}

	public int getIconID() {
		return toInt(get(26));
	}
}
