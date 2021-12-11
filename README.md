### Simple Chat 还未完全完成，如果发现问题请提交 issue！

# Feature

 - 多线程心跳检测
 - 能够踢出在线用户(通过IP地址)
 - 当浏览器访问时会返回警告页面
 - 自动屏蔽错误的连接请求
 - 轻量化

# 用法
[下载最新版本](https://github.com/XIAYM-gh/Simple-Chat/releases/tag/v1.0.0)

需求: `JDK/JRE 11+`

启动方式:<br>

```shell
java -jar 文件名.jar
```

服务端目前支持的语言:

`中文(简体) , English`

客户端目前支持的语言:

`中文(简体) , English`

# 帮助

服务端可以使用 `help` 命令查看所有帮助.

客户端目前只有 `stop` 和 `online` 能够使用.

# 编译

编译时需要包含运行库


服务端主类: `tcp.server.main`

客户端主类: `tcp.client.main`

可以使用命令编译 (jdk 8/11 , Release 中使用 jdk/jre 11):


客户端:

```shell
# 目录 client/
javac -d . -cp jline3.jar:json.jar -encoding UTF-8 *.java
echo "Manifest-Version: 1.0">mf.txt
echo "Main-Class: tcp.client.main">>mf.txt
# 解压 jline3.jar 到当前目录生成 org 和 META-INF 文件夹
jar -cvmf mf.txt client.jar ./tcp ./org
```

服务端:

```shell
# 目录 server/
javac -d . -cp json.jar:jline3.jar -encoding UTF-8 src/*.java src/mainsrc/*.java src/dataTypes/*.java src/plugin/*.java
echo "Manifest-Version: 1.0">mf.txt
echo "Main-Class: tcp.client.main">>mf.txt
# 解压 json.jar 和 jline3.jar 到当前目录生成 org 和 META-INF 文件夹
jar -cvmf mf.txt server.jar ./tcp ./org
```
