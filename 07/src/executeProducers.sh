export CP=.:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar

for i in {0..40}; do
  java -cp $CP Producer/Producer $i &
done