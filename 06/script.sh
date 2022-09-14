mvn clean package

NUM_CLIENTES=2

mvn exec:java -Dexec.mainClass="io.confluent.examples.clients.cloud.ProducerExample" \
-Dexec.args="$HOME/.confluent/java.config $NUM_CLIENTES"

for i in 0; do # TODO deve iterar de 0 ate NUM_CLIENTES, mas nao ta indo direito
  mvn exec:java -Dexec.mainClass="io.confluent.examples.clients.cloud.ConsumerExample" \
-Dexec.args="$HOME/.confluent/java.config $i" &
done