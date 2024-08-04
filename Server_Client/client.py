import socket

# Server address and port
server_address = ('localhost', 10000)
# Create a UDP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.settimeout(2)  # Timeout for the socket operations

sequence_number = 0  # Starting sequence number

while True:
    sequence_number += 1  # Increment the sequence number for the new packet
    message = f'Message with SEQ {sequence_number}'
    encoded_message = message.encode('utf-8')
    packet_length = len(encoded_message)
    
    # Send data
    print(f'Sending: {message} with packet length: {packet_length}')
    sent = sock.sendto(encoded_message, server_address)

    # Receive response
    try:
        data, server = sock.recvfrom(4096)
        print(f'Received: {data.decode("utf-8")}')
    except socket.timeout:
        print('No ACK received, resending...')
        sequence_number -= 1  # Decrement the sequence number to resend the same packet

    # User prompt to continue or quit
    user_input = input("Press Enter to send next message or type 'exit' to quit: ")
    if user_input.lower() == 'exit':
        break

# Close the socket
sock.close()