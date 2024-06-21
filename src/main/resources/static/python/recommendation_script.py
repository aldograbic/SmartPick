import sys
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
from scipy.sparse import csr_matrix

# Pretpostavljamo da je putanja do CSV datoteke proslijeđena kao argument
file_path = sys.argv[1]
user_id = int(sys.argv[2])

data = pd.read_csv(file_path)
pivot_table = data.pivot_table(index='userId', columns='productId', values='behaviorType', aggfunc='count', fill_value=0)
pivot_sparse = csr_matrix(pivot_table)

cosine_sim = cosine_similarity(pivot_sparse)

def recommend_products(user_id, pivot_table, cosine_sim, top_n=5):
    user_idx = pivot_table.index.get_loc(user_id)
    similar_users = list(enumerate(cosine_sim[user_idx]))
    similar_users = sorted(similar_users, key=lambda x: x[1], reverse=True)[1:]
    recommendations = {}
    for similar_user, score in similar_users:
        similar_user_id = pivot_table.index[similar_user]
        similar_user_interactions = data[data['userId'] == similar_user_id]
        for product_id in similar_user_interactions['productId']:
            if product_id not in recommendations:
                recommendations[product_id] = score
            else:
                recommendations[product_id] += score
    sorted_recommendations = sorted(recommendations.items(), key=lambda x: x[1], reverse=True)
    recommended_products = [rec[0] for rec in sorted_recommendations[:top_n]]
    return recommended_products

recommended_products = recommend_products(user_id, pivot_table, cosine_sim)
for product in recommended_products:
    print(product)
