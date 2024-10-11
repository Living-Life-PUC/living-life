# living-life
**Living Life** é uma rede social fitness projetada para conectar atletas e profissionais de Educação Física, com o objetivo de promover a saúde e combater o sedentarismo. A plataforma facilita a interação entre os usuários, o compartilhamento de dicas e treinos, a contratação de profissionais especializados e a criação de desafios personalizados. Desenvolvida com Spring Boot e containerizada utilizando Docker, a aplicação busca simplificar seu processo de execução por meio do uso de Docker Compose.
## Requisitos

- [Docker](https://www.docker.com/get-started)

## Como Executar

Para iniciar a aplicação, siga os seguintes passos:

1. Clone o repositório:
   ```bash
   git clone https://github.com/gustavopcr/living-life.git
   cd living-life
   ```
2. Execute o Docker Compose:
   ```bash
   docker compose up
   ```

## Estrutura do Projeto

- **src/**: Código-fonte da aplicação.
- **docker-compose.yml**: Arquivo de configuração do Docker Compose.
- **./mvnw**: Wrapper do Maven para gerenciar dependências.