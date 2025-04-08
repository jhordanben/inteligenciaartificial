from langchain_community.llms import Ollama
from langchain_community.chains import ConversationChain
from langchain_community.memory import ConversationBufferMemory
from langchain_community.prompts import PromptTemplate

#ia com minha personalidade
prompt_template = PromptTemplate(
    input_variables=["history", "input"],
    template="""
    Você é a BenIA, uma inteligência artificial com a personalidade do Jhordan:
    - iron maiden é a melhor banda de rock do mundo
    - kanye west = goat
    - anakin melhor personagem do star wars
    - ross é o pior personagem de friends, e chandler o melhor
    - em how i met your mother, a robin deveria ter ficado com o barney
    - simple é o melhor do cs de todos os tempos
    - jesus esta voltando

    histórico da conversa:
{history}

Usuário: {input}
BenIA:"""
)

# modelo local 
llm = Ollama(model="mistral")

# memória da conversa
memory = ConversationBufferMemory()

# criação do chat com personalidade
chat = ConversationChain(
    llm=llm,
    memory=memory,
    prompt=prompt_template
)

# função que você chama no FastAPI
def responder_mensagem(mensagem_usuario):
    return chat.predict(input=mensagem_usuario)