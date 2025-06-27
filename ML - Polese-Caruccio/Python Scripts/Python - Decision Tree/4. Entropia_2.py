import numpy as np

def entropy(labels):
 if not labels:
    return 0
 counts = np.unique(labels, return_counts=True)[1]
 fractions = counts / float(len(labels))
 return - np.sum(fractions * np.log2(fractions))


print(f'{entropy([1, 1, 0, 1, 0]):.4f}')
print(f'{entropy([1, 1, 0, 1, 0, 0]):.4f}')
print(f'{entropy([1, 1, 1, 1]):.4f}')