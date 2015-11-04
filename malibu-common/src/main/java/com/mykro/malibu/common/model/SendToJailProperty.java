package com.mykro.malibu.common.model;

/**
 * A property that represents Go To Jail.
 */
public class SendToJailProperty extends BaseProperty {

	private JailProperty jail;
	
	public SendToJailProperty(String name, JailProperty jail) {
		super(name);
		this.jail = jail;
	}

	@Override
	public void playerPasses(Player player) {
	}

	@Override
	public void playerArrives(Player player) {
		player.relocate(jail);
	}

}
