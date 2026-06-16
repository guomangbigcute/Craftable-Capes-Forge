package com.craftablecapes.registry;

import com.craftablecapes.CraftableCapes;
import com.craftablecapes.items.CapeItem;
import com.craftablecapes.items.OnlineCapeItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * Registry handler for all cape items using DeferredRegister (1.20.1+)
 * Registers both local texture capes and online texture capes
 * Total: 90+ capes ported from Fabric version
 */
public class CapeRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(CapeRegistry.class);
    
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(Registries.ITEM, CraftableCapes.MOD_ID);
    
    // ==================== Official Mojang Capes ====================
    public static final DeferredHolder<Item, ? extends Item> CAPE_OLD_MOJANG;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MOJANG;
    public static final DeferredHolder<Item, ? extends Item> CAPE_NEW_MOJANG;
    
    // ==================== Minecon Capes ====================
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_2011;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_2012;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_2013;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_2015;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_2016;
    
    // ==================== Translator Capes ====================
    public static final DeferredHolder<Item, ? extends Item> CAPE_TRANSLATOR;
    public static final DeferredHolder<Item, ? extends Item> CAPE_TRANSLATOR_CN;
    public static final DeferredHolder<Item, ? extends Item> CAPE_TRANSLATOR_JP;
    
    // ==================== Special Event Capes ====================
    public static final DeferredHolder<Item, ? extends Item> CAPE_MILLIONTH_CUSTOMER;
    public static final DeferredHolder<Item, ? extends Item> CAPE_DB;
    public static final DeferredHolder<Item, ? extends Item> CAPE_SNOWMAN;
    public static final DeferredHolder<Item, ? extends Item> CAPE_SPADE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_PRISMARINE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_TURTLE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_BIRTHDAY;
    public static final DeferredHolder<Item, ? extends Item> CAPE_SCROLLS;
    public static final DeferredHolder<Item, ? extends Item> CAPE_COBALT;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MOJIRA;
    public static final DeferredHolder<Item, ? extends Item> CAPE_REALMS;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MIGRATOR;
    public static final DeferredHolder<Item, ? extends Item> CAPE_VANILLA;
    public static final DeferredHolder<Item, ? extends Item> CAPE_CHERRY_BLOSSOM;
    public static final DeferredHolder<Item, ? extends Item> CAPE_15_YEARS;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MOJANG_OFFICE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_YEARN;
    public static final DeferredHolder<Item, ? extends Item> CAPE_HOME;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MENACE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_COMMON;
    public static final DeferredHolder<Item, ? extends Item> CAPE_DOCUMENT;
    public static final DeferredHolder<Item, ? extends Item> CAPE_ZOMBIE_HORSE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MOONLIGHT_TRAIL;
    public static final DeferredHolder<Item, ? extends Item> CAPE_CRAFTER;
    public static final DeferredHolder<Item, ? extends Item> CAPE_BUILDER;
    public static final DeferredHolder<Item, ? extends Item> CAPE_OXEYE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_COPPER;
    
    // ==================== Local Texture Capes ====================
    public static final DeferredHolder<Item, ? extends Item> CAPE_TWITCH;
    public static final DeferredHolder<Item, ? extends Item> CAPE_TIKTOK;
    public static final DeferredHolder<Item, ? extends Item> CAPE_10_YEARS;
    public static final DeferredHolder<Item, ? extends Item> CAPE_AWESOM;
    public static final DeferredHolder<Item, ? extends Item> CAPE_BACON;
    public static final DeferredHolder<Item, ? extends Item> CAPE_BLONK;
    public static final DeferredHolder<Item, ? extends Item> CAPE_FROG;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MCC15;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_3;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_4;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_5;
    public static final DeferredHolder<Item, ? extends Item> CAPE_NO_CIRCLE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_NYAN;
    public static final DeferredHolder<Item, ? extends Item> CAPE_NYE_2011;
    public static final DeferredHolder<Item, ? extends Item> CAPE_PANCAPE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_PRIDE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_SNAIL;
    public static final DeferredHolder<Item, ? extends Item> CAPE_SQUID;
    public static final DeferredHolder<Item, ? extends Item> CAPE_VETERINARIAN;
    public static final DeferredHolder<Item, ? extends Item> CAPE_VILLAGER_RESCUE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_XBOX;
    public static final DeferredHolder<Item, ? extends Item> CAPE_XBOX_BDAY;
    public static final DeferredHolder<Item, ? extends Item> CAPE_XMAS;
    public static final DeferredHolder<Item, ? extends Item> CAPE_SNIFFER;
    public static final DeferredHolder<Item, ? extends Item> CAPE_VALENTINE;
    
    // Minecraft Dungeons Capes
    public static final DeferredHolder<Item, ? extends Item> CAPE_AMETHYST;
    public static final DeferredHolder<Item, ? extends Item> CAPE_BLUE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_CLOUDY_CLIMB;
    public static final DeferredHolder<Item, ? extends Item> CAPE_COW_CRUSADER;
    public static final DeferredHolder<Item, ? extends Item> CAPE_DOWNPOUR_CAPE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_FAUNA_FAIRE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_GLOW;
    public static final DeferredHolder<Item, ? extends Item> CAPE_HAMMER;
    public static final DeferredHolder<Item, ? extends Item> CAPE_HERO;
    public static final DeferredHolder<Item, ? extends Item> CAPE_ICEOLOGER;
    public static final DeferredHolder<Item, ? extends Item> CAPE_LUMINOUS_NIGHT;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MYSTERY;
    public static final DeferredHolder<Item, ? extends Item> CAPE_PHANTOM;
    public static final DeferredHolder<Item, ? extends Item> CAPE_PRISM_CAPE;
    public static final DeferredHolder<Item, ? extends Item> CAPE_RED_ROYAL;
    public static final DeferredHolder<Item, ? extends Item> CAPE_SOUL;
    public static final DeferredHolder<Item, ? extends Item> CAPE_TURTLE_SHELL;
    public static final DeferredHolder<Item, ? extends Item> CAPE_YEAR_1;
    public static final DeferredHolder<Item, ? extends Item> CAPE_YEAR_2;
    
    // Custom Minecon Capes
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_6;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_7;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_8;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_9;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_10;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_11;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_12;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_13;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_14;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_15;
    public static final DeferredHolder<Item, ? extends Item> CAPE_MINECON_16;
    public static final DeferredHolder<Item, ? extends Item> CAPE_4J_STUDIO;
    
    // ==================== Registration ====================
    
    // Helper: register an online cape
    private static DeferredHolder<Item, ? extends Item> registerOnlineCape(String name, String hash) {
        return ITEMS.register(name + "_cape", () -> {
            OnlineCapeItem cape = CraftableCapes.registerOnlineCape(hash, name);
            return cape;
        });
    }
    
    // Helper: register a local cape
    private static DeferredHolder<Item, ? extends Item> registerLocalCape(String name) {
        return ITEMS.register(name + "_cape", () -> {
            CapeItem cape = CraftableCapes.registerCape(name);
            return cape;
        });
    }
    
    static {
        LOGGER.info("Registering Craftable Capes items...");
        
        // ==================== Official Mojang Capes ====================
        CAPE_OLD_MOJANG = registerOnlineCape("old_mojang", 
            "8f120319222a9f4a104e2f5cb97b2cda93199a2ee9e1585cb8d09d6f687cb761");
        CAPE_MOJANG = registerOnlineCape("mojang", 
            "5786fe99be377dfb6858859f926c4dbc995751e91cee373468c5fbf4865e7151");
        CAPE_NEW_MOJANG = registerOnlineCape("new_mojang", 
            "9e507afc56359978a3eb3e32367042b853cddd0995d17d0da995662913fb00f7");
        
        // ==================== Minecon Capes ====================
        CAPE_MINECON_2011 = registerOnlineCape("minecon_2011", 
            "953cac8b779fe41383e675ee2b86071a71658f2180f56fbce8aa315ea70e2ed6");
        CAPE_MINECON_2012 = registerOnlineCape("minecon_2012", 
            "a2e8d97ec79100e90a75d369d1b3ba81273c4f82bc1b737e934eed4a854be1b6");
        CAPE_MINECON_2013 = registerOnlineCape("minecon_2013", 
            "153b1a0dfcbae953cdeb6f2c2bf6bf79943239b1372780da44bcbb29273131da");
        CAPE_MINECON_2015 = registerOnlineCape("minecon_2015", 
            "b0cc08840700447322d953a02b965f1d65a13a603bf64b17c803c21446fe1635");
        CAPE_MINECON_2016 = registerOnlineCape("minecon_2016", 
            "e7dfea16dc83c97df01a12fabbd1216359c0cd0ea42f9999b6e97c584963e980");
        
        // ==================== Translator Capes ====================
        CAPE_TRANSLATOR = registerOnlineCape("translator", 
            "1bf91499701404e21bd46b0191d63239a4ef76ebde88d27e4d430ac211df681e");
        CAPE_TRANSLATOR_CN = registerOnlineCape("translator_cn", 
            "2262fb1d24912209490586ecae98aca8500df3eff91f2a07da37ee524e7e3cb6");
        CAPE_TRANSLATOR_JP = registerOnlineCape("translator_jp", 
            "ca29f5dd9e94fb1748203b92e36b66fda80750c87ebc18d6eafdb0e28cc1d05f");
        
        // ==================== Special Event Capes ====================
        CAPE_MILLIONTH_CUSTOMER = registerOnlineCape("millionth", 
            "70efffaf86fe5bc089608d3cb297d3e276b9eb7a8f9f2fe6659c23a2d8b18edf");
        CAPE_DB = registerOnlineCape("db", 
            "bcfbe84c6542a4a5c213c1cacf8979b5e913dcb4ad783a8b80e3c4a7d5c8bdac");
        CAPE_SNOWMAN = registerOnlineCape("snowman", 
            "23ec737f18bfe4b547c95935fc297dd767bb84ee55bfd855144d279ac9bfd9fe");
        CAPE_SPADE = registerOnlineCape("spade", 
            "2e002d5e1758e79ba51d08d92a0f3a95119f2f435ae7704916507b6c565a7da8");
        CAPE_PRISMARINE = registerOnlineCape("prismarine", 
            "d8f8d13a1adf9636a16c31d47f3ecc9bb8d8533108aa5ad2a01b13b1a0c55eac");
        CAPE_TURTLE = registerOnlineCape("turtle", 
            "5048ea61566353397247d2b7d946034de926b997d5e66c86483dfb1e031aee95");
        CAPE_BIRTHDAY = registerOnlineCape("birthday", 
            "2056f2eebd759cce93460907186ef44e9192954ae12b227d817eb4b55627a7fc");
        CAPE_SCROLLS = registerOnlineCape("scrolls", 
            "3efadf6510961830f9fcc077f19b4daf286d502b5f5aafbd807c7bbffcaca245");
        CAPE_COBALT = registerOnlineCape("cobalt", 
            "ca35c56efe71ed290385f4ab5346a1826b546a54d519e6a3ff01efa01acce81");
        CAPE_MOJIRA = registerOnlineCape("mojira", 
            "ae677f7d98ac70a533713518416df4452fe5700365c09cf45d0d156ea9396551");
        CAPE_REALMS = registerOnlineCape("realms", 
            "17912790ff164b93196f08ba71d0e62129304776d0f347334f8a6eae509f8a56");
        CAPE_MIGRATOR = registerOnlineCape("migrator", 
            "2340c0e03dd24a11b15a8b33c2a7e9e32abb2051b2481d0ba7defd635ca7a933");
        CAPE_VANILLA = registerOnlineCape("vanilla", 
            "f9a76537647989f9a0b6d001e320dac591c359e9e61a31f4ce11c88f207f0ad4");
        CAPE_CHERRY_BLOSSOM = registerOnlineCape("cherry_blossom", 
            "afd553b39358a24edfe3b8a9a939fa5fa4faa4d9a9c3d6af8eafb377fa05c2bb");
        CAPE_15_YEARS = registerOnlineCape("15_years", 
            "cd9d82ab17fd92022dbd4a86cde4c382a7540e117fae7b9a2853658505a80625");
        CAPE_MOJANG_OFFICE = registerOnlineCape("mojang_office", 
            "5c29410057e32abec02d870ecb52ec25fb45ea81e785a7854ae8429d7236ca26");
        CAPE_YEARN = registerOnlineCape("yearn", 
            "308b32a9e303155a0b4262f9e5483ad4a22e3412e84fe8385a0bdd73dc41fa89");
        CAPE_HOME = registerOnlineCape("home", 
            "1de21419009db483900da6298a1e6cbf9f1bc1523a0dcdc16263fab150693edd");
        CAPE_MENACE = registerOnlineCape("menace", 
            "dbc21e222528e30dc88445314f7be6ff12d3aeebc3c192054fba7e3b3f8c77b1");
        CAPE_COMMON = registerOnlineCape("common", 
            "5ec930cdd2629c8771655c60eebeb867b4b6559b0e6d3bc71c40c96347fa03f0");
        CAPE_DOCUMENT = registerOnlineCape("document", 
            "fdcf48f01ec480d1d7cbec27f7ddce48c9da2be6724641109444dae58d4cd013");
        CAPE_ZOMBIE_HORSE = registerOnlineCape("zombie_horse", 
            "a3f6e4f14801f3ea55e3d95b9b4ef3b5e8802d947f669de93d6ec4b9354a436b");
        CAPE_MOONLIGHT_TRAIL = registerOnlineCape("moonlight_trail", 
            "fe8a02dfe9e390e44ff33d69feef9d3943f76d3901015bbd50f0b67722d288bd");
        CAPE_CRAFTER = registerOnlineCape("crafter", 
            "479eacefa3cdd7aca94207f36c0dd449653ddf259daf40544a5866baf05eee22");
        CAPE_BUILDER = registerOnlineCape("builder", 
            "2c579968c64c1719740fd8c2a451461879b238002574fce48f7d1a7c36a1c7d4");
        CAPE_OXEYE = registerOnlineCape("oxeye", 
            "7706b5f5fc90329691e59277dcc66ba20572219fa8e5da472afd5235fad12cc8");
        CAPE_COPPER = registerOnlineCape("copper", 
            "5e6f3193e74cd16cdd6637d9bae5484e3a37ff2a14c2d157c659a07810b1bdca");
        
        // ==================== Local Texture Capes ====================
        CAPE_TWITCH = registerLocalCape("twitch");
        CAPE_TIKTOK = registerLocalCape("tiktok");
        CAPE_10_YEARS = registerLocalCape("10_years");
        CAPE_AWESOM = registerLocalCape("awesom");
        CAPE_BACON = registerLocalCape("bacon");
        CAPE_BLONK = registerLocalCape("blonk");
        CAPE_FROG = registerLocalCape("frog");
        CAPE_MCC15 = registerLocalCape("mcc15");
        CAPE_MINECON_3 = registerLocalCape("minecon_3");
        CAPE_MINECON_4 = registerLocalCape("minecon_4");
        CAPE_MINECON_5 = registerLocalCape("minecon_5");
        CAPE_NO_CIRCLE = registerLocalCape("no_circle");
        CAPE_NYAN = registerLocalCape("nyan");
        CAPE_NYE_2011 = registerLocalCape("nye_2011");
        CAPE_PANCAPE = registerLocalCape("pancape");
        CAPE_PRIDE = registerLocalCape("pride");
        CAPE_SNAIL = registerLocalCape("snail");
        CAPE_SQUID = registerLocalCape("squid");
        CAPE_VETERINARIAN = registerLocalCape("veterinarian");
        CAPE_VILLAGER_RESCUE = registerLocalCape("villager_rescue");
        CAPE_XBOX = registerLocalCape("xbox");
        CAPE_XBOX_BDAY = registerLocalCape("xbox_bday");
        CAPE_XMAS = registerLocalCape("xmas");
        CAPE_SNIFFER = registerLocalCape("sniffer");
        CAPE_VALENTINE = registerLocalCape("valentine");
        
        // Minecraft Dungeons Capes
        CAPE_AMETHYST = registerLocalCape("mcd_amethyst");
        CAPE_BLUE = registerLocalCape("mcd_blue");
        CAPE_CLOUDY_CLIMB = registerLocalCape("mcd_cloudy_climb");
        CAPE_COW_CRUSADER = registerLocalCape("mcd_cow_crusader");
        CAPE_DOWNPOUR_CAPE = registerLocalCape("mcd_downpour");
        CAPE_FAUNA_FAIRE = registerLocalCape("mcd_fauna_faire");
        CAPE_GLOW = registerLocalCape("mcd_glow");
        CAPE_HAMMER = registerLocalCape("mcd_hammer");
        CAPE_HERO = registerLocalCape("mcd_hero");
        CAPE_ICEOLOGER = registerLocalCape("mcd_iceologer");
        CAPE_LUMINOUS_NIGHT = registerLocalCape("mcd_luminous_night");
        CAPE_MYSTERY = registerLocalCape("mcd_mystery");
        CAPE_PHANTOM = registerLocalCape("mcd_phantom");
        CAPE_PRISM_CAPE = registerLocalCape("mcd_prism");
        CAPE_RED_ROYAL = registerLocalCape("mcd_red_royal");
        CAPE_SOUL = registerLocalCape("mcd_soul");
        CAPE_TURTLE_SHELL = registerLocalCape("mcd_turtle_shell");
        CAPE_YEAR_1 = registerLocalCape("mcd_year_1");
        CAPE_YEAR_2 = registerLocalCape("mcd_year_2");
        
        // Custom Minecon Capes
        CAPE_MINECON_6 = registerLocalCape("minecon_6");
        CAPE_MINECON_7 = registerLocalCape("minecon_7");
        CAPE_MINECON_8 = registerLocalCape("minecon_8");
        CAPE_MINECON_9 = registerLocalCape("minecon_9");
        CAPE_MINECON_10 = registerLocalCape("minecon_10");
        CAPE_MINECON_11 = registerLocalCape("minecon_11");
        CAPE_MINECON_12 = registerLocalCape("minecon_12");
        CAPE_MINECON_13 = registerLocalCape("minecon_13");
        CAPE_MINECON_14 = registerLocalCape("minecon_14");
        CAPE_MINECON_15 = registerLocalCape("minecon_15");
        CAPE_MINECON_16 = registerLocalCape("minecon_16");
        CAPE_4J_STUDIO = registerLocalCape("4j_studio");
        
        LOGGER.info("Registered {} cape items total!", CraftableCapes.ALL_CAPES_LIST.size());
    }
}
