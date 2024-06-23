import sys
import pandas as pd
import numpy as np
from scipy.sparse.linalg import svds
from sklearn.preprocessing import MinMaxScaler

# Check if correct number of arguments are provided
if len(sys.argv) < 3:
    print("Usage: python recommendation_script.py <file_path> <user_id>")
    sys.exit(1)

# Retrieve arguments
file_path = sys.argv[1]
user_id = int(sys.argv[2])

print(f"File path: {file_path}")
print(f"User ID: {user_id}")

try:
    # Read the CSV file
    data = pd.read_csv(file_path)
    print("CSV file loaded successfully")

    # Create pivot table and normalize based on interactions (both views and purchases)
    pivot_table = data.pivot_table(index='userId', columns='productId', values='behaviorType', aggfunc='size', fill_value=0)

    # Optionally, apply weighting or normalization based on interaction frequency
    pivot_table_norm = np.log1p(pivot_table)

    print("Pivot table:")
    print(pivot_table)
    print("Normalized Pivot table:")
    print(pivot_table_norm)

    # Convert pivot table to numpy array
    pivot_matrix = pivot_table_norm.values

    # Perform Singular Value Decomposition (SVD)
    k = min(pivot_matrix.shape) - 1  # Adjust `k` based on matrix dimensions
    U, sigma, Vt = svds(pivot_matrix, k=k)

    sigma = np.diag(sigma)

    # Reconstruct the matrix
    predicted_ratings = np.dot(np.dot(U, sigma), Vt)

    # Convert predicted ratings to DataFrame
    predicted_ratings_df = pd.DataFrame(predicted_ratings, columns=pivot_table.columns, index=pivot_table.index)

    # Scale predicted ratings to the original range of behaviorType values
    scaler = MinMaxScaler()
    predicted_ratings_scaled = scaler.fit_transform(predicted_ratings_df)

    # Get the row corresponding to the user_id
    user_row_index = pivot_table.index.get_loc(user_id)
    user_predictions = predicted_ratings_scaled[user_row_index]

    # Sort the user's predicted ratings and get top N products
    top_n = 10
    recommended_product_ids = np.argsort(-user_predictions)[:top_n]

    # Print top recommended product IDs
    recommended_products = pivot_table.columns[recommended_product_ids].tolist()
    print("Top recommended products:")
    for product in recommended_products:
        print(product)

except FileNotFoundError:
    print(f"Error: File not found at path {file_path}")
except Exception as e:
    print(f"An error occurred: {e}")
