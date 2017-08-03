package com.examplemod;

import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.modconfig.AbstractConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.minecraft.client.resources.I18n;

/**
 * This is a simple example of adding a config panel to a mod. Your LiteMod
 * class should implement {@link Configurable} and return this class in order to
 * support the settings functionality of the mod panel.
 * 
 * @author Adam Mummery-Smith
 */
public class ExampleModConfigPanel extends AbstractConfigPanel
{
    /* (non-Javadoc)
     * @see com.mumfrey.liteloader.modconfig.ConfigPanel#getPanelTitle()
     */
    @Override
    public String getPanelTitle()
    {
        return I18n.format("examplemod.config.title");
    }
    
    /* (non-Javadoc)
     * @see com.mumfrey.liteloader.modconfig.AbstractConfigPanel#addOptions(
     *      com.mumfrey.liteloader.modconfig.ConfigPanelHost)
     */
    @Override
    protected void addOptions(ConfigPanelHost host)
    {
        final LiteModExample mod = host.<LiteModExample>getMod();
        
        this.addLabel(1, 0, 0, 200, 32, 0xFFFF55, I18n.format("examplemod.config.help.1"), I18n.format("examplemod.config.help.2"));
        this.addControl(new GuiCheckbox(0, 0, 32, I18n.format("examplemod.config.option.enabled")), new ConfigOptionListener<GuiCheckbox>()
        {
            @Override
            public void actionPerformed(GuiCheckbox control)
            {
                mod.setClockVisibility(control.checked = !control.checked);
            }
        }).checked = mod.getClockVisibility();
    }

    @Override
    public void onPanelHidden()
    {
        // This example applies the changes immediately, however you may wish to
        // only save changes when the user clicks "save and close". In which
        // case you should apply your changes here
    }
}
