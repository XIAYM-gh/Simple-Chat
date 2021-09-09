### Simple Chat还未完全完成，如果发现问题请提交issue!

# Feature

 - 多线程心跳检测
 - 能够踢出在线用户(通过IP地址)
 - 当浏览器访问时会返回警告页面
 - 自动屏蔽错误的连接请求
 - 轻量化

# Usage

需求: `JDK/JRE 11+`

启动方式:<br>

```shell
java -jar 文件名.jar
```

服务端目前支持的语言:

`中文(简体) , English`

客户端目前支持的语言:

`中文(简体)`

** 目前还在适配阶段 **

# Help

服务端可以使用 `help` 命令查看所有帮助.

客户端目前只有 `stop` 和 `online` 能够使用.

# Compile

编译时需要包含jline3<br>
并且把jline3的jar文件内的org文件夹添加到server/client.jar中<br>
服务端主类: tcp.server.main<br>
客户端主类: tcp.client.main<br>
均可以使用此命令编译(jdk 8/11 Release中使用jdk/jre 11):

`root@localhost:~/源码文件夹/# javac -d . -cp jline3.jar -encoding UTF-8 *.java`
