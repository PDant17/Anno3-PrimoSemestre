import matplotlib.pyplot as plt
import numpy as np

# La frazione della classe positiva varia da 0 a 1:
pos_fraction = np.linspace(0.00, 1.00, 1000)

gini = 1 - pos_fraction**2 - (1-pos_fraction)**2

plt.plot(pos_fraction, gini)
plt.ylim(0, 1)
plt.xlabel('Positive fraction')
plt.ylabel('Gini Impurity')
plt.show()