cd ./framework/
javac -d . *.java
jar -cvf ../test/WEB-INF/lib/framework-1822.jar  ./etu1822/*
cd ../test/java/
javac -d ../WEB-INF/classes/ *.java
cd ../
jar -cvf D:/apache-tomcat-10.0.22/webapps/test.war WEB-INF *.jsp