package com.examplemod;

import com.mumfrey.liteloader.PreRenderListener;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.io.File;

/**
 * This is a very simple example LiteMod, it draws an analogue clock on the minecraft HUD using
 * a traditional onTick hook supplied by LiteLoader's "Tickable" interface.
 *
 * @author Adam Mummery-Smith
 */
public class LiteModExample implements Tickable, PreRenderListener
{
	/**
	 * This is our instance of Clock which we will draw every tick
	 */
	private Clock clock = new Clock(10, 10);
	
	/**
	 * This is a keybinding that we will register with the game and use to toggle the clock
	 * 
	 * Notice that we specify the key name as an *unlocalised* string. The localisation is provided from the included resource file
	 */
	private static KeyBinding clockKeyBinding = new KeyBinding("key.clock.toggle", Keyboard.KEY_F12, "key.categories.litemods");
	
	private ClockCloudConfig preferences = new ClockCloudConfig(this.clock);
	
	/**
	 * Default constructor. All LiteMods must have a default constructor. In general you should do very little
	 * in the mod constructor EXCEPT for initialising any non-game-interfacing components or performing
	 * sanity checking prior to initialisation
	 */
	public LiteModExample()
	{
	}
	
	/**
	 * getName() should be used to return the display name of your mod and MUST NOT return null
	 * 
	 * @see com.mumfrey.liteloader.LiteMod#getName()
	 */
	@Override
	public String getName()
	{
		return "Example Mod";
	}
	
	/**
	 * getVersion() should return the same version string present in the mod metadata, although this is
	 * not a strict requirement.
	 * 
	 * @see com.mumfrey.liteloader.LiteMod#getVersion()
	 */
	@Override
	public String getVersion()
	{
		return "0.0.0";
	}
	
	/**
	 * init() is called very early in the initialisation cycle, before the game is fully initialised, this
	 * means that it is important that your mod does not interact with the game in any way at this point.
	 * 
	 * @see com.mumfrey.liteloader.LiteMod#init(java.io.File)
	 */
	@Override
	public void init(File configPath)
	{
		// The key binding declared above won't do anything unless we register it, ModUtilties provides 
		// a convenience method for this
		LiteLoader.getInput().registerKeyBinding(LiteModExample.clockKeyBinding);
		
		this.clock.setSize(this.preferences.size);
		this.clock.setVisible(this.preferences.visible);
	}
	
	/**
	 * upgradeSettings is used to notify a mod that its version-specific settings are being migrated
	 * 
	 * @see com.mumfrey.liteloader.LiteMod#upgradeSettings(java.lang.String, java.io.File, java.io.File)
	 */
	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath)
	{
	}
	
	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
	{
		// The three checks here are critical to ensure that we only draw the clock as part of the "HUD"
		// and don't draw it over active GUI's or other elements
		if (inGame && minecraft.currentScreen == null && Minecraft.isGuiEnabled())
		{
			if (LiteModExample.clockKeyBinding.isPressed())
			{
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
				{
					this.preferences.size = (this.preferences.size << 1) & 0x1FF;
					this.clock.setSize(this.preferences.size);
					this.preferences.size = this.clock.getSize();
				}
				else
				{
					this.clock.setVisible(!this.clock.isVisible());
					this.preferences.visible = this.clock.isVisible();
				}
				
				// Our @Expose annotations control what properties get saved, this tells liteloader to
				// actually write the properties to disk
				LiteLoader.getInstance().writeConfig(this);
			}
			
			// Render the clock
			this.clock.render(minecraft);
		}
	}

	@Override
	public void onRenderWorld(float partialTicks)
	{
//		System.err.printf(">> onRenderWorld!\n");
	}

	@Override
	public void onSetupCameraTransform(float partialTicks, int pass, long timeSlice)
	{
//		System.err.printf(">> onSetupCameraTransform %s, %d, %d!\n", partialTicks, pass, timeSlice);
	}

	@Override
	public void onRenderSky(float partialTicks, int pass)
	{
//		System.err.printf(">> onRenderSky %s, %d!\n", partialTicks, pass);
	}

	@Override
	public void onRenderClouds(float partialTicks, int pass, RenderGlobal renderGlobal)
	{
//		System.err.printf(">> onRenderClouds %s, %d!\n", partialTicks, pass);
	}

	@Override
	public void onRenderTerrain(float partialTicks, int pass)
	{
//		System.err.printf(">> onRenderTerrain %s, %d!\n", partialTicks, pass);
	}
}
