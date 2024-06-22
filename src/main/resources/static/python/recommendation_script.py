import sys
import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from scipy.sparse import csr_matrix

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

    # Filter data for the specified user ID and keep both views and purchases
    user_interactions = data[data['userId'] == user_id]

    if user_interactions.empty:
        print(f"No interaction data found for user ID {user_id}")
        sys.exit(0)

    print("User Interactions:")
    print(user_interactions)

    # Create pivot table and normalize based on interactions (both views and purchases)
    pivot_table = user_interactions.pivot_table(index='userId', columns='productId', values='behaviorType', aggfunc='size', fill_value=0)

    # Optionally, you can apply weighting or normalization based on interaction frequency
    # For example, using log transformation to reduce the impact of very frequent items
    pivot_table_norm = np.log1p(pivot_table)

    print("Pivot table:")
    print(pivot_table)
    print("Normalized Pivot table:")
    print(pivot_table_norm)

    # Convert pivot table to sparse matrix for efficient computation
    pivot_sparse = csr_matrix(pivot_table_norm)

    # Compute cosine similarity matrix
    cosine_sim = cosine_similarity(pivot_sparse, pivot_sparse)

    print("Cosine similarity matrix:")
    print(cosine_sim)

    # Function to recommend top N products based on cosine similarity
    def recommend_products(user_id, data, pivot_table_norm, cosine_sim, top_n=10):
        # Find the index of the user in the pivot table
        user_idx = pivot_table_norm.index.get_loc(user_id)

        # Get similarity scores for all users relative to the given user
        similar_users = list(enumerate(cosine_sim[user_idx]))

        # Sort users by similarity score, descending (excluding self-similarity)
        similar_users = sorted(similar_users, key=lambda x: x[1], reverse=True)[1:]

        recommendations = {}
        for similar_user, score in similar_users:
            similar_user_id = pivot_table_norm.index[similar_user]
            # Fetch all interactions of similar users
            similar_user_interactions = data[data['userId'] == similar_user_id]
            for index, row in similar_user_interactions.iterrows():
                product_id = row['productId']
                behavior_type = row['behaviorType']
                if behavior_type == 'purchase':
                    score_weight = 2  # Weight for purchase
                else:
                    score_weight = 1  # Weight for view

                if product_id not in recommendations:
                    recommendations[product_id] = score * score_weight
                else:
                    recommendations[product_id] += score * score_weight

        # Sort recommendations by score and return top N products
        sorted_recommendations = sorted(recommendations.items(), key=lambda x: x[1], reverse=True)
        recommended_products = [rec[0] for rec in sorted_recommendations[:top_n]]
        return recommended_products

    # Get recommendations
    recommended_products = recommend_products(user_id, data, pivot_table_norm, cosine_sim, top_n=10)

    if recommended_products:
        print("Top recommended products:")
        for product in recommended_products:
            print(product)
    else:
        print("No recommendations found for this user.")

except FileNotFoundError:
    print(f"Error: File not found at path {file_path}")
except Exception as e:
    print(f"An error occurred: {e}")
