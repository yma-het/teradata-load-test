#!/bin/bash

for NUM in `seq 1 1 20`
do
( ./exec_java2.sh
  ./exec_java.sh ) &
done
wait
