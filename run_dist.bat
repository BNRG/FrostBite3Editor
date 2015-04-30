@echo off
title FrostBite3Editor Console
mode con: cols=130 lines=20> nul
echo DO NOT CLOSE THIS WINDOW, ITS REQUIRED!
echo JavaFX does only works on Java 8+
java -jar dist.jar
pause