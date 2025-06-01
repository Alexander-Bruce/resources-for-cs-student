import socket 
import threading   
import requests

HEADER = 64
PORT = 2333
SERVER = socket.gethostbyname(socket.gethostname())
ADDR = (SERVER, PORT)
FORMAT = 'utf-8'
DISCONNECTION = "EXIT"
QINGYUNKE_API_URL = 'http://api.qingyunke.com/api.php'

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(ADDR)

# 存储客户端连接的列表
clients = []

def get_bot_reply(user_input):
    params = {
        'key': 'free',
        'appid': 0,
        'msg': user_input
    }
    response = requests.get(QINGYUNKE_API_URL, params=params)
    if response.status_code == 200:
        data = response.json()
        bot_reply = data['content']
        return bot_reply
    else:
        return 'Connected error, please try again'

def handle_client(conn, addr):
    print(f"{addr} is connected")
    connected = True
    while connected:
        try:
            msg_length = conn.recv(HEADER).decode(FORMAT)
            if msg_length:
                msg_length = int(msg_length)
                msg = conn.recv(msg_length).decode(FORMAT)
                if msg == DISCONNECTION:
                    connected = False
                    print(f"{addr} is disconnected")
                else:
                    # 将消息传回给每个客户端
                    msg = get_bot_reply(msg)
                    conn.send(msg.encode(FORMAT))
        except ConnectionResetError:
            print(f"{addr} is forcibly disconnected")
            break
    conn.close()
    clients.remove(conn)  # 将断开连接的客户端从列表中移除

def start():
    print("Server is already started......")
    server.listen()
    while True:
        conn, addr = server.accept()
        clients.append(conn)  # 将新连接的客户端添加到列表中
        thread = threading.Thread(target=handle_client, args=(conn, addr))
        thread.start()

if __name__ == "__main__":
    start()
