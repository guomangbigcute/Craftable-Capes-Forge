@echo off
REM ============================================
REM Craftable Capes - GitHub 推送脚本
REM 使用方法：双击运行，或在终端执行
REM ============================================

cd /d "%~dp0"

REM 添加远程仓库（如果未添加）
git remote remove origin 2>nul
git remote add origin https://github.com/guomangbigcute/Craftable-Capes-Forge.git

REM 确保当前在 1.21-neoforge 分支
git checkout 1.21-neoforge

REM 推送到 GitHub，将 1.21-neoforge 设为主分支
echo 正在推送代码到 GitHub...
git push -u origin 1.21-neoforge:master --force

REM 也推送其他分支
git checkout 1.20-forge
git push -u origin 1.20-forge --force

git checkout master
git push -u origin master --force

REM 回到 1.21-neoforge 分支
git checkout 1.21-neoforge

echo.
echo 推送完成！
echo 仓库地址：https://github.com/guomangbigcute/Craftable-Capes-Forge
pause
