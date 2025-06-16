import pickle
import numpy as np
import os

# Load model once
model_path = os.path.join(os.path.dirname(__file__), "model.pkl")

with open(model_path, "rb") as f:
    model = pickle.load(f)

# This function will be called from Android (Java) via Chaquopy
def recommendation(N, P, K, temperature, humidity, ph, rainfall):
    features = np.array([[N, P, K, temperature, humidity, ph, rainfall]])
    prediction = model.predict(features)
    return prediction[0]  # directly return predicted crop name


