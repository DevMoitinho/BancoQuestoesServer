# BancoQuestõesServer
 
API REST para armazenamento e gestão de questões, pensada para professores que precisam organizar um banco de questões e montar provas.
 
> **Status:** em desenvolvimento.
> O front-end está em um repositório separado: [BancoQuestoesInterface](https://github.com/DevMoitinho/BancoQuestoesInterface).
 
## Funcionalidades
 
- Cadastro, consulta, edição e remoção de questões (CRUD)
- Busca de questões com filtros
- Geração de provas a partir das questões cadastradas
- Autenticação e autorização com JWT
- [PREENCHER: outras funcionalidades, por exemplo categorias, níveis de dificuldade, usuários por perfil]
## Tecnologias
 
- Java [PREENCHER: versão]
- Spring Boot
- Spring Security + JWT
- PostgreSQL
- Docker
- Maven
## Pré-requisitos
 
- JDK [PREENCHER: versão]
- PostgreSQL em execução [PREENCHER: versão] ou Docker
- Git
## Como configurar
 
### 1. Clonar o repositório
 
```bash
git clone https://github.com/DevMoitinho/BancoQuestoesServer.git
cd BancoQuestoesServer
```
 
### 2. Configurar as variáveis de ambiente
 
A aplicação usa as seguintes variáveis [PREENCHER: confirmar os nomes no `application.properties`]:
 
| Variável | Descrição | Exemplo |
|---|---|---|
| `DB_URL` | URL de conexão com o PostgreSQL | `jdbc:postgresql://localhost:5432/bancoquestoes` |
| `DB_USER` | Usuário do banco | `postgres` |
| `DB_PASSWORD` | Senha do banco | `sua_senha` |
| `JWT_SECRET` | Chave secreta usada para assinar os tokens | `uma_chave_longa_e_aleatoria` |
 
> Nunca envie senhas ou chaves reais para o repositório.
 
### 3. Executar
 
**Localmente, com Maven:**
 
```bash
./mvnw spring-boot:run
```
 
**Com Docker:**
 
```bash
docker build -t bancoquestoes-server .
docker run -p 8080:8080 \
  -e DB_URL=... \
  -e DB_USER=... \
  -e DB_PASSWORD=... \
  -e JWT_SECRET=... \
  bancoquestoes-server
```
 
A API fica disponível em `http://localhost:8080` [PREENCHER: confirmar a porta].
 
## Autenticação
 
A API usa tokens JWT.
 
1. Faça login em `[PREENCHER: rota de login]` com suas credenciais.
2. Use o token recebido no cabeçalho das demais requisições:
```
Authorization: Bearer <seu_token>
```
 
## Endpoints
 
A API possui 14 endpoints.
 
| Método | Rota | Descrição | Autenticação |
|---|---|---|---|
| [PREENCHER] | [PREENCHER] | [PREENCHER] | [PREENCHER] |
 
[PREENCHER: uma linha por endpoint, agrupando por recurso: autenticação, questões, provas]
 
### Exemplo de requisição
 
```bash
curl -X GET http://localhost:8080/[PREENCHER: rota] \
  -H "Authorization: Bearer <seu_token>"
```
 
## Estrutura do projeto
 
```
src/main/java/...   # [PREENCHER: pacotes, por exemplo controller, service, repository, model, security]
Dockerfile
pom.xml
```
 
## Próximos passos
 
- [PREENCHER: o que ainda falta, por exemplo testes automatizados, documentação com Swagger, deploy]
## Contribuindo
 
Este é um projeto open source. Sugestões e contribuições são bem-vindas:
 
1. Faça um fork do repositório
2. Crie uma branch para a sua alteração (`git checkout -b minha-alteracao`)
3. Faça o commit e envie a branch
4. Abra um Pull Request
## Licença
 
[PREENCHER: escolha uma licença, por exemplo MIT, e adicione o arquivo LICENSE]
 
## Autor
 
João Vitor Moitinho Barbosa · [GitHub](https://github.com/DevMoitinho)

