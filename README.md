# Sistema de Gestão de Usuários, Emails e Notificações

Este projeto é uma arquitetura de microsserviços composta pelos seguintes serviços:
- **Usuário**: Gerencia os usuários, incluindo login e permissões.
- **Email**: Gerencia emails associados aos usuários.
- **Notificação**: Envia notificações aos administradores sobre eventos de email.
- **Eureka Server**: Realiza o *service discovery*.
- **API Gateway**: Intermedia chamadas entre microsserviços e controla o acesso aos endpoints com base nas permissões do usuário.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Cloud Eureka**
- **Spring Cloud Gateway**
- **Spring Security**
- **Spring Kafka**
- **Java Mail Sender**
- **MySQL**
- **Docker**
- **OpenFeign** 
- **Lombok**
- **JUnit5**
- **ModelMapper**
- **Flyway**
- **Jwt**

## Estrutura de Serviços

1. **Service-Usuario**
    - Gerencia os dados dos usuários.
    - Exemplo de tabela MySQL:
    ```sql
    CREATE TABLE usuario (
        id INT PRIMARY KEY AUTO_INCREMENT,
        nome VARCHAR(200) NOT NULL,
        cpf VARCHAR(50),
        password VARCHAR(200),
        data_criacao DATE,
        data_atualizacao DATE,
        eh_admin BOOLEAN
    );
    ```

2. **Service-Email**
    - Gerencia emails dos usuários.
    - Exemplo de tabela MySQL:
    ```sql
    CREATE TABLE email (
        id INT PRIMARY KEY AUTO_INCREMENT,
        usuario_id INT NOT NULL,
        email VARCHAR(150) NOT NULL,
        data_criacao DATE NOT NULL,
        eh_admin BOOLEAN NOT NULL
    );
    ```

3. **Service-Notificacao**
    - Envia notificações aos administradores sobre eventos relacionados a emails de forma assincrona.
    
4. **Service-Discovery (Eureka Server)**
    - Todos os microsserviços são registrados no Eureka.

5. **API Gateway**
    - Controla o roteamento e a segurança das requisições.
  
6. **Kafka e Zookeper**
    - Controla a conexão assincrona entre os serviços
   **Obs: O Docker-compose na raiz do projeto e do Kafka** 

## Configuração do API Gateway

O **API Gateway** roteia requisições para os microsserviços e aplica segurança com base no campo `eh_admin` na tabela **Usuário**.

### Regras de Segurança

- Apenas usuários com `eh_admin = true` podem acessar endpoints de administração.
- A segurança é configurada no Gateway para validar o token JWT e verificar a permissão.

## Documentação PostManCollection
- https://documenter.getpostman.com/view/25729709/2sAXqnej4C
