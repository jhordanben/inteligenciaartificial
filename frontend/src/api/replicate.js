import axios from 'axios';


const REPLICATE_API_TOKEN = "//PROTEGIDO";


export async function gerarImagemReplicate(prompt) {
    try {
       
        const { data: prediction } = await axios.post(
            "https://api.replicate.com/v1/predictions",
            {
                version: "db21e45fb98b0a28d0ef5c546f2780c19cb2b25d778d8e144452c0ffdfb8f168",
                input: { prompt },
            },
            {
                headers: {
                    Authorization: `Token ${REPLICATE_API_TOKEN}`,
                    "Content-Type": "application/json",
                },
            }
        );

        const urlResultado = prediction.urls.get;

        
        const aguardarResultado = async () => {
            let resultado;

            while (true) {
                const { data } = await axios.get(urlResultado, {
                    headers: {
                        Authorization: `Token ${REPLICATE_API_TOKEN}`,
                    },
                });

                if (data.status === "succeeded") {
                    return data.output[0]; 
                }

                if (data.status === "failed") {
                    throw new Error("Falha ao gerar imagem");
                }

               
                await new Promise(res => setTimeout(res, 1000));
            }
        };

        return await aguardarResultado();
    } catch (erro) {
        console.error("Erro na geração de imagem:", erro);
        throw erro;
    }
}
