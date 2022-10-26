javac client/Client.java

for i in {1..10}; do
    java client/Client $i &
done
