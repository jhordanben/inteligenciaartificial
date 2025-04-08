from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
from langchain_core.prompts import ChatPromptTemplate
from langchain_openai import ChatOpenAI
import os
from dotenv import load_dotenv

# Carrega variáveis do .env
load_dotenv()

# Inicializa FastAPI
app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)


llm = ChatOpenAI(
    model="gpt-3.5-turbo",
    api_key=os.getenv("OPENAI_API_KEY")
)



prompt = ChatPromptTemplate.from_messages([
    ("system", "Você é a BenIA, uma assistente pessoal gentil, divertida e esperta."),
    ("user", "{input}")
])

@app.post("/chat")
async def chat(request: Request):
    try:
        data = await request.json()
        user_input = data.get("message", "")

        print("📥 Recebido do front:", user_input)

        if not user_input:
            print("⚠️ Nenhuma mensagem recebida no corpo da requisição.")
            return {"response": "Mensagem vazia."}

        chain = prompt | llm
        response = chain.invoke({"input": user_input})

        print("🧠 Resposta da BenIA:", response.content)
        return {"response": response.content}
    
    except Exception as e:
        print("🔥 Erro geral no chat:", str(e))
        return {"response": f"Erro interno: {str(e)}"}