#!/bin/bash

##got sketch and then modified from stack overflow
arg1=$1
arg2=$2
arg3=$3

##directory where jar file is located    
dir=./

##jar file name
jar_name= #you have to change this to the arguments for the input type you want to test

## Perform some validation on input arguments, one example below
if [ -z "$1" ] || [ -z "$2" ] || [ -z "$3" ]; then
        echo "Missing arguments, exiting..."
        echo "Usage : $0 <constraints>.txt <prefs>.txt <schedule>.txt"
        exit 1
fi

java -jar $dir/$jar_name $arg1 $arg2 $arg3