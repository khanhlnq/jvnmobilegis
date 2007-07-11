JVNMobileGIS - WMS mobile viewer
Copyright 2007 - JavaVietnam.org

1. Java IDE
This project is ready for import into popular Java IDEs:
- NetBeans 5.5.x or above (with Mobility Pack)
- IntelliJ IDEA
It is not ready for Eclipse yet because I cannot make this project work with EclipseME in my Linux machine. Anybody can build, run and debug this project with EclipseME?
Be careful when you switch development environment, your tool may overwrite our JAD settings.

2. Without java IDE
You can also build/emulate this project without IDE, all you need are:
- A text editor
- Sun Java Wireless Toolkit. Just copy this project to "apps" folder of WTK

3. Obfuscate
To make a small size application, I recommend using Proguard to obfuscate the deployed package.

4. Make executable file for Windows
I don't like Windows! But to bring convenience to non-Java users, I have included Mide2Exe (http://kwyshell.myweb.hinet.net/Project/Midp2Exe/) to "bin" folder. It's easy to use, you can make the executable file yourself to demo it without Java installed.
Run "Midp2Exe -jar JVNMobileGIS.jar -jad JVNMobileGIS.jad -o JVNMobileGIS.exe" in "bin" directory to make the file.

Happy developing!!!
  
Contact information:
Khanh Le Ngoc Quoc
khanh.lnq AT gmail.com
