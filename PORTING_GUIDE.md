# Craftable Capes - Forge 1.18.2 Port

这是一个从 Fabric 1.20.1 移植到 Forge 1.18.2 的披风模组，使用 Curios API 替代 Trinkets。

## 📦 已完成的工作

### 1. 核心架构
- ✅ 主类 [CraftableCapes.java](file://G:/forge-1.18.2-40.3.0-mdk/src/main/java/com/craftablecapes/CraftableCapes.java) - Forge @Mod 入口
- ✅ 配置系统 - gradle.properties 和 build.gradle 已更新
- ✅ Curios API 依赖集成

### 2. 物品系统
- ✅ [CapeItem.java](file://G:/forge-1.18.2-40.3.0-mdk/src/main/java/com/craftablecapes/items/CapeItem.java) - 本地纹理披风基类
- ✅ [OnlineCapeItem.java](file://G:/forge-1.18.2-40.3.0-mdk/src/main/java/com/craftablecapes/items/OnlineCapeItem.java) - 在线纹理披风类
- ✅ [CapeRegistry.java](file://G:/forge-1.18.2-40.3.0-mdk/src/main/java/com/craftablecapes/registry/CapeRegistry.java) - 物品注册系统（示例披风）

### 3. 客户端系统
- ✅ [FileCache.java](file://G:/forge-1.18.2-40.3.0-mdk/src/main/java/com/craftablecapes/client/FileCache.java) - 纹理缓存管理
- ✅ [ClientSetup.java](file://G:/forge-1.18.2-40.3.0-mdk/src/main/java/com/craftablecapes/client/ClientSetup.java) - 客户端初始化

### 4. 配置文件
- ✅ [mods.toml](file://G:/forge-1.18.2-40.3.0-mdk/src/main/resources/META-INF/mods.toml) - 模组元数据和Curios依赖
- ✅ [build.gradle](file://G:/forge-1.18.2-40.3.0-mdk/build.gradle) - Curios Maven仓库和依赖

## 🔧 待完成的工作

### 高优先级（核心功能）

#### 1. Mixin 实现 ⭐⭐⭐
需要创建Mixin来替换玩家的披风纹理：

**需要创建的文件：**
```
src/main/java/com/craftablecapes/mixin/
├── AbstractClientPlayerMixin.java    # 注入 getCapeTexture() 方法
└── CapeLayerMixin.java                # 修改披风渲染层（可选）
```

**功能说明：**
- 检测玩家是否装备了Curios披风槽位的披风
- 如果是，返回披风的纹理而不是默认披风
- 需要使用 `@Inject` 或 `@Redirect` 修改原版方法

**参考原Fabric代码：**
- [PlayerEntityMixin.java](file://G:/CraftableCapes-1.20.1/CraftableCapes-1.20.1/src/main/java/xyz/ryhon/craftablecapes/mixin/PlayerEntityMixin.java)
- [CapeFeatureRendererMixin.java](file://G:/CraftableCapes-1.20.1/CraftableCapes-1.20.1/src/main/java/xyz/ryhon/craftablecapes/mixin/CapeFeatureRendererMixin.java)

#### 2. Curios API 集成 ⭐⭐⭐
需要实现与Curios的交互：

**需要创建的文件：**
```
src/main/java/com/craftablecapes/integration/
└── CuriosIntegration.java             # Curios API 辅助方法
```

**功能说明：**
- 检查玩家是否在cape槽位装备了披风
- 获取当前装备的披风物品
- 监听装备/卸下事件

**Curios API 关键方法：**
```java
// 获取玩家的Curios组件
ICurios curio = CuriosApi.getCuriosHelper().getCuriosCapability(player);

// 检查特定槽位
Optional<ItemStack> stack = curio.getStacksHandler("cape");

// 获取槽位中的物品
ItemStack capeStack = stack.orElse(ItemStack.EMPTY);
```

#### 3. 添加所有披风注册 ⭐⭐
在 [CapeRegistry.java](file://G:/forge-1.18.2-40.3.0-mdk/src/main/java/com/craftablecapes/registry/CapeRegistry.java) 中添加剩余的披风：

原Fabric版本有约90个披风，目前只注册了12个示例。需要从原项目复制所有披风定义：
- 查看 [CraftableCapes.java](file://G:/CraftableCapes-1.20.1/CraftableCapes-1.20.1/src/main/java/xyz/ryhon/craftablecapes/CraftableCapes.java#L34-L170) 第34-170行

### 中优先级（资源和配置）

#### 4. 资源文件 ⭐⭐
需要创建：

**a) 语言文件**
```
src/main/resources/assets/craftablecapes/lang/
├── en_us.json    # 英文翻译
└── zh_cn.json    # 中文翻译
```

**b) 披风纹理**（对于本地纹理披风）
```
src/main/resources/assets/craftablecapes/textures/capes/
├── tiktok.png
├── 10_years.png
└── ... (其他自定义披风)
```

**c) 模型文件**
```
src/main/resources/assets/craftablecapes/models/item/
├── old_mojang_cape.json
├── mojang_cape.json
└── ... (每个披风都需要)
```

#### 5. Curios 槽位配置 ⭐⭐
创建Curios配置文件来定义cape槽位：

```
src/main/resources/data/curios/
└── slots/
    └── cape.json                     # 定义cape槽位属性
```

示例配置：
```json
{
  "size": 1,
  "order": 1,
  "icon": "curios:slot/empty_cape",
  "add_cosmetic": true
}
```

#### 6. 合成配方 ⭐
为披风添加合成配方（可选）：

```
src/main/resources/data/craftablecapes/recipes/
├── old_mojang_cape.json
├── mojang_cape.json
└── ... 
```

### 低优先级（优化和扩展）

#### 7. 数据生成器（可选）⭐
如果需要自动生成配方和标签，可以创建数据生成器

#### 8. 配置选项 ⭐
添加配置文件允许用户：
- 启用/禁用特定披风
- 调整披风渲染选项
- 自定义合成配方

## 📝 开发步骤建议

### 第一步：实现Mixin（最关键）

1. 在 `build.gradle` 中确保已配置Mixin：
```gradle
minecraft {
    // ... existing config
}

// 添加Mixin配置
mixin {
    add sourceSets.main, "craftablecapes.refmap.json"
    config "craftablecapes.mixins.json"
}
```

2. 创建 `AbstractClientPlayerMixin.java`：
```java
@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerMixin {
    @Inject(at = @At("RETURN"), method = "getCapeTexture", cancellable = true)
    private void getCapeTexture(CallbackInfoReturnable<ResourceLocation> info) {
        // 检查Curios cape槽位
        // 如果有披风，设置info.setReturnValue(capeTexture)
    }
}
```

3. 创建Mixin配置文件 `src/main/resources/craftablecapes.mixins.json`

### 第二步：Curios集成

1. 创建帮助类来处理Curios API调用
2. 在Mixin中使用这个帮助类
3. 测试披风是否能正确显示

### 第三步：完善资源

1. 添加所有披风的语言翻译
2. 添加本地纹理披风的PNG文件
3. 创建物品模型JSON文件

### 第四步：测试

1. 运行游戏
2. 使用 `/give` 命令获取披风
3. 在Curios界面装备披风
4. 确认披风正确显示

## 🔗 有用的资源

- **Curios API 文档**: https://github.com/TheIllusiveC4/Curios
- **Forge Mixin 教程**: https://docs.minecraftforge.net/en/latest/advanced/accesstransformers/
- **原Fabric项目**: G:\CraftableCapes-1.20.1
- **Curios Maven**: https://maven.theillusivec4.net/

## 🐛 常见问题

### Q: 披风不显示？
A: 检查：
1. Mixin是否正确注入
2. Curios是否正确安装
3. 披风是否在正确的槽位
4. 纹理路径是否正确

### Q: 编译错误？
A: 运行 `gradlew clean` 然后重新构建

### Q: 如何添加更多披风？
A: 在 `CapeRegistry.java` 中添加新的注册调用

## 📄 许可证

本项目遵循 GPL-3.0 许可证（与原Fabric版本相同）

---

**下一步建议**：先实现Mixin系统，这是让披风显示的核心！
