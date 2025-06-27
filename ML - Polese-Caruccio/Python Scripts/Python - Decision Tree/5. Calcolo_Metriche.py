import matplotlib.pyplot as plt
import numpy as np

def gini_impurity(labels):
    # When the set is empty, it is also pure
    if not labels:
        return 0
    # Count the occurrences of each label
    counts = np.unique(labels, return_counts=True)[1]
    fractions = counts / float(len(labels))
    return 1 - np.sum(fractions ** 2)   

def entropy(labels):
 if not labels:
    return 0
 counts = np.unique(labels, return_counts=True)[1]
 fractions = counts / float(len(labels))
 return - np.sum(fractions * np.log2(fractions))

criterion_function = {'gini': gini_impurity,'entropy': entropy}

def weighted_impurity(groups, criterion='gini'):
    """
    Calculate weighted impurity of children after a split
    @param groups: list of children, and a child consists a
    list of class labels
    @param criterion: metric to measure the quality of a split,
    'gini' for Gini Impurity or 'entropy' for
    Information Gain
    @return: float, weighted impurity
    """
    total = sum(len(group) for group in groups)
    weighted_sum = 0.0

    for group in groups:
        weighted_sum += len(group) / float(total) * criterion_function[criterion](group)
    
    return weighted_sum



children_1 = [[1, 0, 1], [0, 1]]
children_2 = [[1, 1], [0, 0, 1]]
print(f"Entropy of #1 split: {weighted_impurity(children_1,'entropy'):.4f}")

#  Entropy of #1 split: 0.9510
print(f"Entropy of #2 split: {weighted_impurity(children_2, 'entropy'):.4f}")