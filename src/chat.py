import ollama

def chat():
    print("Bem-vindo ao seu chatbot privado! Digite 'sair' para encerrar.")
    while True:
        user_input = input("Você: ")
        if user_input.lower() == "sair":
            break
        response = ollama.chat(model="mistral", messages=[{"role": "user", "content": user_input}])
        print("Bot:", response["message"]["content"])

if __name__ == "__main__":
    chat()
