import numpy as np

def main():
    for i in [5, 10, 20, 40, 80]:
        with open('RTTS_' + i + '.txt') as f:
            valores = []

            for line in f:
                valores.append(float(line))
            
            mean = np.mean(valores)
            std = np.std(valores)
            
            print("Dados {}\nMedia: {} - Desvio: {}".format(i, mean, std))