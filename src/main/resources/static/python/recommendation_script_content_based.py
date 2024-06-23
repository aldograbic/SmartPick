import sys
import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer

# Check if correct number of arguments are provided
if len(sys.argv) < 4:
    print("Usage: python recommendation_script.py <interactions_file_path> <products_file_path> <user_id>")
    sys.exit(1)

# Retrieve arguments
interactions_file_path = sys.argv[1]
products_file_path = sys.argv[2]
user_id = int(sys.argv[3])

print(f"Interactions file path: {interactions_file_path}")
print(f"Products file path: {products_file_path}")
print(f"User ID: {user_id}")

try:
    # Read the CSV files
    interactions_data = pd.read_csv(interactions_file_path)
    products_data = pd.read_csv(products_file_path)  # Load products CSV data

    print("CSV files loaded successfully")

    # Assuming products_data contains a 'productId' column and a 'description' column with product descriptions or features
    tfidf = TfidfVectorizer(stop_words='english')
    tfidf_matrix = tfidf.fit_transform(products_data['description'].fillna(''))  # Adjusted to 'description' column name from CSV

    # Compute cosine similarity matrix for products
    product_cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

    print("Cosine similarity matrix for products:")
    print(product_cosine_sim)

    # Function to recommend top N products based on product similarity
    def recommend_products(user_id, interactions_data, products_data, product_cosine_sim, top_n=10):
        # Fetch interactions of the given user
        user_interactions = interactions_data[interactions_data['userId'] == user_id]

        # Aggregate scores for each product based on user interactions
        recommendations = {}
        for index, row in user_interactions.iterrows():
            product_id = row['productId']
            behavior_type = row['behaviorType']
            product_idx = products_data[products_data['productId'] == product_id].index[0]
            similar_products = list(enumerate(product_cosine_sim[product_idx]))

            for similar_product, score in similar_products:
                similar_product_id = products_data.iloc[similar_product]['productId']
                if behavior_type == 'purchase':
                    score_weight = 2  # Weight for purchase
                else:
                    score_weight = 1  # Weight for view

                if similar_product_id not in recommendations:
                    recommendations[similar_product_id] = score * score_weight
                else:
                    recommendations[similar_product_id] += score * score_weight

        # Sort recommendations by score and return top N products
        sorted_recommendations = sorted(recommendations.items(), key=lambda x: x[1], reverse=True)
        recommended_products = [rec[0] for rec in sorted_recommendations[:top_n]]
        return recommended_products

    # Get recommendations
    recommended_products = recommend_products(user_id, interactions_data, products_data, product_cosine_sim, top_n=10)

    if recommended_products:
        for product in recommended_products:
            print(product)
    else:
        print("No recommendations found for this user.")

except FileNotFoundError:
    print(f"Error: File not found at path {interactions_file_path} or {products_file_path}")
except Exception as e:
    print(f"An error occurred: {e}")
