package com.mykro.malibu.common.model;

/**
 * A property that represents a Tax levy, either fixed or proportional.
 */
public class TaxProperty extends BaseProperty {

	public TaxProperty(String name, int fixedTax, int proportionalRate ) {
		super(name);
	}

	@Override
	public void playerPasses(Player player) {
	}

	@Override
	public void playerArrives(Player player) {
	}

}
