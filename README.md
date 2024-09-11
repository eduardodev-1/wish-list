# wish-list
lista de desejos
# Documentação do Projeto

## Testes e Configuração do Banco de Dados

### Testes Locais

Para rodar os testes de repository e de integração, você precisará ter uma instância local do MongoDB rodando na porta padrão `27017`.

### Configuração da Aplicação no Docker

Ao subir a aplicação no Docker, você precisará substituir o endereço do MongoDB no `application.properties` de `mongodb://localhost:27017` para `mongodb://host.docker.internal:27017`. Isso permitirá que a aplicação Docker consiga acessar o banco de dados MongoDB local.

### Documentação dos Endpoints

A aplicação está documentada com Swagger. Você pode acessar a documentação através dos seguintes endpoints:

- **Swagger UI Custom Path:** `/swagger-ui.html`
- **API Docs Endpoint Custom Path:** `/api-docs`

## Configurações do Swagger

```properties
# Swagger UI custom path
springdoc.swagger-ui.path=/swagger-ui.html

# /api-docs endpoint custom path
springdoc.api-docs.path=/api-docs
