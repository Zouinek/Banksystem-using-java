#!/bin/bash

BASE_PATH="/home/wasd/projects/Banksystem"
SRC_PATH="$BASE_PATH/src"
OUT_PATH="$BASE_PATH/out/production/Banksystem"

mkdir -p "$OUT_PATH"

echo "Compiling Java files..."
javac -d "$OUT_PATH" -classpath "$OUT_PATH" $(find "$SRC_PATH" -name "*.java")

if [ $? -eq 0 ]; then
	clear
	java -classpath "$OUT_PATH" App
else
	echo "Compilation failed."
	exit 1
fi
