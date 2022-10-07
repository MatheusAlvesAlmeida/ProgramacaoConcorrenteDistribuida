wget https://repo1.maven.org/maven2/com/rabbitmq/amqp-client/5.7.1/amqp-client-5.7.1.jar
wget https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.26/slf4j-api-1.7.26.jar
wget https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/1.7.26/slf4j-simple-1.7.26.jar

export CP=.:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar

javac -cp amqp-client-5.7.1.jar Consumer/Consumer.java Producer/Producer.java

export PREFETCHCOUNT=1
java -cp $CP Consumer/Consumer $PREFETCHCOUNT
