# Locadora Kalleo

Sistema web de locacao de jogos de videogame desenvolvido em Java com JSF e PrimeFaces. A aplicacao foi adaptada de uma locadora de filmes para um catalogo de jogos, hoje usando JDBC direto com MySQL e inicializacao automatica do banco.

## Visao geral

O sistema permite:

- cadastro, alteracao, consulta e exclusao de jogos
- cadastro, alteracao, consulta e exclusao de clientes
- cadastro, alteracao, consulta e exclusao de funcionarios
- efetuacao de locacoes
- devolucao de jogos locados
- resumo financeiro com graficos
- carga automatica de base inicial com seed de jogos e dados de homologacao

O catalogo foi remodelado para jogos de videogame, com suporte a:

- nome do jogo
- plataforma
- genero
- faixa etaria
- preco
- promocao
- imagem
- disponibilidade

## Stack utilizada

- Java 21
- Maven
- Apache Tomcat 9
- JSF 2.2
- PrimeFaces 8
- MySQL
- JDBC puro

## Estrutura principal

- `src/main/java/br/com/foursys/locadora/backingbean`
  - backing beans das telas JSF
- `src/main/java/br/com/foursys/locadora/controller`
  - camada de controle usada pelos backings
- `src/main/java/br/com/foursys/locadora/dao`
  - acesso a dados com JDBC
- `src/main/java/br/com/foursys/locadora/util`
  - utilitarios, conexao JDBC e bootstrap do banco
- `src/main/java/br/com/foursys/locadora/listener`
  - listener de inicializacao do banco
- `src/main/webapp`
  - telas XHTML e recursos web
- `src/main/resources/db/init.sql`
  - script de inicializacao do banco
- `backup - locadora.sql`
  - dump principal do banco para importacao manual

## Requisitos

Antes de rodar o projeto, garanta que o ambiente tenha:

- JDK 21 instalado
- Maven configurado
- MySQL em execucao
- Apache Tomcat 9 configurado na IDE ou localmente

Importante:

- Este projeto usa `javax.*`, portanto deve ser executado em Tomcat 9.
- Tomcat 10+ e Tomcat 11 nao sao compativeis com a base atual sem migracao para Jakarta.

## Configuracao do banco

Hoje a conexao esta configurada para MySQL local.

Configuracao padrao usada pela aplicacao:

- host: `localhost`
- porta: `3306`
- banco: `dtb-locadora-rich`
- usuario: `root`
- senha: `root`

A conexao e feita via `JdbcUtil`.

Se o seu MySQL usar outra porta, usuario ou senha, ajuste a configuracao em:

- `src/main/java/br/com/foursys/locadora/util/JdbcUtil.java`

## Inicializacao automatica do banco

Na subida da aplicacao, um listener tenta:

1. conectar ao MySQL
2. criar o banco caso ele ainda nao exista
3. aplicar o script `src/main/resources/db/init.sql`
4. garantir uma seed inicial quando necessario

Arquivos relacionados:

- `src/main/java/br/com/foursys/locadora/util/DatabaseInitializer.java`
- `src/main/java/br/com/foursys/locadora/listener/DatabaseBootstrapListener.java`
- `src/main/webapp/WEB-INF/web.xml`

Parametros importantes do `web.xml`:

- `locadora.db.init.enabled`
  - habilita ou desabilita a inicializacao automatica
- `locadora.db.init.failOnError`
  - define se a aplicacao deve falhar ao subir quando o bootstrap do banco nao conseguir concluir

Observacao:

- Se o banco ja existir com dados antigos, a seed nao substitui automaticamente tudo em todos os cenarios.
- Para refletir uma nova massa de dados, pode ser necessario recriar o banco ou importar novamente o dump.

## Massa de dados

O projeto possui duas fontes principais para popular o banco:

- `src/main/resources/db/init.sql`
- `backup - locadora.sql`

Atualmente a base inclui:

- catalogo de jogos conhecidos em multiplas plataformas
- clientes fake para homologacao
- funcionarios fake para homologacao
- locacoes de teste distribuidas em 2024, 2025 e 2026
- relacoes entre locacoes e jogos

Isso permite testar:

- filtros de consulta
- locacao
- devolucao
- resumo financeiro
- graficos por ano, por mes e por categoria

## Como executar no Eclipse

1. Importe o projeto como projeto Maven existente.
2. Configure o servidor Apache Tomcat 9.
3. Verifique se o MySQL esta ativo.
4. Confira se usuario, senha e porta do banco batem com o `JdbcUtil`.
5. Execute `Maven > Update Project`.
6. Faca `Project > Clean`, se necessario.
7. Suba o projeto no Tomcat.

URL padrao esperada:

- `http://localhost:8080/locadora`

Dependendo da configuracao do Eclipse, o contexto pode usar o nome do projeto publicado.

## Como compilar via Maven

Comando utilizado para validar a compilacao do projeto:

```bash
mvn -q "-Dmaven.repo.local=.m2/repository" -DskipTests compile
