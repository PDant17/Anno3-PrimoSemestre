import matplotlib.pyplot as plt
import numpy as np

X_train = [['tech', 'professional'],
           ['fashion', 'student'],
           ['fashion', 'professional'],
           ['sports', 'student'],
           ['tech', 'student'],
           ['tech', 'retired'],
           ['sports', 'professional']]

y_train = [1,
           0,
           0,
           0,
           1,
           0,
           1]

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


criterion_function_np = {'gini': gini_impurity, 'entropy': entropy}
def weighted_impurity(groups, criterion='gini'):
    """
    Calculate weighted impurity of children after a split
    @param groups: list of children, and a child consists a list of class labels
    @param criterion: metric to measure the quality of a split, 'gini' for Gini Impurity or 'entropy' for Information Gain
    @return: float, weighted impurity
    """
    total = sum(len(group) for group in groups)
    weighted_sum = 0.0
    for group in groups:
        weighted_sum += len(group) / float(total) * criterion_function_np[criterion](group)
    return weighted_sum


print(f"\nGini split (Interest,Tech): {weighted_impurity([[[0, 1], [1, 1, 0]]],'gini'):.4f}")

print(f"\nGini split (Interest,Sport): {weighted_impurity([[1, 1, 0],[0, 1]],'gini'):.4f}")

print(f"\nGini split (Occupation,Professional): {weighted_impurity([[0, 1, 0],[1, 1]],'gini'):.4f}")

print(f"\nGini split (Occupation,Student): {weighted_impurity([[1, 0, 1],[0, 1]],'gini'):.4f}")

print(f"\nGini split (Occupation,Retired): {weighted_impurity([[1, 0, 1, 1],[0]],'gini'):.4f}")

#--------------------------

print(f"\nEntropy split (Interest,Tech): {weighted_impurity([[[0, 1], [1, 1, 0]]],'entropy'):.4f}")

print(f"\nEntropy split (Interest,Sport): {weighted_impurity([[1, 1, 0],[0, 1]],'entropy'):.4f}")

print(f"\nEntropy split (Occupation,Professional): {weighted_impurity([[0, 1, 0],[1, 1]],'entropy'):.4f}")

print(f"\nEntropy split (Occupation,Student): {weighted_impurity([[1, 0, 1],[0, 1]],'entropy'):.4f}")

print(f"\nGini split (Occupation,Retired): {weighted_impurity([[1, 0, 1, 1],[0]],'gini'):.4f}")