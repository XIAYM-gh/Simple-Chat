### Simple Chat is not finish yet, if you find a bug or some bugs, please open an issue!

English | [中文](https://github.com/XIAYM-gh/Java-Socket-Simple-Chat/blob/master/README_cn.md)

# Feature

 - Heartbeat sending
 - Kick online users by IP Address
 - Returns a "warning page" if someone uses a web browser to access
 - Not reporting wrong connections (e.g. Using a web browser)
 - Light

# Usage
[Download latest version](https://github.com/XIAYM-gh/Java-Socket-Simple-Chat/releases/tag/v1.0.0)

Requirement: `JDK/JRE 11+`

How to launch:<br>

```shell
java -jar <File name>.jar
```

Server-side language support:

`中文(简体) , English`

Client-side language support:

`中文(简体) , English`

# Help

Server-side: type `help`

Client-side: only `stop` and `online`

# Compile

It requires some libs<br>

Server's main class: `tcp.server.main`

Client's main class: `tcp.client.main`

You can use the commands below to compile(jdk 8/11):


Client side:

```shell
#Work Directory: client/
javac -d . -cp jline3.jar -encoding UTF-8 *.java
echo "Manifest-Version: 1.0">mf.txt
echo "Main-Class: tcp.client.main">>mf.txt
#Unzip jline3.jar to get "org" folder
jar -cvmf mf.txt client.jar ./tcp ./org
```

Server side:

```shell
#Work Directory: server/
javac -d . -cp json.jar -encoding UTF-8 *.java
echo "Manifest-Version: 1.0">mf.txt
echo "Main-Class: tcp.client.main">>mf.txt
#Unzip json.jar to get "org" folder
jar -cvmf mf.txt server.jar ./tcp ./org
```
