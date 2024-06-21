import tensorflow as tf
import tensorflow_recommenders as tfrs
import pandas as pd
from sklearn.model_selection import train_test_split

# Load the product data
products = pd.read_json('products.json')  # Ensure this contains all the product features

# Simulate user interactions (e.g., purchases, views, ratings)
# This is just an example, you should use actual user interaction data
interactions = pd.DataFrame({
    'user_id': [1, 2, 3, 4, 5],
    'product_id': [101, 102, 103, 104, 105],
    'rating': [5.0, 4.0, 3.0, 4.5, 5.0]
})

# Prepare the data
products['productId'] = products['productId'].astype(str)
interactions['user_id'] = interactions['user_id'].astype(str)
interactions['product_id'] = interactions['product_id'].astype(str)

train, test = train_test_split(interactions, test_size=0.2)

# Convert data to TensorFlow datasets
train_ds = tf.data.Dataset.from_tensor_slices((
    {"user_id": train['user_id'], "product_id": train['product_id']},
    train['rating']
))
test_ds = tf.data.Dataset.from_tensor_slices((
    {"user_id": test['user_id'], "product_id": test['product_id']},
    test['rating']
))

# Create a recommendation model
class RecommenderModel(tfrs.Model):
    def __init__(self):
        super().__init__()
        embedding_dimension = 32

        self.user_model = tf.keras.Sequential([
            tf.keras.layers.StringLookup(vocabulary=interactions['user_id'].unique()),
            tf.keras.layers.Embedding(len(interactions['user_id'].unique()) + 1, embedding_dimension)
        ])

        self.product_model = tf.keras.Sequential([
            tf.keras.layers.StringLookup(vocabulary=products['productId'].unique()),
            tf.keras.layers.Embedding(len(products['productId'].unique()) + 1, embedding_dimension)
        ])

        self.task = tfrs.tasks.Ranking(
            loss=tf.keras.losses.MeanSquaredError(),
            metrics=[tf.keras.metrics.RootMeanSquaredError()]
        )

    def compute_loss(self, features, target, training=False):
        user_embeddings = self.user_model(features["user_id"])
        product_embeddings = self.product_model(features["product_id"])
        return self.task(user_embeddings, product_embeddings, target)

model = RecommenderModel()
model.compile(optimizer=tf.keras.optimizers.Adagrad(0.1))

# Fit the model
model.fit(train_ds.shuffle(1000).batch(8192), epochs=3)

# Save the model
model.save('recommender_model')


# Convert products to TensorFlow dataset
product_dataset = tf.data.Dataset.from_tensor_slices(dict(products))

class SimilarityModel(tfrs.Model):
    def __init__(self):
        super().__init__()
        embedding_dimension = 32

        self.product_model = tf.keras.Sequential([
            tf.keras.layers.StringLookup(vocabulary=products['productId'].unique()),
            tf.keras.layers.Embedding(len(products['productId'].unique()) + 1, embedding_dimension)
        ])

        self.task = tfrs.tasks.Retrieval()

    def compute_loss(self, features, target, training=False):
        product_embeddings = self.product_model(features["product_id"])
        return self.task(product_embeddings)

similarity_model = SimilarityModel()
similarity_model.compile(optimizer=tf.keras.optimizers.Adagrad(0.1))

# Fit the model
similarity_model.fit(product_dataset.batch(8192), epochs=3)

# Index the product embeddings
index = tfrs.layers.factorized_top_k.BruteForce(similarity_model.product_model)
index.index_from_dataset(product_dataset.map(lambda x: (x["product_id"], similarity_model.product_model(x["product_id"]))))

# Save the model and index
similarity_model.save('similarity_model')
index.save('similarity_index')
