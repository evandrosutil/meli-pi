# Requisito 6

## USER STORY

> **COMO** vendedor **QUERO** consultar como andam as vendas dos produtos enviados para o armazém e listados em anúncios PARA definição de estratégias de produção e vendas.

## CENÁRIO

> **CENÁRIO 1**: Os produtos de um vendedor são vendidos  
> **DADO QUE** os produtos de um vendedor são vendidos  
> **E** que <span style="color:green"> os produtos foram listados em um anúncio desse vendedor </span>   
> **E** que <span style="color:green">o status da compra esteja finalizado </span>  
> **QUANDO** <span style="color:green">o vendedor consulta as vendas realizadas</span>   
> **ENTÃO**, <span style="color:green">uma lista de vendas é apresentada</span>  
> **E** <span style="color:green">e cada venda apresenta a quantidade de produtos vendidos</span>  
> **E** <span style="color:green">e apresenta o valor total</span>
> **E** <span style="color:green">e podem ser filtradas por data."</span>

## VALIDAÇÕES
- Autentique-se como vendedor e acesse os terminais.
- Consultar as vendas

## SETUP INICIAL DO WAREHOUSE
Na raíz do projeto, primeiro crie e inicie os containers com
```shell
# Linux
docker-compose -f docker/docker-compose.yaml up -d
```
```shell
# macOS
docker compose -f docker/docker-compose.yaml up -d
```
Na sequencia, rode o projeto na sua IDE ou, se preferir, rode o projeto localmente executando os comandos:
```bash
# instala as dependências do maven
mvn install

# realiza o build do projeto
mvn package

# inicia a aplicação
java -jar ./target/projeto-integrador-0.0.1-SNAPSHOT.jar

```
Depois, execute o seguinte comando para preparar o banco:
```shell
./Doc/requisitos/setup.sh
```

### Autenticar o usuário
```shell
curl -X POST http://localhost:8080/api/v1/auth -H "Content-Type: application/json" -d '{
  "email": "marketplace@email.com",
  "password": "123"
}'
```
Nota: Nas próximas requisições, feita a autenticação acima, seu _buyerId_ será 2.

A partir de agora, é possível rodar as requisições como vendedor direto no postman:

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/20174716-eb987137-d85b-4070-a261-a939c884bb14?action=collection%2Ffork&collection-url=entityId%3D20174716-eb987137-d85b-4070-a261-a939c884bb14%26entityType%3Dcollection%26workspaceId%3Da0439393-ec83-4bc9-bd89-8b59d38616c8)