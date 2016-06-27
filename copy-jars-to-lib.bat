@echo off
echo ------------------------------------
echo 下载最新的依赖（jar包），并复制到src/main/webapp/WEB-INF/lib中
echo current path is : %~dp0
echo ------------------------------------
set MVN=mvn
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m

pushd %~dp0
:: 删除 所有.jar 文件
call del /f /s /q *.jar

call %MVN%  dependency:copy-dependencies -DoutputDirectory=src/main/webapp/WEB-INF/lib 
if errorlevel 1 goto error

echo ------------------------------------
echo 已下载jar包到src/main/webapp/WEB-INF/lib中!
echo ------------------------------------

goto end


:error
echo Error Happen!!!
:end
pause