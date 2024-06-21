from flask import Flask, request, jsonify
import tensorflow as tf
import numpy as np

app = Flask(__name__)

# Load the trained models
recommender_model = tf.keras.models.load_model('recommender_model')
similarity_model = tf.keras.models.load_model('similarity_model')
index = tf.keras.models.load_model('similarity_index')

@app.route('/recommend/<string:user_id>', methods=['GET'])
def recommend(user_id):
    # Make predictions
    user_embedding = recommender_model.user_model([user_id])
    scores = recommender_model.product_model(np.array([i for i in range(1, len(product_ids)+1)]))
    scores = np.dot(scores, user_embedding.T).flatten()
    top_product_ids = [product_ids[i] for i in np.argsort(scores)[::-1][:10]]

    return jsonify(top_product_ids)

@app.route('/similar/<string:product_id>', methods=['GET'])
def similar_products(product_id):
    _, product_ids = index(np.array([product_id]), k=10)
    return jsonify(product_ids[0].numpy().tolist())

if __name__ == '__main__':
    app.run(port=5000)
