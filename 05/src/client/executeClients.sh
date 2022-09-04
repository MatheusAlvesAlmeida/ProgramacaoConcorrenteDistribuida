javac -cp ../ Client.java

for i in {1..80}; do
  java -cp ../ client/Client &
done