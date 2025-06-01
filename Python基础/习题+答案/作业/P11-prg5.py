import pandas as pd

file_path = "drinks.csv"
df = pd.read_csv(file_path)

df_grouped_mean = df.groupby('continent').agg({'beer_servings': 'mean', 'spirit_servings': 'mean', 'wine_servings': 'mean'})
df_grouped_median = df.groupby('continent').agg({'beer_servings': 'median', 'spirit_servings': 'median', 'wine_servings': 'median'})

print("Mean in continents\n", df_grouped_mean)
print("\nMedian in continents\n", df_grouped_median)

