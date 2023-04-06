import requests
import json

url = "https://nccapp-62042-default-rtdb.firebaseio.com/.json"

def home():
    try:
        r = requests.get(url).text
        dict_obj = json.loads(r)
        print(dict_obj["home"])
        return dict_obj

    except:
        print("Exception")
        return None
    

#home()