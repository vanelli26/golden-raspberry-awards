# Golden Raspberry Awards API

## Descrição
API para obter informações sobre os vencedores do prêmio Pior Filme do Golden Raspberry Awards.

## Instruções para rodar o projeto

1. Clone o repositório.
2. Execute `mvn clean install` para compilar o projeto.
3. Execute `mvn spring-boot:run` para rodar a aplicação.
4. Para rodar os testes de integração, execute `mvn test`.

## Documentação da API

A documentação da API está disponível no Swagger UI. Você pode acessá-la através do seguinte link:

[Swagger UI](http://localhost:8080/swagger-ui/index.html)

Certifique-se de que a aplicação está em execução antes de acessar o Swagger UI.

## Banco de Dados
O banco de dados H2 também está disponível para consulta. Você pode acessá-lo através do seguinte link:

[H2 Console](http://localhost:8080/h2-console)

JDBC URL: `jdbc:h2:mem:testdb`
User: `gra`
Password: `vanelli`

## Autenticação

Esta API utiliza autenticação básica. Para acessar os endpoints protegidos, você deve incluir um cabeçalho `Authorization` em suas requisições.

### Formato do Cabeçalho

- **username**: O nome de usuário para autenticação (por exemplo, `gra`).
- **password**: A senha correspondente (por exemplo, `vanelli`).
