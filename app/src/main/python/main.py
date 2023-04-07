import requests
import json
from smtplib import SMTP
from email.message import EmailMessage
from random import randint


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


def users():
    try:
        r = requests.get(url).text
        dict_obj = json.loads(r)
        print(dict_obj["home"])
        return dict_obj["users"]

    except:
        print("Exception")
        return None
    

def send_mail(email_id):
    admin_mail = "harini2023project@gmail.com"
    admin_password = "fundowawfgiguhvq"
    code = randint(10**6,10**7-1)
    email = EmailMessage()
    email["From"] = admin_mail
    email["To"] = email_id
    email["Subject"] = "NCC Verification code"
    email.set_content(f"Your verification code is {code}")

    try:
        smtp_request = SMTP(host="smtp.gmail.com",port=587)
        smtp_request.starttls()
        smtp_request.login(admin_mail,admin_password)
        smtp_request.send_message(email)

        print("Mail Sent")
        return code

    except:
        return -1

        
    

# home()
# send_mail("seenusanjay20102002@gmail.com")