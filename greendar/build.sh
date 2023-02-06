#!/bin/bash
PID="Greendar"
echo $PID kill
kill -9 $(ps -ef | grep $PID | grep -v grep | awk '{print $2 }')
./gradlew build
echo "start Application!"
nohup java -jar ./build/libs/Greendar.jar &