<h1> GitHub Finder </h1>

Bem-vindo ao GitHub Finder, um aplicativo para exibir informações básicas de usuários do [GitHub](https://github.com/). Nele você poderá:

- Procurar usuários pelo nome
- Descobrir seus repositórios públicos e seus detalhes, como descrição e número de estrelas, forks, etc.
- Visitar a página web do perfil


## Tecnologias

- Linguagem adotada: **Kotlin**
- [API do GitHub](https://developer.github.com/v3/)
- Uma arquitetura de atividade única, usando o componente Navegação para gerenciar operações de fragmento.
- Camada de apresentação que contém um fragmento (View) e um ViewModel por tela.
- Live Data e Data Binding.
- Suporta mudanças de orientação das telas sem perder estado.
- Constraint Layout.

## Instruções de uso

A aplicação é simples, bastando que entre com o nome de usuário no campo indicado. Selecione o usuário desejado e você terá mais detalhes sobre o mesmo, como uma lista com seus repositórios públicos.

<div align=center margin= auto> 
  <img src="img.png"  width=80%>
</div>


## TODO

- Apenas 30 resultados são exibidos por vez por conta da paginação da API. Contornar isso.