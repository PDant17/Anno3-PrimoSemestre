import numpy as np

def gini_impurity(labels):
    # When the set is empty, it is also pure
    if not labels:
        return 0
    # Count the occurrences of each label
    counts = np.unique(labels, return_counts=True)[1]
    fractions = counts / float(len(labels))
    return 1 - np.sum(fractions ** 2)   


print(f'{gini_impurity([1, 1, 0, 1, 0]):.4f}\n')
print(f'{gini_impurity([1, 1, 0, 1, 0, 0]):.4f}\n')
print(f'{gini_impurity([1, 1, 1, 1]):.4f}\n')