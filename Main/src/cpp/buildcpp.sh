#!/bin/bash
javac -h . ../com/game/nativeclass/Native.java
g++ -c -fPIC -I/usr/lib/jvm/java-8-openjdk-amd64/include -I/usr/lib/jvm/java-8-openjdk-amd64/include/linux com_game_nativeclass_Native.cpp -o com_game_nativeclass_Native.o
g++ -shared -fPIC -o libnative.so com_game_nativeclass_Native.o -lc

