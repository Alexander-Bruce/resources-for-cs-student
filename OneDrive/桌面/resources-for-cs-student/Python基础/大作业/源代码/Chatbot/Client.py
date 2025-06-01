import socket
import threading
import time

HEADER = 64
PORT = 2333
SERVER = socket.gethostbyname(socket.gethostname())
FORMAT = 'utf-8'
DISCONNECTION = "EXIT"
ADDR = (SERVER, PORT)

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.connect(ADDR)


def send(msg):
    message = msg.encode(FORMAT)
    msg_length = len(message)
    header_message = str(msg_length).encode(FORMAT)
    header_message += b' ' * (HEADER - len(header_message))
    client.send(header_message)
    client.send(message)


def receive():
    _, port = client.getsockname()
    while True:
        try:
            msg = client.recv(HEADER).decode(FORMAT)
            if not msg:
                print("Server disconnected.")
                break
            if msg:
                print(f"Recieved: {msg}")
        except ConnectionResetError:
            print("Server disconnected.")
            break
        except:
            print("An error occurred while receiving.")
            break
        time.sleep(1)


if __name__ == "__main__":
    connected = True
    receive_thread = threading.Thread(target=receive, daemon=True)
    receive_thread.start()

    while connected:
        message = input()
        send(message)
        if message == DISCONNECTION:
            connected = False
