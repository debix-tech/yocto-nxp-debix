#!/bin/bash

while [ 1 ] 
do
	echo 0 > /sys/class/leds/KV_COIL_EN/brightness
	echo 0 > /sys/class/leds/Dout1/brightness
	echo 0 > /sys/class/leds/Dout2/brightness
	sleep 1 
	echo 1 > /sys/class/leds/KV_COIL_EN/brightness
	echo 1 > /sys/class/leds/Dout1/brightness
	echo 1 > /sys/class/leds/Dout2/brightness
	sleep 1 
done
