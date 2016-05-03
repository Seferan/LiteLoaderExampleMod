package com.examplemod;

import com.mumfrey.liteloader.modconfig.CloudConfig;
import com.mumfrey.webprefs.WebPreferencesManager;

public class ClockCloudConfig extends CloudConfig
{
	/**
	 * Replicated variable, this will be synchronised with the cloud service
	 */
	public int size = 64;
	
	/**
	 * Replicated variable, this will also be synchronised with the cloud
	 * service
	 */
	public boolean visible = true;
	
	/**
	 * Cached so we can apply values to the clock when received, we could also
	 * have this object call back against the mod for example but this is
	 * simpler 
	 */
	private transient Clock clock;
	
	public ClockCloudConfig(Clock clock)
	{
		super(WebPreferencesManager.getDefault(), true);
		this.clock = clock;
	}
	
	/* (non-Javadoc)
	 * @see com.mumfrey.liteloader.modconfig.CloudConfig#onUpdated()
	 */
	@Override
	protected void onUpdated()
	{
		this.clock.setVisible(this.visible);
		this.clock.setSize(this.size);
	}
}
