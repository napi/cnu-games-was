#!/bin/bash
if ! [ -d /var/log/riot ];
then
  sudo mkdir /var/log/riot
fi
if ! [ -d /home/ec2-user/$1 ];
then
  sudo mkdir /home/ec2-user/$1
fi
cp -f $WORKSPACE/target/*.jar /home/ec2-user/$1
cd /home/ec2-user/$1
sudo /usr/bin/nohup /usr/bin/java -Xmx128M -jar -Dapplication.name=$1 -Dspring.profiles.active=dev /home/ec2-user/$1/*.jar > /dev/null 2> /dev/null < /dev/null &
