PATH=%PATH%;.
#This will compile the java code that reads the file
javac -cp .;./curve-core-development-SNAPSHOT.jar compat.java
java -cp .;./curve-core-development-SNAPSHOT.jar compat  %1
