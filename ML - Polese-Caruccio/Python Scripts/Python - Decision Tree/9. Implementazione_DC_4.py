import matplotlib.pyplot as plt
import numpy as np

X_train_n = [[6, 7],
[2, 4],
[7, 2],
[3, 6],
[4, 7],
[5, 2],
[1, 6],
[2, 0],
[6, 3],
[4, 1]]
y_train_n = [0, 0, 0, 0, 0, 1, 1, 1, 1, 1]

def gini_impurity_np(labels):
    # When the set is empty, it is also pure
    if labels.size == 0:
        return 0
    # Count the occurrences of each label
    counts = np.unique(labels, return_counts=True)[1]
    fractions = counts / float(len(labels))
    return 1 - np.sum(fractions ** 2)


def entropy_np(labels):
    # When the set is empty, it is also pure
    if labels.size == 0:
        return 0
    counts = np.unique(labels, return_counts=True)[1]
    fractions = counts / float(len(labels))
    return - np.sum(fractions * np.log2(fractions))


criterion_function_np = {'gini': gini_impurity_np, 'entropy': entropy_np}
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


def split_node(X, y, index, value):
    """
    Split data set X, y based on a feature and a value
    @param X: numpy.ndarray, dataset feature
    @param y: numpy.ndarray, dataset target
    @param index: int, index of the feature used for splitting
    @param value: value of the feature used for splitting
    @return: list, list: left and right child, a child is in the format of [X, y]
    """
    x_index = X[:, index]
    # if this feature is numerical
    if X[0, index].dtype.kind in ['i', 'f']:
        mask = x_index >= value
    # if this feature is categorical
    else:
        mask = x_index == value
    # split into left and right child
    left = [X[~mask, :], y[~mask]]
    right = [X[mask, :], y[mask]]
    return left, right


def get_best_split(X, y, criterion):
    """
    Obtain the best splitting point and resulting children for the data set X, y
    @param X: numpy.ndarray, dataset feature
    @param y: numpy.ndarray, dataset target
    @param criterion: gini or entropy
    @return: dict {index: index of the feature, value: feature value, children: left and right children}
    """
    best_index, best_value, best_score, children = None, None, 1, None
    for index in range(len(X[0])):
        for value in np.sort(np.unique(X[:, index])):
            groups = split_node(X, y, index, value)
            impurity = weighted_impurity([groups[0][1], groups[1][1]], criterion)
            if impurity < best_score:
                best_index, best_value, best_score, children = index, value, impurity, groups
    return {'index': best_index, 'value': best_value, 'children': children}



def get_leaf(labels):
    # Obtain the leaf as the majority of the labels
    return np.bincount(labels).argmax()



def split(node, max_depth, min_size, depth, criterion):
    """
    Split children of a node to construct new nodes or assign them terminals
    @param node: dict, with children info
    @param max_depth: int, maximal depth of the tree
    @param min_size: int, minimal samples required to further split a child
    @param depth: int, current depth of the node
    @param criterion: gini or entropy
    """
    left, right = node['children']
    del (node['children'])
    if left[1].size == 0:
        node['right'] = get_leaf(right[1])
        return
    if right[1].size == 0:
        node['left'] = get_leaf(left[1])
        return
    # Check if the current depth exceeds the maximal depth
    if depth >= max_depth:
        node['left'], node['right'] = get_leaf(left[1]), get_leaf(right[1])
        return
    # Check if the left child has enough samples
    if left[1].size <= min_size:
        node['left'] = get_leaf(left[1])
    else:
        # It has enough samples, we further split it
        result = get_best_split(left[0], left[1], criterion)
        result_left, result_right = result['children']
        if result_left[1].size == 0:
            node['left'] = get_leaf(result_right[1])
        elif result_right[1].size == 0:
            node['left'] = get_leaf(result_left[1])
        else:
            node['left'] = result
            split(node['left'], max_depth, min_size, depth + 1, criterion)
    # Check if the right child has enough samples
    if right[1].size <= min_size:
        node['right'] = get_leaf(right[1])
    else:
        # It has enough samples, we further split it
        result = get_best_split(right[0], right[1], criterion)
        result_left, result_right = result['children']
        if result_left[1].size == 0:
            node['right'] = get_leaf(result_right[1])
        elif result_right[1].size == 0:
            node['right'] = get_leaf(result_left[1])
        else:
            node['right'] = result
            split(node['right'], max_depth, min_size, depth + 1, criterion)


def train_tree(X_train, y_train, max_depth, min_size, criterion='gini'):
    """
    Construction of a tree starts here
    @param X_train: list of training samples (feature)
    @param y_train: list of training samples (target)
    @param max_depth: int, maximal depth of the tree
    @param min_size: int, minimal samples required to further split a child
    @param criterion: gini or entropy
    """
    X = np.array(X_train)
    y = np.array(y_train)
    root = get_best_split(X, y, criterion)
    split(root, max_depth, min_size, 1, criterion)
    return root



CONDITION = {'numerical': {'yes': '>=', 'no': '<'},
             'categorical': {'yes': 'is', 'no': 'is not'}}

def visualize_tree(node, depth=0):
    if isinstance(node, dict):
        if node['value'].dtype.kind in ['i', 'f']:
            condition = CONDITION['numerical']
        else:
            condition = CONDITION['categorical']
        print('{}|- X{} {} {}'.format(depth * '  ', node['index'] + 1, condition['no'], node['value']))
        if 'left' in node:
            visualize_tree(node['left'], depth + 1)
        print('{}|- X{} {} {}'.format(depth * '  ', node['index'] + 1, condition['yes'], node['value']))
        if 'right' in node:
            visualize_tree(node['right'], depth + 1)
    else:
        print(f"{depth * '  '}[{node}]")

tree = train_tree(X_train_n, y_train_n, 2, 2)
visualize_tree(tree)