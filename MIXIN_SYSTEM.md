# Mixin 系统实现说明

## 📋 概述

已成功为 Craftable Capes 模组实现了完整的 Mixin 系统，用于：
1. **拦截玩家披风纹理获取** - 替换为Curios槽位中装备的自定义披风
2. **修改披风渲染方式** - 支持透明纹理的正确渲染

---

## 🏗️ 架构设计

### 文件结构

```
src/main/java/com/craftablecapes/
├── mixin/
│   ├── AbstractClientPlayerMixin.java    # 核心：注入披风纹理获取
│   └── CapeLayerMixin.java                # 渲染优化：支持透明纹理
├── integration/
│   └── CuriosIntegration.java             # Curios API 交互层
└── ...

src/main/resources/
└── craftablecapes.mixins.json             # Mixin 配置文件
```

---

## 🔧 核心组件详解

### 1. AbstractClientPlayerMixin.java

**作用**：拦截 `AbstractClientPlayer.getCapeTexture()` 方法

**工作原理**：
```java
@Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
private void onGetCapeTexture(CallbackInfoReturnable<ResourceLocation> cir) {
    // 1. 通过 CuriosIntegration 获取装备的披风
    ResourceLocation capeTexture = CuriosIntegration.getEquippedCapeTexture(player);
    
    // 2. 如果有自定义披风，返回它并取消原版方法
    if (capeTexture != null) {
        cir.setReturnValue(capeTexture);
        cir.cancel();
    }
}
```

**关键点**：
- `@Inject` - 在方法开头注入代码
- `cancellable = true` - 允许取消原版方法的执行
- `cir.setReturnValue()` - 设置返回值
- `cir.cancel()` - 阻止原版方法继续执行

---

### 2. CuriosIntegration.java

**作用**：封装与 Curios API 的所有交互

**核心方法**：

```java
// 获取玩家装备的披风物品
public static Optional<CapeItem> getEquippedCape(Player player) {
    // 1. 获取玩家的 Curios 能力
    Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosHelper()
        .resolveCuriosCapability(player);
    
    // 2. 检查 "cape" 槽位
    Optional<ItemStack> capeStack = handler.getEquippedItem("cape");
    
    // 3. 返回 CapeItem（如果存在）
    if (stack.getItem() instanceof CapeItem) {
        return Optional.of((CapeItem) stack.getItem());
    }
}

// 获取披风纹理
public static ResourceLocation getEquippedCapeTexture(Player player) {
    Optional<CapeItem> cape = getEquippedCape(player);
    return cape.map(CapeItem::getTextureLocation).orElse(null);
}
```

**重要细节**：
- ✅ 只在客户端执行（`player.level.isClientSide()`）
- ✅ 异常处理 - 防止Curios未安装时崩溃
- ✅ 返回 `Optional` - 安全的空值处理

---

### 3. CapeLayerMixin.java

**作用**：修改披风渲染类型以支持透明度

**工作原理**：
```java
@Redirect(
    method = "render(...)",
    at = @At(value = "INVOKE", target = "RenderType.entitySolid(...)")
)
private static RenderType redirectGetEntitySolid(ResourceLocation location) {
    // 将 entitySolid 替换为 entityCutout
    return RenderType.entityCutout(location);
}
```

**为什么需要这个**：
- `entitySolid` - 不支持透明像素，会将透明部分渲染为黑色
- `entityCutout` - 正确处理透明度，披风边缘更平滑

---

### 4. craftablecapes.mixins.json

**作用**：Mixin系统的配置文件

**关键配置**：
```json
{
  "package": "com.craftablecapes.mixin",     // Mixin类的包名
  "refmap": "craftablecapes.refmap.json",    // 引用映射文件（自动生成）
  "client": [                                 // 仅客户端加载的Mixin
    "AbstractClientPlayerMixin",
    "CapeLayerMixin"
  ]
}
```

**注意**：
- `"client"` 数组中的Mixin只会在客户端加载
- `"mixins"` 数组用于两侧都需要的Mixin（本模组不需要）
- `refmap` 文件会在编译时自动生成

---

## ⚙️ Build.gradle 配置

### 添加的插件和依赖

```gradle
// 1. 应用Mixin插件
apply plugin: 'org.spongepowered.mixin'

// 2. 添加Mixin注解处理器
dependencies {
    annotationProcessor 'org.spongepowered:mixin:0.8.5:processor'
}

// 3. 在JAR清单中声明Mixin配置
tasks.named('jar', Jar).configure {
    manifest {
        attributes([
            'MixinConfigs': 'craftablecapes.mixins.json'
        ])
    }
}
```

---

## 🔄 工作流程

### 当玩家装备披风时：

```
1. 玩家在Curios界面的"cape"槽位装备披风物品
   ↓
2. 游戏尝试渲染玩家的披风
   ↓
3. Minecraft调用 AbstractClientPlayer.getCapeTexture()
   ↓
4. AbstractClientPlayerMixin 拦截该方法
   ↓
5. 调用 CuriosIntegration.getEquippedCapeTexture(player)
   ↓
6. CuriosIntegration 检查 Curios cape 槽位
   ↓
7a. 如果有披风 → 返回披风纹理 → Mixin设置返回值并取消原版方法
7b. 如果没有披风 → 返回null → 原版方法继续执行（使用默认披风）
   ↓
8. CapeLayerMixin 修改渲染类型为 entityCutout
   ↓
9. 披风以正确的透明度渲染
```

---

## 🧪 测试方法

### 1. 编译项目
```bash
gradlew clean build
```

### 2. 运行客户端
```bash
gradlew runClient
```

### 3. 在游戏中测试
```mcfunction
# 给自己一个披风
/give @p craftablecapes:mojang_cape

# 打开Curios界面（默认键位：G）
# 将披风放入 cape 槽位

# 按F3查看调试信息，或切换到第三人称视角
# 应该能看到披风显示
```

### 4. 验证Mixin是否工作

**方法1：检查日志**
启动时应该看到类似这样的日志：
```
[main/INFO] [mixin/]: Mixing craftablecapes.mixins.json:...
```

**方法2：使用Mixin调试**
在 `gradle.properties` 中添加：
```properties
org.gradle.jvmargs=-Xmx3G -Dmixin.debug.export=true
```

这会在 `.minecraft/run/mixin.output` 导出混合后的类。

---

## 🐛 常见问题排查

### Q1: Mixin没有生效？

**检查清单**：
1. ✅ `craftablecapes.mixins.json` 是否在 `src/main/resources/` 根目录
2. ✅ JAR清单中是否有 `MixinConfigs: craftablecapes.mixins.json`
3. ✅ Mixin类的包名是否与配置文件中的 `package` 匹配
4. ✅ 方法签名是否正确（参数类型、返回值）

**调试步骤**：
```bash
# 清理并重新构建
gradlew clean build

# 查看详细日志
gradlew runClient --info
```

---

### Q2: 编译错误 "Cannot resolve symbol 'CuriosApi'"？

**解决方案**：
1. 确保已运行 `gradlew clean` 
2. 刷新IDE的Gradle项目
3. 检查 `build.gradle` 中Curios依赖是否正确

---

### Q3: 游戏崩溃，提示Mixin注入失败？

**可能原因**：
1. 方法签名不匹配（Minecraft版本不同）
2. Mixin配置错误

**解决方法**：
查看崩溃日志中的具体错误信息，常见修复：
```java
// 如果方法描述符错误，可能需要调整 @Inject 的 method 参数
method = "getCapeTexture"  // 确保方法名正确
```

---

### Q4: 披风显示但纹理是黑色/粉色？

**原因**：纹理路径错误或纹理文件不存在

**检查**：
1. 本地披风：确保PNG文件在 `assets/craftablecapes/textures/capes/`
2. 在线披风：检查FileCache是否正确下载纹理
3. 使用资源重载（F3+T）测试

---

## 🔍 高级调试技巧

### 1. 启用Mixin详细日志

在 `mods.toml` 中添加：
```toml
[modproperties.craftablecapes]
mixin_debug=true
```

### 2. 检查Mixin是否被应用

启动时在日志中搜索：
```
[main/INFO] [mixin/]: Mixing ...
```

应该看到：
```
Mixing AbstractClientPlayerMixin from craftablecapes.mixins.json
Mixing CapeLayerMixin from craftablecapes.mixins.json
```

### 3. 运行时检查Curios集成

在Mixin中添加临时调试日志：
```java
LOGGER.info("Checking for equipped cape on player: {}", player.getName());
ResourceLocation texture = CuriosIntegration.getEquippedCapeTexture(player);
LOGGER.info("Found cape texture: {}", texture);
```

---

## 📚 相关资源

- **SpongePowered Mixin 文档**: https://github.com/SpongePowered/Mixin/wiki
- **Curios API 文档**: https://github.com/TheIllusiveC4/Curios
- **Forge Mixin 教程**: https://docs.minecraftforge.net/en/latest/advanced/mixins/

---

## ✅ 完成检查清单

- [x] Mixin插件已添加到build.gradle
- [x] Mixin注解处理器依赖已添加
- [x] JAR清单包含MixinConfigs属性
- [x] craftablecapes.mixins.json 配置文件已创建
- [x] AbstractClientPlayerMixin 已实现
- [x] CapeLayerMixin 已实现
- [x] CuriosIntegration 帮助类已创建
- [x] 所有Mixin类在配置文件中注册

---

## 🎯 下一步

Mixin系统已完成！现在可以：

1. **测试功能** - 运行游戏验证披风显示
2. **添加更多披风** - 在CapeRegistry中注册剩余披风
3. **完善资源** - 添加语言文件、模型、纹理
4. **创建合成配方** - 让玩家可以通过制作获得披风

祝开发顺利！🎉
