import pandas as pd

file_path = "drinks.csv"
df = pd.read_csv(file_path)

df_grouped = df.groupby('continent')['spirit_servings'].describe()

print(f'continent   mean   max   min')
for i in df_grouped.index:
    print(f"{i}:   {round(df_grouped['mean'][i], 2)}   {round(df_grouped['max'][i],2)}   {round(df_grouped['min'][i], 2)}")