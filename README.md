# Estrutura Hierárquica de Delegação

## Autores

| **Identificação** | **Nome** | **Formação** |
| :-: | :-: | :-: |
| <img src="https://github.com/dartmol203.png" width=100 height=100 alt="André Corrêa da Silva" class="img-thumbnail image"> | André Corrêa da Silva | Graduando em Engenharia de Software (UnB) |
| <img src="https://github.com/gabrielm2q.png" width=100 height=100 alt="Gabriel Mariano da Silva" class="img-thumbnail image"> | Gabriel Mariano da Silva | Graduando em Engenharia de Software (UnB) |

*Tabela 1: Identificação dos Autores*

## Descrição

O *building block* contido neste repositório tem por objetivo a implementação de uma Estrutura Hierárquica de Delegação, onde o agente responsável pela decisão, representando o "topo da hierarquia", delega aos agentes subordinados, tomando como base para tal decisão a capacidade e disponibilidade de cada um
dos respectivos agentes subalternos, a execução das operações devidas sobre a informação inicial, informando a ele os resultados obtidos.

<!-- ### Projeto em Execução

<img src="" alt="Descrição do Print">

*Figura 1: Print do Projeto em Execução* -->

## Requisitos Técnicos

1. **Identificação de agentes qualificados e disponíveis:** dado um conjunto inicial de dados, o agente responsável, tendo em mente as informações que devem ser retiradas destes, deve ser capaz de identificar um agente subalterno qualificado e disponível para a realização de alguma das operações necessárias;
2. **Delegação de atividades:** tendo identificado um agente que possa ser responsabilizado por uma atividade, o agente responsável deve delegar a este a realização de tal;
3. **Execução das atividades:** tendo recebido uma demanda e o conjunto de dados a ser trabalhado, um agente subalterno deve ser capaz de realizar as operações
necessárias sobre estes dados;
4. **Retorno dos resultados:** uma vez realizada a operação necessária sobre os dados obtidos, o agente subordinado deve retornar ao agente requerente os resultados obtidos.

## Requisitos para Execução

Para a efetiva execução do *building block* disposto no repositório, se faz necessária a instalação e configuração do *software* *Maven* em sua máquina. Para tal, basta seguir as instruções de instalação dispostas na [**documentação do *Maven***](https://maven.apache.org/install.html). Para o desenvolvimento do *building block*, foi utilizado o *Maven* na versão **3.8.7**. Além disso, todas as instruções de execução consideram o uso de sistemas operacionais baseados em *Linux*.

## Como Executar?

Para a execução do *building block*, é possível utilizar-se do *Makefile* adicionado ao repositório ao seguir os seguintes passos:

- Primeiramente, clone o repositório em sua máquina:

```bash
git clone https://github.com/SMA-building-blocks/hierarchical-delegation.git
```

- Em seguida, vá para a pasta do repositório:

```bash
cd hierarchical-delegation
```

- Para realizar a *build* do projeto e executá-lo em seguida, execute o seguinte comando:

```bash
make build-and-run
```

> 🚨 **IMPORTANTE:** Ao executar o projeto, primeiro será realizada a criação de todos os agentes participantes. Logo após, para a efetiva realização do propósito desejado pelo *building block*, é necessário pressionar **ENTER** no terminal para a continuidade da execução do código. Esta decisão foi tomada em prol de uma facilitação do uso do *sniffer* para a visualização da comunicação entre os agentes participantes.

- É possível realizar apenas a *build* do projeto com o seguinte comando:

```bash
make build
```

- Similarmente, é possível rodar o projeto após a geração de sua build com o seguinte comando:

```bash
make run
```

- Por fim, para apagar os arquivos derivados da *build* do projeto, execute o seguinte comando:

```bash
make clean
```

## Referências

[*Jade Project*](https://jade-project.gitlab.io/). <br />
[*Maven*](https://maven.apache.org/).