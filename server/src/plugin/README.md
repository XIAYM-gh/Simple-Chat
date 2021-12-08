### Plugin Service
Reference part:

- CSDN

- [1689295068/Miraibot (Plugin)](https://github.com/1689295608/MiraiBot/tree/main/src/com/windowx/miraibot/plugin) [AGPL v3]

### Simple doc
To write a Plugin, you should prepare these files:

- ./config.properties
  ** Example: **
  ** name=examplePlugin **
  ** version=1.0 **
  ** author=uwu **
  ** main=package.name.className (like cn.xiaym.testPlugin.main) **

- ./path/to/your/classes/

Example plugin:
```java
package my.firstPlugin;

import tcp.server.plugin.*;

public class main extends SimpleChatPlugin{
        @Override
        public void onEnable(){
                this.log("Hello World!");
        }
}
```
