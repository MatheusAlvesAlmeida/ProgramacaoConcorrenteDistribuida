for i in {1..5}; do
  sleep 1
  echo "Attempt $i"
  java /home/gabriel/Documents/CIn/programacao_concorrente/ProgramacaoConcorrenteDistribuida/04/src/UDP/cliente/Cliente.java $i &
done