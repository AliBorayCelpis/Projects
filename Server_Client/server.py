import socket

# Create a UDP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
# Bind the socket to the port
server_address = ('localhost', 10000)
sock.bind(server_address)

print(f"Server is running on {server_address[0]} port {server_address[1]}")

while True:
    # Receive message
    data, address = sock.recvfrom(4096)
    message = data.decode('utf-8')
    seq_number = message.split()[3]  # Assuming the SEQ is the fourth word in the message
    packet_length = len(data)
    
    # Send ACK
    ack_message = f'ACK for SEQ {seq_number} with packet length: {packet_length}'
    encoded_ack_message = ack_message.encode('utf-8')
    sock.sendto(encoded_ack_message, address)
    print(f'Sent: {ack_message}')

# Note: No manual mode and error handling are implemented in this snippet