package com.riwa;

import com.riwa.util.font.f1;
import com.riwa.util.icon.i1;
import com.riwa.util.render.r1;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = riwa.a, name = "riwa", version = riwa.b)
public class riwa {
    public static final String a = "riwa";
    public static final String b = "1.0";

    private static Logger c;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        c = event.getModLog();
        f1.a();
        r1.a();
        i1.a();
        MinecraftForge.EVENT_BUS.register(new k1());
        c.info("riwa render api initialized");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        c.info("riwa ready");
    }
}
