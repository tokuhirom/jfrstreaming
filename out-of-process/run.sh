#!/bin/bash
javac Producer.java
javac Consumer.java

java -XX:StartFlightRecording -XX:FlightRecorderOptions=repository=/tmp/abc -XX:+FlightRecorder Producer &
PRODUCER_PID=$!

jcmd $PRODUCER_PID JFR.start
JFRPATH=$(jinfo -sysprops $PRODUCER_PID | grep jdk.jfr.repository | perl -nE 'print $1 if /jdk.jfr.repository=(\S+)/')
echo "JFRPATH='$JFRPATH'"

java Consumer "$JFRPATH"

function cleanup {
  kill $PRODUCER_PID
}

trap cleanup EXIT

