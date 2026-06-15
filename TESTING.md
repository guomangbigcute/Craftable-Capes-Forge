# Mixin 系统测试指南

## 🚀 快速测试步骤

### 1. 编译项目

打开终端，在项目根目录运行：

```powershell
.\gradlew clean build
```

**预期输出**：
```
BUILD SUCCESSFUL in XXs
```

如果出现错误，请检查：
- Java 17 是否正确安装
- Gradle 是否正确配置
- 所有依赖是否能下载

---

### 2. 运行开发客户端

```powershell
.\gradlew runClient
```

**首次运行可能需要5-10分钟**（需要下载Minecraft和Forge）

---

### 3. 在游戏中测试

#### 步骤A：获取披风

游戏启动后，按 `T` 打开聊天框，输入：

```mcfunction
/give @p craftablecapes:mojang_cape
```

你应该收到一个名为 "Mojang Cape" 的物品。

其他可测试的披风：
- `craftablecapes:old_mojang_cape`
- `craftablecapes:minecon_2011_cape`
- `craftablecapes:translator_cape`
- `craftablecapes:tiktok_cape` (需要纹理文件)

---

#### 步骤B：装备披风

1. 按默认键位 **`G`** 打开Curios界面
   - 如果Curios使用不同键位，检查控制设置
   
2. 找到 **"Cape"** 槽位

3. 将披风从物品栏拖到 Cape 槽位

---

#### 步骤C：查看披风

**方法1：第三人称视角**
- 按 `F5` 切换到第三人称
- 应该能看到角色背后显示披风

**方法2：移动观察**
- 在第三人称下移动或飞行
- 披风应该跟随玩家动作飘动

**方法3：截图验证**
- 按 `F2` 截图
- 截图保存在 `.minecraft/screenshots/`

---

## ✅ 验证清单

### 编译阶段
- [ ] `gradlew clean build` 成功完成
- [ ] 没有编译错误
- [ ] 生成了 `build/libs/craftablecapes-1.0.0.jar`

### 启动阶段
- [ ] 游戏成功启动
- [ ] 主菜单显示模组列表中有 "Craftable Capes"
- [ ] Curios模组也已加载

### 功能测试
- [ ] `/give` 命令能成功获取披风
- [ ] Curios界面有 "Cape" 槽位
- [ ] 披风可以放入槽位
- [ ] 第三人称能看到披风
- [ ] 披风纹理正确显示（不是黑色/粉色方块）
- [ ] 披风随玩家移动而飘动

---

## 🔍 调试技巧

### 检查Mixin是否加载

在游戏启动日志中搜索（`.minecraft/run/logs/latest.log`）：

```
Mixing AbstractClientPlayerMixin from craftablecapes.mixins.json
Mixing CapeLayerMixin from craftablecapes.mixins.json
```

如果找不到这些行，说明Mixin没有正确加载。

---

### 常见问题诊断

#### 问题1：披风物品存在但无法装备

**可能原因**：
- Curios未安装或版本不匹配
- Cape槽位未配置

**解决方法**：
```mcfunction
# 检查Curios是否加载
/modlist curios

# 如果没有，确认curios jar在mods文件夹中
```

---

#### 问题2：披风装备了但不显示

**诊断步骤**：

1. **检查Mixin是否工作**
   
   在日志中查找是否有Mixin相关错误

2. **检查纹理路径**
   
   对于在线披风（如mojang_cape），应该看到类似：
   ```
   [FileCache] Downloading texture...
   ```

3. **手动测试Mixin**
   
   在聊天框输入：
   ```mcfunction
   /data get entity @p Cape
   ```
   
   如果返回空或默认值，说明Mixin可能没生效

---

#### 问题3：纹理是黑色或粉色方块

**原因**：纹理文件缺失或路径错误

**解决方案**：

对于**本地纹理披风**（如tiktok_cape）：
1. 确保PNG文件存在于：
   ```
   src/main/resources/assets/craftablecapes/textures/capes/tiktok.png
   ```
2. 文件名必须完全匹配（包括大小写）
3. PNG格式必须是有效的

对于**在线纹理披风**（如mojang_cape）：
1. 检查网络连接
2. 查看FileCache是否正确下载
3. 等待几秒让纹理异步加载

---

### 启用详细日志

修改 `gradle.properties`：

```properties
# 添加Mixin调试参数
org.gradle.jvmargs=-Xmx3G -Dmixin.debug.verbose=true -Dmixin.debug.export=true
```

这会：
- 输出详细的Mixin注入信息
- 导出混合后的类到 `.minecraft/run/mixin.output/`

---

## 🧪 高级测试

### 测试多个披风

```mcfunction
# 获取所有类型的披风
/give @p craftablecapes:mojang_cape
/give @p craftablecapes:minecon_2011_cape
/give @p craftablecapes:migrator_cape

# 切换不同的披风观察效果
```

### 测试在线纹理下载

```mcfunction
# 获取需要下载的披风
/give @p craftablecapes:vanilla_cape

# 第一次装备时可能会有短暂延迟（下载纹理）
# 后续应该立即显示（缓存）
```

### 性能测试

```mcfunction
# 创造模式下快速飞行
/gamemode creative
# 观察FPS是否稳定
```

---

## 📊 预期结果

### 成功的标志

✅ **编译成功**
```
BUILD SUCCESSFUL
```

✅ **启动日志正常**
```
[main/INFO] [CraftableCapes/]: Craftable Capes initialized!
[main/INFO] [CraftableCapes/]: Ported from Fabric (Trinkets) to Forge (Curios API)
```

✅ **游戏中功能正常**
- 披风物品有正确的名称和图标
- Curios界面有Cape槽位
- 第三人称能看到披风
- 披风纹理清晰，透明部分正确
- 移动时披风自然飘动

---

## ❌ 失败处理

### 如果测试失败

1. **收集信息**
   - 保存完整的错误日志
   - 记录具体哪一步失败
   - 截图错误画面

2. **检查环境**
   ```powershell
   # 确认Java版本
   java -version
   
   # 应该是 Java 17
   ```

3. **清理重建**
   ```powershell
   .\gradlew clean
   .\gradlew build
   ```

4. **查阅文档**
   - [MIXIN_SYSTEM.md](file://G:/forge-1.18.2-40.3.0-mdk/MIXIN_SYSTEM.md) - Mixin详细说明
   - [PORTING_GUIDE.md](file://G:/forge-1.18.2-40.3.0-mdk/PORTING_GUIDE.md) - 移植指南

---

## 🎉 测试通过后的下一步

如果所有测试都通过了，恭喜！你可以：

1. **添加剩余披风** - 注册原Fabric版本的所有90+披风
2. **完善资源** - 添加语言文件、模型、合成配方
3. **优化代码** - 添加配置选项、性能优化
4. **打包发布** - 构建最终JAR文件

祝测试顺利！🚀
