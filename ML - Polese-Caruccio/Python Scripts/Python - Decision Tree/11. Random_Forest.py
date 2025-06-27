from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import GridSearchCV


X_train = [[6, 7],
[2, 4],
[7, 2],
[3, 6],
[4, 7],
[5, 2],
[1, 6],
[2, 0],
[6, 3],
[4, 1]]
y_train = [0, 0, 0, 0, 0, 1, 1, 1, 1, 1]

parameters = {'max_depth': [3, 10, None]}

random_forest = RandomForestClassifier(n_estimators=100,criterion='gini', min_samples_split=30,n_jobs=-1)


# La profondità massima viene regolata come segue:

grid_search = GridSearchCV(random_forest, parameters, n_jobs=-1, cv=3, scoring='roc_auc')
grid_search.fit(X_train, y_train)
print(f'Il miglior parametro è: {grid_search.best_params_}')
print(f'Lo score del miglior parametro è: {grid_search.best_score_}')