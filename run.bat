echo off
PATH=%PATH%;.
:This will compile the java code that reads the file
set JAR=%2
if "%JAR%"==""   set JAR=curve-core-development-SNAPSHOT.jar
javac -cp ".;./%JAR%" compat.java
java -cp ".;./%JAR%" compat  %1
