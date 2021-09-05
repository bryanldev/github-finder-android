<div align=center margin= auto> 
  <img src="img/github_icon.png"  width=20%>
</div>
<h2 align="center"> GitHub Finder </h2>
<p align=center>Aplicativo Android para exibir informações básicas de usuários do <a href="https://github.com/">GitHub.</a></p>

---

</br>
<div align=center margin= auto> 
  <img src="img/screens.png"  width=80%>
</div>

## Funcionalidades

- Procurar usuários pelo 'username' da plataforma
- Descobrir os repositórios públicos destes usuários, com informações como: descrição, número de estrelas, forks e linguagem.
- Visitar a página web do perfil

## Instruções de uso

### Pré-requisitos

Para rodar este projeto em modo de desenvolvimento, será necessário que você tenha o ambiente básico para aplicações android utilizando o Android Studio. Essas informações podem ser encontradas [aqui](https://developer.android.com/training/basics/firstapp/running-app).

### Instalação

**Clone este repositório**

```
$ git clone https://github.com/bryanlds/github-finder-android.git

$ cd github-finder-android
```

Importe o projeto para o Android Studio e aguarde o Gradle baixar as dependências.

## Tecnologias

- Linguagem adotada: [**Kotlin**](https://kotlinlang.org/)
- [API do GitHub](https://developer.github.com/v3/)
- Uma arquitetura de Activity única, usando o componente [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) para gerenciamento de operações nos Fragments
- Camada de apresentação que contém um Fragment (View) e um [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) por tela
- [Live Data](https://developer.android.com/topic/libraries/architecture/livedata?hl=pt-br)
- [Data Binding](https://developer.android.com/topic/libraries/data-binding)
- Suporta mudanças de orientação das telas sem perda de estado
- [Constraint Layout](https://developer.android.com/training/constraint-layout)
- [Retrofit](https://square.github.io/retrofit/) - cliente HTTP
- [CircleImageView](https://github.com/hdodenhof/CircleImageView) - _'A fast circular ImageView perfect for profile images'_
- [Picasso](https://github.com/square/picasso) - download e cache de imagens
- [Paging v3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- Injeção de Dependência

## Licença

Este projeto está [licenciado](https://github.com/bryanlds/github-finder-android/blob/master/LICENSE) sob a licença MIT.
