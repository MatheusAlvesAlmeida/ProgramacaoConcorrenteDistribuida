## Uso básico

- Rode o RabbitMQ numa instância docker:
    ```
    # latest RabbitMQ 3.10
    docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management
    ```

- Agora, para começar, baixe as bibliotecas do RabbitMQ:
    ```
    wget https://repo1.maven.org/maven2/com/rabbitmq/amqp-client/5.7.1/amqp-client-5.7.1.jar
    wget https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.26/slf4j-api-1.7.26.jar
    wget https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/1.7.26/slf4j-simple-1.7.26.jar
    ```

- Em seguida, exporte os .jar para a variável CP:
    ```
    export CP=.:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar
    ```

- Por fim, para rodar os códigos:

    ```
    1 - compile-os:
        javac -cp amqp-client-5.7.1.jar Consumer/Consumer.java Producer/Producer.java

    2 - rode o consumidor:
        java -cp $CP Consumer/Consumer

    3 - rode o produtor:
        java -cp $CP Producer/Producer 0
    ```

## Caso de estresse

- Rode o script que sobe o consumidor e baixa as bibliotecas:
    ```
    ./executeConsumer.sh    
    ```

    Após esse comando, o consumidor estará inscrito numa fila, em loop infinito, aguardando por mensagens.

- Rode o script que sobe os produtores:
    ```
    ./executeProducers.sh    
    ```
