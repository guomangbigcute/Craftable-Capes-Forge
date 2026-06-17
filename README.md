# Craftable Capes (NeoForge)

A Minecraft mod that lets you craft and wear any cape — including official Mojang capes, Minecon capes, and more!

Originally created as a **Fabric** mod by **[Ryhon0](https://github.com/Ryhon0/CraftableCapes)**.  
Ported to **NeoForge 1.21.11** by **MayaGuomang**, using the Curios API for cape slot integration.

## Features

- **90+ capes** to craft and wear, including:
  - Official Mojang capes (Minecon 2011-2016, Translator, Migrator, etc.)
  - Custom community capes (Pride, Twitch, TikTok, Nyan, etc.)
  - Minecraft Dungeons capes
- **Online capes** load textures from Mojang's texture servers (with local caching)
- **Offline capes** use bundled static textures
- Cape textures render correctly on the player model via `CapeLayer`
- Random cape displayed as the creative tab icon
- Non-stackable items (max 1 per slot)

## Requirements

- **Minecraft**: 1.21.11
- **NeoForge**: 21.11.42+
- **Curios API**: 14.0.0+ for 1.21.11

## Building

```bash
./gradlew build
```

The mod jar will be in `build/libs/`.

## License

This project is licensed under the **GNU General Public License v3.0 (GPL-3.0)**,  
matching the original Fabric project. See [LICENSE](LICENSE) for details.

## Credits

- **Original Author**: [Ryhon0](https://github.com/Ryhon0) — Fabric version
- **NeoForge Port**: [MayaGuomang](https://github.com/guomangbigcute)
