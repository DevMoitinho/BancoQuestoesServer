# BancoQuestõesServer
 
API REST para armazenamento e gestão de questões, pensada para professores que precisam organizar um banco de questões por disciplina e montar provas.
 
> **Status:** em desenvolvimento.
> O front-end está em um repositório separado: [BancoQuestoesInterface](https://github.com/DevMoitinho/BancoQuestoesInterface).
 
## Funcionalidades
 
- CRUD de questões, cada uma com enunciado, alternativas (uma ou mais marcadas como corretas), disciplina e tags
- Pesquisa de questões por texto, disciplina e tags
- Organização por disciplinas e tags (CRUD de cada uma)
- Geração de provas: sorteio aleatório de questões de uma disciplina, com o tamanho da prova escolhido na requisição
- Autenticação com JWT
- Documentação interativa da API com Swagger UI
## Tecnologias
 
- Java 21
- Spring Boot 4.1
- Spring Data JPA
- Spring Security + JWT ([jjwt](https://github.com/jwtk/jjwt))
- Bean Validation
- springdoc-openapi (Swagger UI)
- ModelMapper
- Lombok
- PostgreSQL
- Docker
- Maven
## Pré-requisitos
 
- JDK 21
- PostgreSQL em execução
- Git
- Docker (opcional, para rodar em container)
## Como configurar
 
### 1. Clonar o repositório
 
```bash
git clone https://github.com/DevMoitinho/BancoQuestoesServer.git
cd BancoQuestoesServer
```
 
### 2. Configurar a aplicação
 
Defina as propriedades abaixo no `application.properties`:
 
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bancoquestoes
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
jwt.secret=uma_chave_longa_e_aleatoria
```
 
| Propriedade | Descrição |
|---|---|
| `spring.datasource.url` | URL de conexão com o PostgreSQL |
| `spring.datasource.username` | Usuário do banco |
| `spring.datasource.password` | Senha do banco |
| `jwt.secret` | Chave secreta usada para assinar os tokens JWT |
 
> Nunca envie senhas ou chaves reais para o repositório. Em produção, prefira variáveis de ambiente (veja a seção sobre Docker).
 
### 3. Executar
 
**Localmente, com Maven:**
 
```bash
./mvnw spring-boot:run
```
 
**Com Docker:**
 
As propriedades também podem ser passadas como variáveis de ambiente.
 
```bash
docker build -t bancoquestoes-server .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/bancoquestoes \
  -e SPRING_DATASOURCE_USERNAME=seu_usuario \
  -e SPRING_DATASOURCE_PASSWORD=sua_senha \
  -e JWT_SECRET=uma_chave_longa_e_aleatoria \
  bancoquestoes-server
```
 
> Dentro do container, `localhost` aponta para o próprio container. Para acessar um PostgreSQL rodando na sua máquina, use `host.docker.internal` ou coloque os dois containers na mesma rede Docker.
 
A API fica disponível em `http://localhost:8080`.
 
## Documentação interativa (Swagger)
 
Com a aplicação rodando, abra [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) para ver e testar todos os endpoints no navegador. Para autenticar, faça login em `POST /auth/login` e use o token no botão **Authorize**.
 
## Autenticação
 
A API usa tokens JWT.
 
1. Envie as credenciais para `POST /auth/login`:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "seu_usuario", "password": "sua_senha"}'
```
 
2. A resposta traz o token:
```json
{ "token": "eyJhbGciOi..." }
```
 
3. Envie o token no cabeçalho das demais requisições:
```
Authorization: Bearer <seu_token>
```
 
### Usuário único
 
O projeto foi pensado para uso individual, com um único usuário, por isso não existe endpoint de cadastro. Cada pessoa que for rodar o sistema cria o seu próprio usuário diretamente no banco de dados. A tabela `usuario` é criada automaticamente na primeira execução da aplicação, então **inicie o sistema ao menos uma vez antes de inserir o usuário**:
 
1. **Gere o hash BCrypt da sua senha.** A senha nunca é guardada em texto puro. Em Linux ou macOS, com o `htpasswd` (pacote `apache2-utils`):
```bash
htpasswd -bnBC 10 "" 'sua_senha' | tr -d ':\n'
```
 
> No Windows, use o WSL ou outra ferramenta que gere hashes BCrypt. Evite sites de geração de hash na internet: você estaria enviando a sua senha a terceiros.
 
2. **Insira o usuário no PostgreSQL**, com o hash gerado:
```sql
INSERT INTO usuario (username, password)
VALUES ('seu_usuario', '<hash_gerado>');
```
 
3. Use esse usuário e a senha escolhida em `POST /auth/login`.
## Endpoints
 
A API possui 14 endpoints.
 
### Autenticação
 
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/auth/login` | Autentica o usuário e retorna um token JWT |
 
### Disciplinas
 
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/disciplina/list` | Lista todas as disciplinas |
| `POST` | `/disciplina` | Cria uma disciplina |
| `PUT` | `/disciplina/{id}` | Atualiza uma disciplina |
| `DELETE` | `/disciplina/{id}` | Remove uma disciplina |
 
### Tags
 
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/tag/list` | Lista todas as tags |
| `POST` | `/tag` | Cria uma tag |
| `PUT` | `/tag/{id}` | Atualiza uma tag |
| `DELETE` | `/tag/{id}` | Remove uma tag |
 
### Questões
 
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/questao/pesquisa` | Pesquisa questões a partir de filtros enviados no corpo da requisição |
| `POST` | `/questao` | Cria uma questão |
| `PUT` | `/questao/{idQuestao}` | Atualiza uma questão |
| `DELETE` | `/questao/{idQuestao}` | Remove uma questão |
 
### Provas
 
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/questao/{idDisciplina}-{tamanho}` | Gera uma prova com `tamanho` questões sorteadas da disciplina `idDisciplina` |
 
## Exemplos de requisição
 
**Criar disciplina ou tag** (`POST /disciplina`, `POST /tag`; o `PUT` usa o mesmo corpo):
 
```json
{ "nome": "Matemática" }
```
 
**Criar questão** (`POST /questao`; o `PUT /questao/{idQuestao}` usa o mesmo corpo):
 
```json
{
  "enunciado": "Quanto é 2 + 2?",
  "alternativas": [
    { "enunciado": "3", "correta": false },
    { "enunciado": "4", "correta": true },
    { "enunciado": "5", "correta": false }
  ],
  "disciplinaId": 1,
  "tagsIds": [1, 2]
}
```
 
O enunciado, as alternativas (ao menos uma) e a disciplina são obrigatórios. As tags são opcionais.
 
**Pesquisar questões** (`POST /questao/pesquisa`):
 
```json
{
  "pesquisa": "soma",
  "disciplinaId": 1,
  "tags": [1, 2]
}
```
 
**Gerar uma prova com 10 questões da disciplina de id 1:**
 
```bash
curl -X GET http://localhost:8080/questao/1-10 \
  -H "Authorization: Bearer <seu_token>"
```
 
As questões são retornadas neste formato:
 
```json
{
  "id": 7,
  "enunciado": "Quanto é 2 + 2?",
  "alternativas": [
    { "enunciado": "3", "correta": false },
    { "enunciado": "4", "correta": true }
  ]
}
```
 
## Estrutura do projeto
 
```
src/main/java/com/DevMoitinho/bancoQuestoes/
├── controller/   # Controladores REST (auth, disciplina, tag, questão, prova)
├── dto/          # Objetos de requisição e resposta
├── service/      # Regras de negócio
└── security/     # Configuração de segurança e geração de tokens JWT
Dockerfile
pom.xml
```
 
## Próximos passos
 
- Testes automatizados,
- Ggeração de multipplas provas.
## Contribuindo
 
Este é um projeto open source. Sugestões e contribuições são bem-vindas:
 
1. Faça um fork do repositório
2. Crie uma branch para a sua alteração (`git checkout -b minha-alteracao`)
3. Faça o commit e envie a branch
4. Abra um Pull Request

 
## Autor
 
João Vitor Moitinho Barbosa · [GitHub](https://github.com/DevMoitinho)

