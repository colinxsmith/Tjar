PATH=%PATH%;.
#This will compile the java code that reads the file
javac -cp ".;./curve-core-46.33.jar" compat.java
java -cp ".;./curve-core-46.33.jar" compat  %1
