package com.craftablecapes.registry;

import com.craftablecapes.CraftableCapes;
import com.craftablecapes.items.CapeItem;
import com.craftablecapes.items.OnlineCapeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Registry handler for all cape items
 * Registers both local texture capes and online texture capes
 * Total: 90+ capes ported from Fabric version
 */
@EventBusSubscriber(modid = CraftableCapes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapeRegistry {
    private static final Logger LOGGER = LogManager.getLogger();
    
    // ==================== Official Mojang Capes ====================
    public static CapeItem CAPE_OLD_MOJANG;
    public static CapeItem CAPE_MOJANG;
    public static CapeItem CAPE_NEW_MOJANG;
    
    // ==================== Minecon Capes ====================
    public static CapeItem CAPE_MINECON_2011;
    public static CapeItem CAPE_MINECON_2012;
    public static CapeItem CAPE_MINECON_2013;
    public static CapeItem CAPE_MINECON_2015;
    public static CapeItem CAPE_MINECON_2016;
    
    // ==================== Translator Capes ====================
    public static CapeItem CAPE_TRANSLATOR;
    public static CapeItem CAPE_TRANSLATOR_CN;
    public static CapeItem CAPE_TRANSLATOR_JP;
    
    // ==================== Special Event Capes ====================
    public static CapeItem CAPE_MILLIONTH_CUSTOMER;
    public static CapeItem CAPE_DB;
    public static CapeItem CAPE_SNOWMAN;
    public static CapeItem CAPE_SPADE;
    public static CapeItem CAPE_PRISMARINE;
    public static CapeItem CAPE_TURTLE;
    public static CapeItem CAPE_BIRTHDAY;
    public static CapeItem CAPE_SCROLLS;
    public static CapeItem CAPE_COBALT;
    public static CapeItem CAPE_MOJIRA;
    public static CapeItem CAPE_REALMS;
    public static CapeItem CAPE_MIGRATOR;
    public static CapeItem CAPE_VANILLA;
    public static CapeItem CAPE_CHERRY_BLOSSOM;
    public static CapeItem CAPE_15_YEARS;
    public static CapeItem CAPE_MOJANG_OFFICE;
    public static CapeItem CAPE_YEARN;
    public static CapeItem CAPE_HOME;
    public static CapeItem CAPE_MENACE;
    public static CapeItem CAPE_COMMON;
    public static CapeItem CAPE_DOCUMENT;
    public static CapeItem CAPE_ZOMBIE_HORSE;
    public static CapeItem CAPE_MOONLIGHT_TRAIL;
    public static CapeItem CAPE_CRAFTER;
    public static CapeItem CAPE_BUILDER;
    public static CapeItem CAPE_OXEYE;
    public static CapeItem CAPE_COPPER;
    
    // ==================== Local Texture Capes (Need PNG files) ====================
    public static CapeItem CAPE_TWITCH;
    public static CapeItem CAPE_TIKTOK;
    public static CapeItem CAPE_10_YEARS;
    public static CapeItem CAPE_AWESOM;
    public static CapeItem CAPE_BACON;
    public static CapeItem CAPE_BLONK;
    public static CapeItem CAPE_FROG;
    public static CapeItem CAPE_MCC15;
    public static CapeItem CAPE_MINECON_3;
    public static CapeItem CAPE_MINECON_4;
    public static CapeItem CAPE_MINECON_5;
    public static CapeItem CAPE_NO_CIRCLE;
    public static CapeItem CAPE_NYAN;
    public static CapeItem CAPE_NYE_2011;
    public static CapeItem CAPE_PANCAPE;
    public static CapeItem CAPE_PRIDE;
    public static CapeItem CAPE_SNAIL;
    public static CapeItem CAPE_SQUID;
    public static CapeItem CAPE_VETERINARIAN;
    public static CapeItem CAPE_VILLAGER_RESCUE;
    public static CapeItem CAPE_XBOX;
    public static CapeItem CAPE_XBOX_BDAY;
    public static CapeItem CAPE_XMAS;
    public static CapeItem CAPE_SNIFFER;
    public static CapeItem CAPE_VALENTINE;
    
    // Minecraft Dungeons Capes
    public static CapeItem CAPE_AMETHYST;
    public static CapeItem CAPE_BLUE;
    public static CapeItem CAPE_CLOUDY_CLIMB;
    public static CapeItem CAPE_COW_CRUSADER;
    public static CapeItem CAPE_DOWNPOUR_CAPE;
    public static CapeItem CAPE_FAUNA_FAIRE;
    public static CapeItem CAPE_GLOW;
    public static CapeItem CAPE_HAMMER;
    public static CapeItem CAPE_HERO;
    public static CapeItem CAPE_ICEOLOGER;
    public static CapeItem CAPE_LUMINOUS_NIGHT;
    public static CapeItem CAPE_MYSTERY;
    public static CapeItem CAPE_PHANTOM;
    public static CapeItem CAPE_PRISM_CAPE;
    public static CapeItem CAPE_RED_ROYAL;
    public static CapeItem CAPE_SOUL;
    public static CapeItem CAPE_TURTLE_SHELL;
    public static CapeItem CAPE_YEAR_1;
    public static CapeItem CAPE_YEAR_2;
    
    // Custom Minecon Capes
    public static CapeItem CAPE_MINECON_6;
    public static CapeItem CAPE_MINECON_7;
    public static CapeItem CAPE_MINECON_8;
    public static CapeItem CAPE_MINECON_9;
    public static CapeItem CAPE_MINECON_10;
    public static CapeItem CAPE_MINECON_11;
    public static CapeItem CAPE_MINECON_12;
    public static CapeItem CAPE_MINECON_13;
    public static CapeItem CAPE_MINECON_14;
    public static CapeItem CAPE_MINECON_15;
    public static CapeItem CAPE_MINECON_16;
    public static CapeItem CAPE_4J_STUDIO;
    
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<net.minecraft.world.item.Item> event) {
        
        LOGGER.info("Registering Craftable Capes items...");
        
        // ==================== Official Mojang Capes (Online textures) ====================
        CAPE_OLD_MOJANG = registerOnlineCape(event, "old_mojang", 
            "8f120319222a9f4a104e2f5cb97b2cda93199a2ee9e1585cb8d09d6f687cb761");
        CAPE_MOJANG = registerOnlineCape(event, "mojang", 
            "5786fe99be377dfb6858859f926c4dbc995751e91cee373468c5fbf4865e7151");
        CAPE_NEW_MOJANG = registerOnlineCape(event, "new_mojang", 
            "9e507afc56359978a3eb3e32367042b853cddd0995d17d0da995662913fb00f7");
        
        // ==================== Minecon Capes (Online textures) ====================
        CAPE_MINECON_2011 = registerOnlineCape(event, "minecon_2011", 
            "953cac8b779fe41383e675ee2b86071a71658f2180f56fbce8aa315ea70e2ed6");
        CAPE_MINECON_2012 = registerOnlineCape(event, "minecon_2012", 
            "a2e8d97ec79100e90a75d369d1b3ba81273c4f82bc1b737e934eed4a854be1b6");
        CAPE_MINECON_2013 = registerOnlineCape(event, "minecon_2013", 
            "153b1a0dfcbae953cdeb6f2c2bf6bf79943239b1372780da44bcbb29273131da");
        CAPE_MINECON_2015 = registerOnlineCape(event, "minecon_2015", 
            "b0cc08840700447322d953a02b965f1d65a13a603bf64b17c803c21446fe1635");
        CAPE_MINECON_2016 = registerOnlineCape(event, "minecon_2016", 
            "e7dfea16dc83c97df01a12fabbd1216359c0cd0ea42f9999b6e97c584963e980");
        
        // ==================== Translator Capes (Online textures) ====================
        CAPE_TRANSLATOR = registerOnlineCape(event, "translator", 
            "1bf91499701404e21bd46b0191d63239a4ef76ebde88d27e4d430ac211df681e");
        CAPE_TRANSLATOR_CN = registerOnlineCape(event, "translator_cn", 
            "2262fb1d24912209490586ecae98aca8500df3eff91f2a07da37ee524e7e3cb6");
        CAPE_TRANSLATOR_JP = registerOnlineCape(event, "translator_jp", 
            "ca29f5dd9e94fb1748203b92e36b66fda80750c87ebc18d6eafdb0e28cc1d05f");
        
        // ==================== Special Event Capes (Online textures) ====================
        CAPE_MILLIONTH_CUSTOMER = registerOnlineCape(event, "millionth", 
            "70efffaf86fe5bc089608d3cb297d3e276b9eb7a8f9f2fe6659c23a2d8b18edf");
        CAPE_DB = registerOnlineCape(event, "db", 
            "bcfbe84c6542a4a5c213c1cacf8979b5e913dcb4ad783a8b80e3c4a7d5c8bdac");
        CAPE_SNOWMAN = registerOnlineCape(event, "snowman", 
            "23ec737f18bfe4b547c95935fc297dd767bb84ee55bfd855144d279ac9bfd9fe");
        CAPE_SPADE = registerOnlineCape(event, "spade", 
            "2e002d5e1758e79ba51d08d92a0f3a95119f2f435ae7704916507b6c565a7da8");
        CAPE_PRISMARINE = registerOnlineCape(event, "prismarine", 
            "d8f8d13a1adf9636a16c31d47f3ecc9bb8d8533108aa5ad2a01b13b1a0c55eac");
        CAPE_TURTLE = registerOnlineCape(event, "turtle", 
            "5048ea61566353397247d2b7d946034de926b997d5e66c86483dfb1e031aee95");
        CAPE_BIRTHDAY = registerOnlineCape(event, "birthday", 
            "2056f2eebd759cce93460907186ef44e9192954ae12b227d817eb4b55627a7fc");
        CAPE_SCROLLS = registerOnlineCape(event, "scrolls", 
            "3efadf6510961830f9fcc077f19b4daf286d502b5f5aafbd807c7bbffcaca245");
        CAPE_COBALT = registerOnlineCape(event, "cobalt", 
            "ca35c56efe71ed290385f4ab5346a1826b546a54d519e6a3ff01efa01acce81");
        CAPE_MOJIRA = registerOnlineCape(event, "mojira", 
            "ae677f7d98ac70a533713518416df4452fe5700365c09cf45d0d156ea9396551");
        CAPE_REALMS = registerOnlineCape(event, "realms", 
            "17912790ff164b93196f08ba71d0e62129304776d0f347334f8a6eae509f8a56");
        CAPE_MIGRATOR = registerOnlineCape(event, "migrator", 
            "2340c0e03dd24a11b15a8b33c2a7e9e32abb2051b2481d0ba7defd635ca7a933");
        CAPE_VANILLA = registerOnlineCape(event, "vanilla", 
            "f9a76537647989f9a0b6d001e320dac591c359e9e61a31f4ce11c88f207f0ad4");
        CAPE_CHERRY_BLOSSOM = registerOnlineCape(event, "cherry_blossom", 
            "afd553b39358a24edfe3b8a9a939fa5fa4faa4d9a9c3d6af8eafb377fa05c2bb");
        CAPE_15_YEARS = registerOnlineCape(event, "15_years", 
            "cd9d82ab17fd92022dbd4a86cde4c382a7540e117fae7b9a2853658505a80625");
        CAPE_MOJANG_OFFICE = registerOnlineCape(event, "mojang_office", 
            "5c29410057e32abec02d870ecb52ec25fb45ea81e785a7854ae8429d7236ca26");
        CAPE_YEARN = registerOnlineCape(event, "yearn", 
            "308b32a9e303155a0b4262f9e5483ad4a22e3412e84fe8385a0bdd73dc41fa89");
        CAPE_HOME = registerOnlineCape(event, "home", 
            "1de21419009db483900da6298a1e6cbf9f1bc1523a0dcdc16263fab150693edd");
        CAPE_MENACE = registerOnlineCape(event, "menace", 
            "dbc21e222528e30dc88445314f7be6ff12d3aeebc3c192054fba7e3b3f8c77b1");
        CAPE_COMMON = registerOnlineCape(event, "common", 
            "5ec930cdd2629c8771655c60eebeb867b4b6559b0e6d3bc71c40c96347fa03f0");
        CAPE_DOCUMENT = registerOnlineCape(event, "document", 
            "fdcf48f01ec480d1d7cbec27f7ddce48c9da2be6724641109444dae58d4cd013");
        CAPE_ZOMBIE_HORSE = registerOnlineCape(event, "zombie_horse", 
            "a3f6e4f14801f3ea55e3d95b9b4ef3b5e8802d947f669de93d6ec4b9354a436b");
        CAPE_MOONLIGHT_TRAIL = registerOnlineCape(event, "moonlight_trail", 
            "fe8a02dfe9e390e44ff33d69feef9d3943f76d3901015bbd50f0b67722d288bd");
        CAPE_CRAFTER = registerOnlineCape(event, "crafter", 
            "479eacefa3cdd7aca94207f36c0dd449653ddf259daf40544a5866baf05eee22");
        CAPE_BUILDER = registerOnlineCape(event, "builder", 
            "2c579968c64c1719740fd8c2a451461879b238002574fce48f7d1a7c36a1c7d4");
        CAPE_OXEYE = registerOnlineCape(event, "oxeye", 
            "7706b5f5fc90329691e59277dcc66ba20572219fa8e5da472afd5235fad12cc8");
        CAPE_COPPER = registerOnlineCape(event, "copper", 
            "5e6f3193e74cd16cdd6637d9bae5484e3a37ff2a14c2d157c659a07810b1bdca");
        
        // ==================== Local Texture Capes (Need PNG files!) ====================
        CAPE_TWITCH = registerLocalCape(event, "twitch");
        CAPE_TIKTOK = registerLocalCape(event, "tiktok");
        CAPE_10_YEARS = registerLocalCape(event, "10_years");
        CAPE_AWESOM = registerLocalCape(event, "awesom");
        CAPE_BACON = registerLocalCape(event, "bacon");
        CAPE_BLONK = registerLocalCape(event, "blonk");
        CAPE_FROG = registerLocalCape(event, "frog");
        CAPE_MCC15 = registerLocalCape(event, "mcc15");
        CAPE_MINECON_3 = registerLocalCape(event, "minecon_3");
        CAPE_MINECON_4 = registerLocalCape(event, "minecon_4");
        CAPE_MINECON_5 = registerLocalCape(event, "minecon_5");
        CAPE_NO_CIRCLE = registerLocalCape(event, "no_circle");
        CAPE_NYAN = registerLocalCape(event, "nyan");
        CAPE_NYE_2011 = registerLocalCape(event, "nye_2011");
        CAPE_PANCAPE = registerLocalCape(event, "pancape");
        CAPE_PRIDE = registerLocalCape(event, "pride");
        CAPE_SNAIL = registerLocalCape(event, "snail");
        CAPE_SQUID = registerLocalCape(event, "squid");
        CAPE_VETERINARIAN = registerLocalCape(event, "veterinarian");
        CAPE_VILLAGER_RESCUE = registerLocalCape(event, "villager_rescue");
        CAPE_XBOX = registerLocalCape(event, "xbox");
        CAPE_XBOX_BDAY = registerLocalCape(event, "xbox_bday");
        CAPE_XMAS = registerLocalCape(event, "xmas");
        CAPE_SNIFFER = registerLocalCape(event, "sniffer");
        CAPE_VALENTINE = registerLocalCape(event, "valentine");
        
        // Minecraft Dungeons Capes
        CAPE_AMETHYST = registerLocalCape(event, "mcd_amethyst");
        CAPE_BLUE = registerLocalCape(event, "mcd_blue");
        CAPE_CLOUDY_CLIMB = registerLocalCape(event, "mcd_cloudy_climb");
        CAPE_COW_CRUSADER = registerLocalCape(event, "mcd_cow_crusader");
        CAPE_DOWNPOUR_CAPE = registerLocalCape(event, "mcd_downpour");
        CAPE_FAUNA_FAIRE = registerLocalCape(event, "mcd_fauna_faire");
        CAPE_GLOW = registerLocalCape(event, "mcd_glow");
        CAPE_HAMMER = registerLocalCape(event, "mcd_hammer");
        CAPE_HERO = registerLocalCape(event, "mcd_hero");
        CAPE_ICEOLOGER = registerLocalCape(event, "mcd_iceologer");
        CAPE_LUMINOUS_NIGHT = registerLocalCape(event, "mcd_luminous_night");
        CAPE_MYSTERY = registerLocalCape(event, "mcd_mystery");
        CAPE_PHANTOM = registerLocalCape(event, "mcd_phantom");
        CAPE_PRISM_CAPE = registerLocalCape(event, "mcd_prism");
        CAPE_RED_ROYAL = registerLocalCape(event, "mcd_red_royal");
        CAPE_SOUL = registerLocalCape(event, "mcd_soul");
        CAPE_TURTLE_SHELL = registerLocalCape(event, "mcd_turtle_shell");
        CAPE_YEAR_1 = registerLocalCape(event, "mcd_year_1");
        CAPE_YEAR_2 = registerLocalCape(event, "mcd_year_2");
        
        // Custom Minecon Capes
        CAPE_MINECON_6 = registerLocalCape(event, "minecon_6");
        CAPE_MINECON_7 = registerLocalCape(event, "minecon_7");
        CAPE_MINECON_8 = registerLocalCape(event, "minecon_8");
        CAPE_MINECON_9 = registerLocalCape(event, "minecon_9");
        CAPE_MINECON_10 = registerLocalCape(event, "minecon_10");
        CAPE_MINECON_11 = registerLocalCape(event, "minecon_11");
        CAPE_MINECON_12 = registerLocalCape(event, "minecon_12");
        CAPE_MINECON_13 = registerLocalCape(event, "minecon_13");
        CAPE_MINECON_14 = registerLocalCape(event, "minecon_14");
        CAPE_MINECON_15 = registerLocalCape(event, "minecon_15");
        CAPE_MINECON_16 = registerLocalCape(event, "minecon_16");
        CAPE_4J_STUDIO = registerLocalCape(event, "4j_studio");
        
        LOGGER.info("Registered {} cape items total!", CraftableCapes.ALL_CAPES_LIST.size());
    }
    
    /**
     * Helper method to register an online cape (downloads texture from Minecraft servers)
     */
    private static CapeItem registerOnlineCape(RegistryEvent.Register<net.minecraft.world.item.Item> event, String name, String hash) {
        OnlineCapeItem cape = CraftableCapes.registerOnlineCape(hash, name);
        registerItem(event, name + "_cape", cape);
        return cape;
    }
    
    /**
     * Helper method to register a local texture cape (requires PNG file)
     */
    private static CapeItem registerLocalCape(RegistryEvent.Register<net.minecraft.world.item.Item> event, String name) {
        CapeItem cape = CraftableCapes.registerCape(name);
        registerItem(event, name + "_cape", cape);
        return cape;
    }
    
    /**
     * Helper method to register an item with the registry
     */
    private static void registerItem(RegistryEvent.Register<net.minecraft.world.item.Item> event, String name, CapeItem item) {
        item.setRegistryName(new ResourceLocation(CraftableCapes.MOD_ID, name));
        event.getRegistry().register(item);
    }
}
