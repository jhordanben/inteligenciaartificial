import ollama

def ask_ollama(message):
    response = ollama.chat(model='llama2:13b', messages=[
        {'role': 'user', 'content': message},
    ])
    return response['message']['content']